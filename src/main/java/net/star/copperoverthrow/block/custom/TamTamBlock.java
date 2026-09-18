package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.custom.TamTamBlockEntity;
import net.star.copperoverthrow.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

public class TamTamBlock extends BaseEntityBlock implements Instruments {
    public static final MapCodec<TamTamBlock> CODEC = simpleCodec(TamTamBlock::new);
    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0.0, 1.0, 7, 16.0, 16.0, 9);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(7.0, 1.0, 0, 9.0, 16.0, 16);
    private static final int BLOCK_WIDTH = 2;
    private static final int BLOCK_HEIGHT = 2;
    private static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public TamTamBlock(Properties properties) {

        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH)
        );
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();

        int requiredUpAirBlocks = this.BLOCK_HEIGHT - 1;

        if (pos.getY() + requiredUpAirBlocks > level.getMaxBuildHeight()) {
            return null;
        }

        Direction sideDir = facing.getClockWise();

        if (!level.getBlockState(pos.below()).canBeReplaced(context) ||
                !level.getBlockState(pos.relative(sideDir)).canBeReplaced(context) ||
                !level.getBlockState(pos.relative(sideDir).below()).canBeReplaced(context)) {
            return null;
        }

        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TamTamBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }








    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        Direction direction = hitResult.getDirection();
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof TamTamBlockEntity blockEntity && !blockEntity.swinging && isProperHit(state, direction)) {
            blockEntity.startSwing(hitResult.getDirection());
            playSound(level, pos, ServerConfig.TAMTAM_BASIC_VOLUME_RADIUS.get().floatValue());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        Direction direction = hit.getDirection();
        BlockPos pos = hit.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        if (!level.isClientSide && level.getBlockEntity(hit.getBlockPos()) instanceof TamTamBlockEntity blockEntity && !blockEntity.swinging && isProperHit(blockState, direction)) {
            if (projectile.mayInteract(level, pos)
                    && projectile.mayBreak(level)
                    && projectile.getDeltaMovement().length() > 0.9) {
                float finalVolume = (float) (ServerConfig.TAMTAM_BASIC_VOLUME_RADIUS.get() * 0.2 * projectile.getDeltaMovement().length());
                blockEntity.startSwing(hit.getDirection());
                playSound(level, pos, finalVolume);
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SWINGING_BE.get(), TamTamBlockEntity::tick);
    }

    private void playSound(Level level, BlockPos pos, float volume){
        if (Math.random()<0.05 && level.getMoonPhase() == 0){
            level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING_MOON.get(), SoundSource.BLOCKS, volume, 1F);
        } else if (Math.random()<0.07) {
            level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING_RARE.get(), SoundSource.BLOCKS, volume, 1F);
        }
        else {
            level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING.get(), SoundSource.BLOCKS, volume, 1F);
        }
    }

    private boolean isProperHit(BlockState pos, Direction p_direction) {
        if (p_direction.getAxis() != Direction.Axis.Y) {
            Direction direction = pos.getValue(FACING);
            return direction.getAxis() == p_direction.getAxis();
        } else {
            return false;
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HorizontalDirectionalBlock.FACING).getAxis() == Direction.Axis.Z) {
            //The not default texture
            return SHAPE_NORTH_SOUTH;
        }
        else{
            return SHAPE_EAST_WEST;
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.above(), Direction.DOWN);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

}
