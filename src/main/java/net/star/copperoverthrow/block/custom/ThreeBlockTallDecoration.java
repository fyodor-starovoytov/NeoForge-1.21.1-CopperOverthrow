package net.star.copperoverthrow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ThreeBlockTallDecoration extends Block {
    public static final EnumProperty<TripleBlockPart> PART = EnumProperty.create("part", TripleBlockPart.class);

    public ThreeBlockTallDecoration(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, TripleBlockPart.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        if (pos.getY() < level.getMaxBuildHeight() - 2
                && level.getBlockState(pos.above(1)).canBeReplaced(context)
                && level.getBlockState(pos.above(2)).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(PART, TripleBlockPart.BOTTOM);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(1), state.setValue(PART, TripleBlockPart.MIDDLE), 3);
        level.setBlock(pos.above(2), state.setValue(PART, TripleBlockPart.TOP), 3);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        TripleBlockPart part = state.getValue(PART);
        if (part == TripleBlockPart.MIDDLE) {
            BlockState bottom = level.getBlockState(pos.below());
            return bottom.is(this) && bottom.getValue(PART) == TripleBlockPart.BOTTOM;
        } else if (part == TripleBlockPart.TOP) {
            BlockState middle = level.getBlockState(pos.below());
            return middle.is(this) && middle.getValue(PART) == TripleBlockPart.MIDDLE;
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        TripleBlockPart part = state.getValue(PART);

        if (direction.getAxis() == Direction.Axis.Y) {
            if (part == TripleBlockPart.BOTTOM && direction == Direction.UP) {
                if (!neighborState.is(this) || neighborState.getValue(PART) != TripleBlockPart.MIDDLE) {
                    return Blocks.AIR.defaultBlockState();
                }
            } else if (part == TripleBlockPart.MIDDLE) {
                if (direction == Direction.DOWN && (!neighborState.is(this) || neighborState.getValue(PART) != TripleBlockPart.BOTTOM)) {
                    return Blocks.AIR.defaultBlockState();
                }
                if (direction == Direction.UP && (!neighborState.is(this) || neighborState.getValue(PART) != TripleBlockPart.TOP)) {
                    return Blocks.AIR.defaultBlockState();
                }
            } else if (part == TripleBlockPart.TOP && direction == Direction.DOWN) {
                if (!neighborState.is(this) || neighborState.getValue(PART) != TripleBlockPart.MIDDLE) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            TripleBlockPart part = state.getValue(PART);
            if (part == TripleBlockPart.TOP) {
                preventCreativeDrop(level, pos.below(2));
                preventCreativeDrop(level, pos.below(1));
            } else if (part == TripleBlockPart.MIDDLE) {
                preventCreativeDrop(level, pos.below(1));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    private void preventCreativeDrop(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(this)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
        }
    }

    public enum TripleBlockPart implements StringRepresentable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        private final String name;

        TripleBlockPart(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }


}
