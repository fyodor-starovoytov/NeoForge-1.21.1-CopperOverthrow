package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.custom.HologramBlock;

import java.util.ArrayList;
import java.util.List;

public class CopperFillBucketItem extends Item {
    public CopperFillBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState clickedBlock = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (player != null && clickedBlock.getBlock() instanceof HologramBlock) {
            ItemStack consumableStack = player.getOffhandItem();
            if (context.getHand() == InteractionHand.OFF_HAND){
                consumableStack = player.getMainHandItem();
            }
            if (consumableStack.getItem() instanceof BlockItem blockItem) {

                List<BlockPos> BlocksToBeFilled = new ArrayList<>(getBlocksToBeFilled(pos, level));

                for (BlockPos fillPos : BlocksToBeFilled) {

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
                        continue;}

                    if (level.isClientSide){
                        ParticleUtils.spawnParticlesOnBlockFaces(
                                level, fillPos, new BlockParticleOption(ParticleTypes.BLOCK, blockItem.getBlock().defaultBlockState()), UniformInt.of(1, 4));
                    }

                    if (!level.isClientSide) {
                        InteractionResult result = blockItem.place(placeContext);
                        if (result.consumesAction()) {
                            if (level instanceof ServerLevel serverLevel) {
                                EquipmentSlot slot = context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                                context.getItemInHand().hurtAndBreak(1, serverLevel, player, tool -> player.onEquippedItemBroken(tool, slot));
                            }
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public static List<BlockPos> getBlocksToBeFilled(BlockPos initalBlockPos, Level level) {
        List<BlockPos> toBeChecked = new ArrayList<>();
        List<BlockPos> toBeDestroyed = new ArrayList<>();
        Block targetBlock = level.getBlockState(initalBlockPos).getBlock();

        int r = 1;

        toBeChecked.addFirst(initalBlockPos.immutable());
        toBeDestroyed.addFirst(initalBlockPos.immutable());

        while (!toBeChecked.isEmpty() && toBeDestroyed.size() < ServerConfig.MEASURE_MAX_DISTANCE.get()){
            BlockPos comparable = toBeChecked.removeLast();
            for (BlockPos pos : BlockPos.betweenClosed(comparable.offset(-r, -r, -r), comparable.offset(r, r, r))) {
                BlockState state = level.getBlockState(pos);

                if (state.is(targetBlock) && !toBeDestroyed.contains(pos.immutable())) {
                    toBeChecked.add(pos.immutable());
                    toBeDestroyed.add(pos.immutable());
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
