package net.star.copperoverthrow.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;


public class CopperScaffoldingBlock extends ScaffoldingBlock implements WeatheringCopper, SimpleWaterloggedBlock{

    private final WeatherState weatherState;

    public CopperScaffoldingBlock(Properties properties, WeatherState weatherState) {
        super(properties);
        this.weatherState = weatherState;
    }


    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    @Override
    public @NotNull WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public boolean isScaffolding(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        return true;
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        int i = getDistance(level, blockpos);
        return this.defaultBlockState()
                .setValue(WATERLOGGED, level.getFluidState(blockpos).getType() == Fluids.WATER)
                .setValue(DISTANCE, i)
                .setValue(BOTTOM, this.isBottom(level, blockpos, i));
    }

    @Override
    protected void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        int i = getDistance(level, pos);
        BlockState blockstate = state.setValue(DISTANCE, i).setValue(BOTTOM, this.isBottom(level, pos, i));
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
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return getDistance(level, pos) < 7;
    }

    private boolean isBottom(BlockGetter level, BlockPos pos, int distance) {
        return distance > 0 && !level.getBlockState(pos.below()).is(this);
    }

    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable().move(Direction.DOWN);
        BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
        int i = 7;
        if (blockstate.getBlock() instanceof  ScaffoldingBlock) {
            i = blockstate.getValue(DISTANCE);
        } else if (blockstate.isFaceSturdy(level, blockpos$mutableblockpos, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos.setWithOffset(pos, direction));
            if (blockstate1.getBlock() instanceof  ScaffoldingBlock) {
                i = Math.min(i, blockstate1.getValue(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }

}
