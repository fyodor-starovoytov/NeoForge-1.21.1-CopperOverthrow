package net.star.copperoverthrow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.antlr.v4.runtime.misc.NotNull;


public class ConductBlock extends PoweredBlock {

    public static int MIN_LIFESPAN_TICKS = 5;
    public static int MAX_LIFESPAN_TICKS = 15;

    public ConductBlock(Properties properties) {
        super(properties);
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide && !oldState.is(state.getBlock())) {
            level.scheduleTick(pos, this, RANDOM_LIFESPAN_TICKS());
        }
    }



    @Override
    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    public static int RANDOM_LIFESPAN_TICKS() {
        return (int) (Math.random() * (MAX_LIFESPAN_TICKS - MIN_LIFESPAN_TICKS + 1)) + MIN_LIFESPAN_TICKS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isClientSide) {
            double d0 = pos.getX() + random.nextDouble();
            double d1 = pos.getY() + random.nextDouble();
            double d2 = pos.getZ() + random.nextDouble();

            level.addParticle(ParticleTypes.ELECTRIC_SPARK, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
