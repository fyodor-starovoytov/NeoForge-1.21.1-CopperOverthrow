package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class LogStripperBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final MapCodec<LogStripperBlock> CODEC = simpleCodec(LogStripperBlock::new);

    public LogStripperBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogStripperBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {

        if (state.getBlock() != newState.getBlock()){
            if (level.getBlockEntity(pos) instanceof LogStripperBlockEntity logStripperBlockEntity){
                Containers.dropContents(level, pos, logStripperBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (level.getBlockEntity(pos) instanceof LogStripperBlockEntity logStripperBlockEntity){

            EquipmentSlot slot = null;
            ItemStack axeStack = ItemStack.EMPTY;

            if (player.getMainHandItem().is(ItemTags.AXES)) {
                slot = EquipmentSlot.MAINHAND;
                axeStack = player.getMainHandItem();
            } else if (player.getOffhandItem().is(ItemTags.AXES)) {
                slot = EquipmentSlot.OFFHAND;
                axeStack = player.getOffhandItem();
            }

            if (isBlockStrippable(logStripperBlockEntity.getItem(0)) && !axeStack.isEmpty()){
                int toDamage = logStripperBlockEntity.getItem(0).getCount() / 2;
                axeStack.hurtAndBreak(Math.max(toDamage, 1), player, (slot));

                logStripperBlockEntity.setItem(0, getStrippedBlock(logStripperBlockEntity.getItem(0)));

                level.playSound(player, pos, SoundEvents.COPPER_BREAK, SoundSource.BLOCKS, 1, 1f);
            }

            if (logStripperBlockEntity.isEmpty() && isBlockStrippable(stack)){
                logStripperBlockEntity.setItem(0, stack);
                stack.shrink(stack.getCount());
                level.playSound(player, pos, SoundEvents.COPPER_HIT, SoundSource.BLOCKS, 1, 2f);
            }

            else if (stack.isEmpty()){
                ItemStack stackInside = logStripperBlockEntity.getItem(0);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackInside);
                logStripperBlockEntity.clearContent();
                level.playSound(player, pos, SoundEvents.COPPER_HIT, SoundSource.BLOCKS, 1, 1f);
            }

        }
        return ItemInteractionResult.SUCCESS;
    }

    public boolean isBlockStrippable(ItemStack stack) {

        if (stack.getItem() instanceof BlockItem blockItem) {

            BlockState state = blockItem.getBlock().defaultBlockState();

            Holder<Block> blockHolder = state.getBlockHolder();
            Strippable strippableData = blockHolder.getData(NeoForgeDataMaps.STRIPPABLES);

            return strippableData != null;
        }
        return false;
    }

    public ItemStack getStrippedBlock(ItemStack stack) {

        if (stack.getItem() instanceof BlockItem blockItem) {
            int count = stack.getCount();

            BlockState state = blockItem.getBlock().defaultBlockState();
            Holder<Block> blockHolder = state.getBlockHolder();
            Strippable strippableData = blockHolder.getData(NeoForgeDataMaps.STRIPPABLES);

            return new ItemStack(strippableData.strippedBlock().asItem()).copyWithCount(count);
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_placed.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.log_stripper_block.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
