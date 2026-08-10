package net.star.copperoverthrow.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.block.ModBlocks;


public class CopperScaffoldingBlock extends ScaffoldingBlock{

  /*  private static final VoxelShape STABLE_SHAPE;
    private static final VoxelShape UNSTABLE_SHAPE;
    private static final VoxelShape UNSTABLE_SHAPE_BOTTOM = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
*/
    public CopperScaffoldingBlock(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        int i = getDistance(level, blockpos);
        return this.defaultBlockState()
                .setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockpos).getType() == Fluids.WATER))
                .setValue(DISTANCE, Integer.valueOf(i))
                .setValue(BOTTOM, Boolean.valueOf(this.isBottom(level, blockpos, i)));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = getDistance(level, pos);
        BlockState blockstate = state.setValue(DISTANCE, Integer.valueOf(i)).setValue(BOTTOM, Boolean.valueOf(this.isBottom(level, pos, i)));
        if (blockstate.getValue(DISTANCE) == 7) {
            if (state.getValue(DISTANCE) == 7) {
                FallingBlockEntity.fall(level, pos, blockstate);
            } else {
                level.destroyBlock(pos, true);
            }
        } else if (state != blockstate) {
            level.setBlock(pos, blockstate, 3);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return getDistance(level, pos) < 7;
    }

    private boolean isBottom(BlockGetter level, BlockPos pos, int distance) {
        return distance > 0 && !level.getBlockState(pos.below()).is(this);
    }

    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable().move(Direction.DOWN);
        BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
        int i = 7;
        if (blockstate.is(ModBlocks.COPPER_SCAFFOLDING)) {
            i = blockstate.getValue(DISTANCE);
        } else if (blockstate.isFaceSturdy(level, blockpos$mutableblockpos, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos.setWithOffset(pos, direction));
            if (blockstate1.is(ModBlocks.COPPER_SCAFFOLDING)) {
                i = Math.min(i, blockstate1.getValue(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }

}
