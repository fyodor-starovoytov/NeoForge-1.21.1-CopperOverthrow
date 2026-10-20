package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.custom.HologramBlock;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class CopperLongRuler extends Item {
    public CopperLongRuler(Properties properties) {
        super(properties);
    }
    private final Block hologramBlock = ModBlocks.HOLOGRAM_BLOCK.get();

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        Direction dir = context.getClickedFace();
        BlockPos pos = context.getClickedPos().relative(dir);

        if (!level.isClientSide){
            if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) == null
                    && stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) == null
                    && stack.get(ModDataComponents.IS_USED) == null) {

                stack.set(ModDataComponents.TEMPORAL_DIRECTION, dir);
                stack.set(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS, pos);
                stack.set(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS, null);
                stack.set(ModDataComponents.IS_USED, 1);

                return InteractionResult.SUCCESS;
            }

            else if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) != null
                    && stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null
                    && stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS) == null
                    && stack.get(ModDataComponents.IS_USED) != null) {

                stack.set(ModDataComponents.IS_USED, null);
                stack.set(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS, pos);

                Player player = context.getPlayer();
                if (player != null) {
                    EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                            ? EquipmentSlot.OFFHAND
                            : EquipmentSlot.MAINHAND;
                    stack.hurtAndBreak(1, player, equipmentslot);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide){
            if (Screen.hasShiftDown() && stack.is(ModItems.COPPER_LONG_RULER)){
                stack.set(ModDataComponents.IS_USED, null);
                stack.set(ModDataComponents.TEMPORAL_DIRECTION, null);
                stack.set(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS, null);
                stack.set(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS, null);

                if (stack.get(ModDataComponents.TEMPORAL_BLOCKPOS_LIST) != null) {
                    List<BlockPos> toDelete = new ArrayList<>(stack.get(ModDataComponents.TEMPORAL_BLOCKPOS_LIST));

                    for (var position : toDelete) {
                        if (level.getBlockState(position).is(hologramBlock)) {
                            level.destroyBlock(position, false);
                        }
                    }
                }

                return InteractionResultHolder.success(stack);
            }
        }
        return  InteractionResultHolder.pass(stack);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !level.isClientSide) {
            if (stack.get(ModDataComponents.IS_USED) != null) {
                BlockPos initialPos = stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS);
                BlockPos secondaryPos = getBlockLookedAt(player);

                if (initialPos == null || secondaryPos == null) return;

                BlockPos lastLookedAtPos = stack.get(ModDataComponents.TEMPORARY_LAST_REGISTERED_BLOCK);

                if (!secondaryPos.equals(lastLookedAtPos)) {
                    List<BlockPos> toDelete = new ArrayList<>();

                    if (stack.get(ModDataComponents.TEMPORAL_BLOCKPOS_LIST) != null) {
                        toDelete.addAll(stack.get(ModDataComponents.TEMPORAL_BLOCKPOS_LIST));
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

                    stack.set(ModDataComponents.TEMPORAL_BLOCKPOS_LIST, toDelete);
                    stack.set(ModDataComponents.TEMPORARY_LAST_REGISTERED_BLOCK, secondaryPos);
                }
            }
        }
    }


    public static List<BlockPos> getVector(BlockPos initial, BlockPos secondary) {
        List<BlockPos> vectorBlocks = new ArrayList<>();

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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_sextant.tooltip"));
        } else {
            if (stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null) {
                tooltipComponents.add(Component.literal("§7" + (stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS).toShortString())));
            }

            if (stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS) != null) {
                tooltipComponents.add(Component.literal("§7" + (stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS).toShortString())));
            }

            if (stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS) != null || stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null) {
                tooltipComponents.add(Component.empty());
            }

            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
