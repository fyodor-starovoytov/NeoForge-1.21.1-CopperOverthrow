package net.star.copperoverthrow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HologramBlock extends ScaffoldingBlock {
    public static final BooleanProperty PLACED_BY_RULER = BooleanProperty.create("placed_by_ruler");
    public static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);


    public HologramBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(BOTTOM, Boolean.valueOf(false))
                .setValue(DISTANCE, Integer.valueOf(0))
                .setValue(PLACED_BY_RULER, false));
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.is(ModBlocks.INVERTED_HOLOGRAM_BLOCK) ? Shapes.block() : Shapes.empty();
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return true;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }

    @Override
    public boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PLACED_BY_RULER);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.@NotNull Builder builder) {
        if (state.getValue(PLACED_BY_RULER)) {
            return Collections.emptyList();
        }
        return super.getDrops(state, builder);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return state;
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return true;
    }
}

