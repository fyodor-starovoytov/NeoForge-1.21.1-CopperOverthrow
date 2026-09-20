package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.custom.TamTamBlockEntity;
import net.star.copperoverthrow.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TamTamBlock extends BaseEntityBlock implements Instruments {
    public static final MapCodec<TamTamBlock> CODEC = simpleCodec(TamTamBlock::new);
    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0.0, 1.0, 7, 16.0, 16.0, 9);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(7.0, 1.0, 0, 9.0, 16.0, 16);
    private static final int BLOCK_HEIGHT = 2;
    private static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<WideThinDoubleBlock> PART = EnumProperty.create("part", WideThinDoubleBlock.class);

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

        return this.defaultBlockState().setValue(FACING, facing).setValue(PART, WideThinDoubleBlock.MAIN_TOP);
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        Direction sideDir = facing.getClockWise();
        level.setBlock(pos.below(), state.setValue(PART, WideThinDoubleBlock.BOTTOM), 3);
        level.setBlock(pos.relative(sideDir).below(), state.setValue(PART, WideThinDoubleBlock.BOTTOM_CLOCKWISE), 3);
        level.setBlock(pos.relative(sideDir), state.setValue(PART, WideThinDoubleBlock.TOP_CLOCKWISE), 3);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        WideThinDoubleBlock part = state.getValue(PART);
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        Direction sideDir = facing.getCounterClockWise();

        if (part == WideThinDoubleBlock.MAIN_TOP){
            return Block.canSupportCenter(level, pos.above(), Direction.DOWN);
        }

        if (part == WideThinDoubleBlock.BOTTOM) {
            BlockState above = level.getBlockState(pos.above());
            return isSameStructure(above) && above.getValue(PART) == WideThinDoubleBlock.MAIN_TOP;
        }

        if (part == WideThinDoubleBlock.BOTTOM_CLOCKWISE) {
            BlockState near = level.getBlockState(pos.relative(sideDir));
            return isSameStructure(near) && near.getValue(PART) == WideThinDoubleBlock.BOTTOM;
        }

        if (part == WideThinDoubleBlock.TOP_CLOCKWISE) {
            BlockState below = level.getBlockState(pos.below());
            return isSameStructure(below) && below.getValue(PART) == WideThinDoubleBlock.BOTTOM_CLOCKWISE;
        }
        return super.canSurvive(state, level, pos);
    }

    private boolean isSameStructure(BlockState state) {
        return state.getBlock() instanceof TamTamBlock;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        WideThinDoubleBlock part = state.getValue(PART);
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        Direction counterSideDir = facing.getCounterClockWise();
        Direction sideDir = facing.getClockWise();

        if (direction.getAxis() == Direction.Axis.Y) {
                if (part == WideThinDoubleBlock.MAIN_TOP && direction == Direction.UP) {
                    if (Block.canSupportCenter(level, pos, direction)) {
                        return Blocks.AIR.defaultBlockState();
                    }
                }
                if (part == WideThinDoubleBlock.BOTTOM && direction == Direction.UP) {
                    if (!isSameStructure(neighborState) || neighborState.getValue(PART) != WideThinDoubleBlock.MAIN_TOP){
                        return Blocks.AIR.defaultBlockState();
                    }
                }

            if (part == WideThinDoubleBlock.TOP_CLOCKWISE && direction == Direction.DOWN) {
                if (!isSameStructure(neighborState) || neighborState.getValue(PART) != WideThinDoubleBlock.BOTTOM_CLOCKWISE){
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }

        if (!(direction.getAxis() == facing.getAxis()) && !(direction.getAxis() == Direction.Axis.Y)) {
            if (part == WideThinDoubleBlock.BOTTOM_CLOCKWISE) {
                if (!isSameStructure(level.getBlockState(pos.relative(counterSideDir))) || level.getBlockState(pos.relative(counterSideDir)).getValue(PART) != WideThinDoubleBlock.BOTTOM) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
            if (part == WideThinDoubleBlock.MAIN_TOP) {
                if (!isSameStructure(level.getBlockState(pos.relative(sideDir))) || level.getBlockState(pos.relative(sideDir)).getValue(PART) != WideThinDoubleBlock.TOP_CLOCKWISE) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        Direction direction = hitResult.getDirection();
        Direction facing = state.getValue(FACING);
        Direction sideDir = facing.getClockWise();
        Direction counterSideDir = facing.getCounterClockWise();
        WideThinDoubleBlock part = state.getValue(PART);
        BlockPos mainTopPos = switch (part) {
            case MAIN_TOP -> pos;
            case TOP_CLOCKWISE -> pos.relative(counterSideDir);
            case BOTTOM -> pos.above();
            case BOTTOM_CLOCKWISE -> pos.relative(counterSideDir).above();
        };
        if (!level.isClientSide && level.getBlockEntity(mainTopPos) instanceof TamTamBlockEntity blockEntity && !blockEntity.swinging && isProperHit(state, direction)) {
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

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        net.minecraft.world.entity.Entity entity = builder.getOptionalParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player && player.isCreative()) {
            return Collections.emptyList();
        }

        if (state.getValue(PART) != WideThinDoubleBlock.MAIN_TOP) {
            return Collections.emptyList();
        }
        return super.getDrops(state, builder);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            WideThinDoubleBlock part = state.getValue(PART);
            Direction facing = state.getValue(FACING);
            Direction sideDir = facing.getClockWise();
            Direction counterSideDir = facing.getCounterClockWise();

            BlockPos mainTopPos = switch (part) {
                case MAIN_TOP -> pos;
                case TOP_CLOCKWISE -> pos.relative(counterSideDir);
                case BOTTOM -> pos.above();
                case BOTTOM_CLOCKWISE -> pos.relative(counterSideDir).above();
            };

            BlockPos[] allParts = new BlockPos[]{
                    mainTopPos,
                    mainTopPos.relative(sideDir),
                    mainTopPos.below(),
                    mainTopPos.relative(sideDir).below()
            };

            if (player.isCreative()) {
                for (BlockPos partPos : allParts) {
                    BlockState targetState = level.getBlockState(partPos);
                    if (targetState.is(this)) {
                        level.setBlock(partPos, Blocks.AIR.defaultBlockState(), 35);
                        level.levelEvent(player, 2001, partPos, Block.getId(targetState));
                    }
                }
            } else {
                // In Survival mode, break sibling parts safely
                for (BlockPos partPos : allParts) {
                    if (!partPos.equals(pos)) {
                        BlockState targetState = level.getBlockState(partPos);
                        if (targetState.is(this)) {
                            level.destroyBlock(partPos, false);
                        }
                    }
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
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
        builder.add(FACING, PART);
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

    public enum WideThinDoubleBlock implements StringRepresentable {
        MAIN_TOP("main_top"),
        BOTTOM("bottom"),
        TOP_CLOCKWISE("top_clockwise"),
        BOTTOM_CLOCKWISE("bottom_clockwise");

        private final String name;

        WideThinDoubleBlock(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
