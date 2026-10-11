package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HangingRootsBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.block.entity.custom.RedWindChimeBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractWindChimeBlock extends BaseEntityBlock {
    public static final MapCodec<RedWindChimeBlock> CODEC = simpleCodec(RedWindChimeBlock::new);
    protected static final VoxelShape SHAPE = Block.box(4, 4.0, 4.0, 12.0, 16.0, 12.0);

    public AbstractWindChimeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState p_153342_, BlockGetter p_153343_, BlockPos p_153344_, CollisionContext p_153345_) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RedWindChimeBlockEntity(pos, state);
    }

    @Override
    protected boolean canSurvive(BlockState p_153347_, LevelReader p_153348_, BlockPos p_153349_) {
        BlockPos blockpos = p_153349_.above();
        BlockState blockstate = p_153348_.getBlockState(blockpos);
        return blockstate.isFaceSturdy(p_153348_, blockpos, Direction.DOWN);
    }

    @Override
    protected BlockState updateShape(BlockState p_153351_, Direction p_153352_, BlockState p_153353_, LevelAccessor p_153354_, BlockPos p_153355_, BlockPos p_153356_) {
        if (p_153352_ == Direction.UP && !this.canSurvive(p_153351_, p_153354_, p_153355_)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(p_153351_, p_153352_, p_153353_, p_153354_, p_153355_, p_153356_);
    }

}
