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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.custom.HologramBlock;
import net.star.copperoverthrow.event.CustomSpyglassArmposeExtension;
import net.star.copperoverthrow.item.ModItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CopperTapeMeasureItem extends AbstractRepairableCopperItem {
    private final Block hologramBlock;

    public CopperTapeMeasureItem(Properties properties, HologramBlock block) {
        super(properties);
        this.hologramBlock = block;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Direction dir = context.getClickedFace();
        BlockPos pos = context.getClickedPos().relative(dir);
        ItemStack stack = context.getItemInHand();
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
            } else if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown() && stack.is(ModItems.COPPER_SEXTANT.get())) {
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
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide && stack.is(ModItems.COPPER_SEXTANT.get())) {
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

                    List<BlockPos> toMark = getFlatCircleBlocksY(initialPos, secondaryPos);

                    if (tag.contains("temporal_direction")) {
                        Direction savedDir = Direction.byName(tag.getString("temporal_direction"));
                        if (savedDir == Direction.EAST || savedDir == Direction.WEST) {
                            toMark = getFlatCircleBlocksX(initialPos, secondaryPos);
                        } else if (savedDir == Direction.NORTH || savedDir == Direction.SOUTH) {
                            toMark = getFlatCircleBlocksZ(initialPos, secondaryPos);
                        }
                    }

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

    private static BlockPos getBlockLookedAt(Player player) {
        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(3f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        BlockPos secondaryPos = traceResult.getBlockPos();
        return secondaryPos;
    }

    public static List<BlockPos> getFlatCircleBlocksY(BlockPos initial, BlockPos secondary) {
        List<BlockPos> circleBlocks = new ArrayList<>();

        Vec2 center = new Vec2(initial.getX(), initial.getZ());
        Vec2 target = new Vec2(secondary.getX(), secondary.getZ());
        Vec2 diff = new Vec2(target.x - center.x, target.y - center.y);

        int r = Math.min(Math.round(diff.length()), ServerConfig.MEASURE_MAX_DISTANCE.get());

        int centerX = Math.round(center.x);
        int centerZ = Math.round(center.y);

        for (int x = -r; x <= r; x++) {
            for (int z = -r; z <= r; z++) {
                float distanceSq = (x * x) + (z * z);

                if (distanceSq >= Math.pow(r - 0.5f, 2) && distanceSq <= Math.pow(r + 0.5f, 2)) {
                    circleBlocks.add(new BlockPos(centerX + x, initial.getY(), centerZ + z));
                }
            }
        }
        return circleBlocks;
    }

    public static List<BlockPos> getFlatCircleBlocksZ(BlockPos initial, BlockPos secondary) {
        List<BlockPos> circleBlocks = new ArrayList<>();

        Vec2 center = new Vec2(initial.getX(), initial.getY());
        Vec2 target = new Vec2(secondary.getX(), secondary.getY());
        Vec2 diff = new Vec2(target.x - center.x, target.y - center.y);

        int r = Math.min(Math.round(diff.length()), ServerConfig.MEASURE_MAX_DISTANCE.get());

        int centerX = Math.round(center.x);
        int centerY = Math.round(center.y);

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                float distanceSq = (x * x) + (y * y);

                if (distanceSq >= Math.pow(r - 0.5f, 2) && distanceSq <= Math.pow(r + 0.5f, 2)) {
                    circleBlocks.add(new BlockPos(centerX + x, centerY + y, initial.getZ()));
                }
            }
        }
        return circleBlocks;
    }

    public static List<BlockPos> getFlatCircleBlocksX(BlockPos initial, BlockPos secondary) {
        List<BlockPos> circleBlocks = new ArrayList<>();

        Vec2 center = new Vec2(initial.getY(), initial.getZ());
        Vec2 target = new Vec2(secondary.getY(), secondary.getZ());
        Vec2 diff = new Vec2(target.x - center.x, target.y - center.y);

        int r = Math.min(Math.round(diff.length()), ServerConfig.MEASURE_MAX_DISTANCE.get());

        int centerY = Math.round(center.x);
        int centerZ = Math.round(center.y);

        for (int y = -r; y <= r; y++) {
            for (int z = -r; z <= r; z++) {
                float distanceSq = (y * y) + (z * z);

                if (distanceSq >= Math.pow(r - 0.5f, 2) && distanceSq <= Math.pow(r + 0.5f, 2)) {
                    circleBlocks.add(new BlockPos(initial.getX(), centerY + y, centerZ + z));
                }
            }
        }
        return circleBlocks;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_sextant.tooltip"));
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
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(CustomSpyglassArmposeExtension.INSTANCE);
    }
}