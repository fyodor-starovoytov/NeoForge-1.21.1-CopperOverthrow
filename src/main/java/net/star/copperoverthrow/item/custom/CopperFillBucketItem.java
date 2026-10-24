package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.custom.HologramBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CopperFillBucketItem extends AbstractRepairableCopperItem {
    public CopperFillBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState clickedBlock = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (player == null || !(clickedBlock.getBlock() instanceof HologramBlock)) {
            return InteractionResult.FAIL;
        }

        ItemStack consumableStack = player.getOffhandItem();
        if (context.getHand() == InteractionHand.OFF_HAND) {
            consumableStack = player.getMainHandItem();
        }

        if (!(consumableStack.getItem() instanceof BlockItem blockItem)) {
            return InteractionResult.FAIL;
        }

        if (level.isClientSide) {
            level.playLocalSound(pos, SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 1, 1, false);
            level.playLocalSound(pos, SoundEvents.WOOL_PLACE, SoundSource.PLAYERS, 1, 1, false);
            return InteractionResult.SUCCESS;
        }

        List<BlockPos> blocksToBeFilled = new ArrayList<>(getBlocksToBeFilled(pos, level));
        ServerLevel serverLevel = (ServerLevel) level;

        for (BlockPos fillPos : blocksToBeFilled) {
            if (consumableStack.isEmpty()) {
                break;
            }

            BlockHitResult hitResult = new BlockHitResult(
                    context.getClickLocation(),
                    context.getClickedFace(),
                    fillPos,
                    context.isInside()
            );

            BlockPlaceContext placeContext = new BlockPlaceContext(
                    level,
                    player,
                    context.getHand(),
                    consumableStack,
                    hitResult
            );

            if (!placeContext.canPlace()) {
                continue;
            }

            InteractionResult result = blockItem.place(placeContext);
            if (result.consumesAction()) {
                //Particles from server to client
                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, blockItem.getBlock().defaultBlockState()),
                        fillPos.getX() + 0.5, fillPos.getY() + 0.5, fillPos.getZ() + 0.5,
                        4, 0.2, 0.2, 0.2, 0.05
                );

                EquipmentSlot slot = context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                context.getItemInHand().hurtAndBreak(1, serverLevel, player, tool -> player.onEquippedItemBroken(tool, slot));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static List<BlockPos> getBlocksToBeFilled(BlockPos initalBlockPos, Level level) {
        List<BlockPos> toBeChecked = new ArrayList<>();
        List<BlockPos> toBeDestroyed = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        Block targetBlock = level.getBlockState(initalBlockPos).getBlock();

        int r = 1;

        BlockPos initialImmutable = initalBlockPos.immutable();
        toBeChecked.addFirst(initialImmutable);
        toBeDestroyed.addFirst(initialImmutable);
        visited.add(initialImmutable);

        while (!toBeChecked.isEmpty() && toBeDestroyed.size() < ServerConfig.MEASURE_MAX_DISTANCE.get()){
            BlockPos comparable = toBeChecked.removeLast();
            for (BlockPos pos : BlockPos.betweenClosed(comparable.offset(-r, -r, -r), comparable.offset(r, r, r))) {
                BlockState state = level.getBlockState(pos);
                BlockPos immutablePos = pos.immutable();

                if (state.is(targetBlock) && !visited.contains(immutablePos)) {
                    toBeChecked.add(immutablePos);
                    toBeDestroyed.add(immutablePos);
                    visited.add(immutablePos);
                }
            }
        }
        return toBeDestroyed;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_fill_bucket.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
