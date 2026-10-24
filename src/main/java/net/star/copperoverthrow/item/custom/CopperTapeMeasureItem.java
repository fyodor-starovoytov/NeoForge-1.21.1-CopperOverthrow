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
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.custom.HologramBlock;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;

import java.util.ArrayList;
import java.util.List;

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

        if (!level.isClientSide) {
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
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player.isShiftKeyDown()){
            if (!level.isClientSide && stack.is(ModItems.COPPER_TAPE_MEASURE)){
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
            if (stack.get(ModDataComponents.IS_USED) != null && isSelected) {
                BlockPos initialPos = stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS);
                BlockPos secondaryPos = getBlockLookedAt(player); // Current target look pos

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

                    List<BlockPos> toMark = getFlatCircleBlocksY(initialPos, secondaryPos);

                    if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.EAST
                            || stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.WEST) {
                        toMark = getFlatCircleBlocksX(initialPos, secondaryPos);
                    }

                    if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.NORTH
                            || stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.SOUTH) {
                        toMark = getFlatCircleBlocksZ(initialPos, secondaryPos);
                    }

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


    private static BlockPos getBlockLookedAt(Player player){

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

        Vec2 diff = new Vec2(secondary.getX() - initial.getX(), target.y - center.y);
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
