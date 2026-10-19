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
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class CopperTapeMeasureItem extends Item {
    private final Block hologramBlock = ModBlocks.HOLOGRAM_BLOCK.get();

    public CopperTapeMeasureItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Direction dir = context.getClickedFace();
        BlockPos pos = context.getClickedPos().relative(dir);

        if (!level.isClientSide){
            ItemStack stack = context.getItemInHand();

            if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) == null
                    && stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) == null) {

                stack.set(ModDataComponents.TEMPORAL_DIRECTION, dir);
                stack.set(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS, pos);
                stack.set(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS, null);

                return InteractionResult.SUCCESS;
            }
            else if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) != null
                    && stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null
                    && stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS) == null) {

                stack.set(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS, pos);

                BlockPos initialPos = stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS);
                BlockPos secondaryPos = stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS);
                List<BlockPos> toUnmark = new ArrayList<>();

                if (initialPos != null && secondaryPos != null) {
                    if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.UP || stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.DOWN) {
                        List<BlockPos> toMark = new ArrayList<>(getFlatHorizontalCircleBlocks(initialPos, secondaryPos));

                        for (var position : toMark){
                            if (level.getBlockState(position).isAir()) {
                                level.setBlockAndUpdate(position, hologramBlock.defaultBlockState());
                                toUnmark.add(position);
                            }
                        }
                    }
                    stack.set(ModDataComponents.TEMPORAL_BLOCKPOS_LIST, toUnmark);

                    Player player = context.getPlayer();
                    if (player != null) {
                        EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                                ? EquipmentSlot.OFFHAND
                                : EquipmentSlot.MAINHAND;
                        stack.hurtAndBreak(1, player, equipmentslot);
                    }
                    return InteractionResult.SUCCESS;
                }

                else if (Screen.hasShiftDown() && stack.is(ModItems.COPPER_TAPE_MEASURE)) {
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
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide){
            if (Screen.hasShiftDown() && stack.is(ModItems.COPPER_TAPE_MEASURE)){
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
        if(entity instanceof Player player){
            if (stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null
                    && stack.get(ModDataComponents.TEMPORAL_SECONDARY_BLOCKPOS) == null) {

                BlockPos initialPos = stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS);
                BlockPos secondaryPos =  getBlockLookedAt(player);

                List<BlockPos> toUnmark = new ArrayList<>();
                if (ModDataComponents.TEMPORAL_BLOCKPOS_LIST != null){
                    List<BlockPos> toDelete = new ArrayList<>(stack.get(ModDataComponents.TEMPORAL_BLOCKPOS_LIST));

                    for (var position : toDelete) {
                        if (level.getBlockState(position).is(hologramBlock)) {
                            level.destroyBlock(position, false);
                        }
                    }
                }

                if (stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.UP || stack.get(ModDataComponents.TEMPORAL_DIRECTION) == Direction.DOWN) {
                    List<BlockPos> toMark = new ArrayList<>(getFlatHorizontalCircleBlocks(initialPos, secondaryPos));

                    for (var position : toMark) {
                        if (level.getBlockState(position).isAir()) {
                            level.setBlockAndUpdate(position, hologramBlock.defaultBlockState());
                            toUnmark.add(position);
                        }
                    }
                }
                stack.set(ModDataComponents.TEMPORAL_BLOCKPOS_LIST, toUnmark);
            }
        }
    }

    private static BlockPos getBlockLookedAt(Player player){

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        BlockPos secondaryPos = traceResult.getBlockPos();

        return secondaryPos;
    }

    public static List<BlockPos> getFlatHorizontalCircleBlocks(BlockPos initial, BlockPos secondary) {
        List<BlockPos> circleBlocks = new ArrayList<>();

        Vec2 center = new Vec2(initial.getX(), initial.getZ());
        Vec2 target = new Vec2(secondary.getX(), secondary.getZ());

        Vec2 diff = new Vec2(secondary.getX() - initial.getX(), target.y - center.y);
        int r = Math.round(diff.length());

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



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.trowel_item.tooltip"));
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
