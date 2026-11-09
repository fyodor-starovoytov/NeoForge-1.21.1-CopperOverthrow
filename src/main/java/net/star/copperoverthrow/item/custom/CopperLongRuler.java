package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.custom.HologramBlock;
import net.star.copperoverthrow.event.CustomSpyglassArmposeExtension;
import net.star.copperoverthrow.item.ModItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CopperLongRuler extends AbstractRepairableCopperItem {
    private final Block hologramBlock;

    public CopperLongRuler(Properties properties, HologramBlock block) {
        super(properties);
        this.hologramBlock = block;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        Direction dir = context.getClickedFace();
        BlockPos pos = context.getClickedPos().relative(dir);
        CompoundTag tag = stack.getOrCreateTag();

        if (!level.isClientSide) {
            if (!tag.contains("temporal_direction")
                    && !tag.contains("temporal_initial_blockpos")
                    && !tag.contains("is_used")) {

                tag.putString("temporal_direction", dir.getName());
                tag.putLong("temporal_initial_blockpos", pos.asLong());
                tag.remove("temporal_secondary_blockpos");
                tag.putInt("is_used", 1);

                return InteractionResult.SUCCESS;
            } else if (tag.contains("temporal_direction")
                    && tag.contains("temporal_initial_blockpos")
                    && !tag.contains("temporal_secondary_blockpos")
                    && tag.contains("is_used")) {

                tag.remove("is_used");
                tag.putLong("temporal_secondary_blockpos", pos.asLong());

                Player player = context.getPlayer();
                if (player != null) {
                    EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                            ? EquipmentSlot.OFFHAND
                            : EquipmentSlot.MAINHAND;
                    stack.hurtAndBreak(1, player, (entity) -> {
                        entity.broadcastBreakEvent(equipmentslot);
                    });
                }
                return InteractionResult.SUCCESS;
            } else if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown() && stack.is(ModItems.COPPER_LONG_RULER.get())) {
                tag.remove("is_used");
                tag.remove("temporal_direction");
                tag.remove("temporal_initial_blockpos");
                tag.remove("temporal_secondary_blockpos");

                if (tag.contains("temporal_blockpos_list", Tag.TAG_LIST)) {
                    ListTag listTag = tag.getList("temporal_blockpos_list", Tag.TAG_LONG);
                    for (int i = 0; i < listTag.size(); i++) {
                        BlockPos position = BlockPos.of(((LongTag) listTag.get(i)).getAsLong());
                        if (level.getBlockState(position).is(hologramBlock)) {
                            level.destroyBlock(position, false);
                        }
                    }
                    tag.remove("temporal_blockpos_list");
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        CompoundTag tag = stack.getOrCreateTag();
        if (player.isShiftKeyDown()){
            if (!level.isClientSide && stack.is(ModItems.COPPER_LONG_RULER.get())){
                tag.remove("is_used");
                tag.remove("temporal_direction");
                tag.remove("temporal_initial_blockpos");
                tag.remove("temporal_secondary_blockpos");

                if (tag.contains("temporal_blockpos_list", Tag.TAG_LIST)) {
                    ListTag listTag = tag.getList("temporal_blockpos_list", Tag.TAG_LONG);
                    for (int i = 0; i < listTag.size(); i++) {
                        BlockPos position = BlockPos.of(((LongTag) listTag.get(i)).getAsLong());
                        if (level.getBlockState(position).is(hologramBlock)) {
                            level.destroyBlock(position, false);
                        }
                    }
                    tag.remove("temporal_blockpos_list");
                }

                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("is_used") && isSelected) {

                if (!tag.contains("temporal_initial_blockpos")) return;

                BlockPos initialPos = BlockPos.of(tag.getLong("temporal_initial_blockpos"));
                BlockPos secondaryPos = getBlockLookedAt(player);

                if (initialPos == null || secondaryPos == null) return;

                BlockPos lastLookedAtPos = null;
                if (tag.contains("last_registered_block")) {
                    lastLookedAtPos = BlockPos.of(tag.getLong("last_registered_block"));
                }

                if (!secondaryPos.equals(lastLookedAtPos)) {
                    List<BlockPos> toDelete = new ArrayList<>();

                    if (tag.contains("temporal_blockpos_list", Tag.TAG_LIST)) {
                        ListTag listTag = tag.getList("temporal_blockpos_list", Tag.TAG_LONG);
                        for (int i = 0; i < listTag.size(); i++) {
                            toDelete.add(BlockPos.of(((LongTag) listTag.get(i)).getAsLong()));
                        }
                    }

                    for (var position : toDelete) {
                        if (level.getBlockState(position).is(hologramBlock)) {
                            level.destroyBlock(position, false);
                        }
                    }
                    toDelete.clear();

                    List<BlockPos> toMark = getVector(initialPos, secondaryPos);

                    for (var position : toMark) {
                        if (level.getBlockState(position).isAir()) {
                            level.setBlockAndUpdate(position, hologramBlock.defaultBlockState().setValue(HologramBlock.PLACED_BY_RULER, true));
                            toDelete.add(position);
                        }
                    }

                    ListTag newListTag = new ListTag();
                    for (BlockPos pos : toDelete) {
                        newListTag.add(LongTag.valueOf(pos.asLong()));
                    }
                    tag.put("temporal_blockpos_list", newListTag);
                    tag.putLong("last_registered_block", secondaryPos.asLong());
                }
            }
        }
    }

    public static List<BlockPos> getVector(BlockPos initial, BlockPos secondary) {
        List<BlockPos> vectorBlocks = new ArrayList<>();

        if (initial.distSqr(secondary) > ServerConfig.MEASURE_MAX_DISTANCE.get() * ServerConfig.MEASURE_MAX_DISTANCE.get()) {
            return vectorBlocks;
        }

        int x1 = initial.getX();
        int y1 = initial.getY();
        int z1 = initial.getZ();

        int x2 = secondary.getX();
        int y2 = secondary.getY();
        int z2 = secondary.getZ();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dz = Math.abs(z2 - z1);

        int xs = (x2 > x1) ? 1 : -1;
        int ys = (y2 > y1) ? 1 : -1;
        int zs = (z2 > z1) ? 1 : -1;

        if (dx >= dy && dx >= dz) {
            int p1 = 2 * dy - dx;
            int p2 = 2 * dz - dx;

            while (x1 != x2) {
                vectorBlocks.add(new BlockPos(x1, y1, z1));
                if (p1 >= 0) {
                    y1 += ys;
                    p1 -= 2 * dx;
                }
                if (p2 >= 0) {
                    z1 += zs;
                    p2 -= 2 * dx;
                }
                p1 += 2 * dy;
                p2 += 2 * dz;
                x1 += xs;
            }
        }
        else if (dy >= dx && dy >= dz) {
            int p1 = 2 * dx - dy;
            int p2 = 2 * dz - dy;

            while (y1 != y2) {
                vectorBlocks.add(new BlockPos(x1, y1, z1));
                if (p1 >= 0) {
                    x1 += xs;
                    p1 -= 2 * dy;
                }
                if (p2 >= 0) {
                    z1 += zs;
                    p2 -= 2 * dy;
                }
                p1 += 2 * dx;
                p2 += 2 * dz;
                y1 += ys;
            }
        }
        else {
            int p1 = 2 * dy - dz;
            int p2 = 2 * dx - dz;

            while (z1 != z2) {
                vectorBlocks.add(new BlockPos(x1, y1, z1));
                if (p1 >= 0) {
                    y1 += ys;
                    p1 -= 2 * dz;
                }
                if (p2 >= 0) {
                    x1 += xs;
                    p2 -= 2 * dz;
                }
                p1 += 2 * dy;
                p2 += 2 * dx;
                z1 += zs;
            }
        }
        vectorBlocks.add(new BlockPos(x2, y2, z2));

        return vectorBlocks;
    }

    private static BlockPos getBlockLookedAt(Player player){
        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(3f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        BlockPos secondaryPos = traceResult.getBlockPos();
        return secondaryPos;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_long_ruler.tooltip"));
        } else {
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                if (tag.contains("temporal_initial_blockpos")) {
                    BlockPos pos = BlockPos.of(tag.getLong("temporal_initial_blockpos"));
                    tooltipComponents.add(Component.literal("§7" + pos.toShortString()));
                }

                if (tag.contains("temporal_secondary_blockpos")) {
                    BlockPos pos = BlockPos.of(tag.getLong("temporal_secondary_blockpos"));
                    tooltipComponents.add(Component.literal("§7" + pos.toShortString()));
                }

                if (tag.contains("temporal_secondary_blockpos") || tag.contains("temporal_initial_blockpos")) {
                    tooltipComponents.add(Component.empty());
                }
            }

            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(CustomSpyglassArmposeExtension.INSTANCE);
    }

}