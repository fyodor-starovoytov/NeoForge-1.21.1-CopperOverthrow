package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbility;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.custom.TamTamBlockEntity;
import net.star.copperoverthrow.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TamTamBlock extends BaseEntityBlock implements WeatheringCopper {

    public static final MapCodec<TamTamBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    WeatheringCopper.WeatherState.CODEC.fieldOf("weather_state").forGetter(TamTamBlock::getAge)
            ).apply(instance, TamTamBlock::new)
    );

    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0.0, 1.0, 7, 16.0, 16.0, 9);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(7.0, 1.0, 0, 9.0, 16.0, 16);
    private static final int BLOCK_HEIGHT = 2;
    private static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<WideThinDoubleBlock> PART = EnumProperty.create("part", WideThinDoubleBlock.class);
    private final WeatheringCopper.WeatherState weatherState;

    public TamTamBlock(Properties properties, WeatherState weatherState) {

        super(properties);
        this.weatherState = weatherState;
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH)
        );
    }


    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();

        int requiredUpAirBlocks = BLOCK_HEIGHT - 1;

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
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TamTamBlockEntity(pos, state);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        Direction sideDir = facing.getClockWise();
        level.setBlock(pos.below(), state.setValue(PART, WideThinDoubleBlock.BOTTOM), 3);
        level.setBlock(pos.relative(sideDir).below(), state.setValue(PART, WideThinDoubleBlock.BOTTOM_CLOCKWISE), 3);
        level.setBlock(pos.relative(sideDir), state.setValue(PART, WideThinDoubleBlock.TOP_CLOCKWISE), 3);
    }

    @Override
    protected boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
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
    protected @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        WideThinDoubleBlock part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        Direction sideDir = facing.getClockWise();
        Direction counterSideDir = facing.getCounterClockWise();

        boolean isValid = switch (part) {
            case MAIN_TOP -> {
                BlockPos clockwisePos = pos.relative(sideDir);
                BlockPos bottomPos = pos.below();
                BlockPos bottomClockwisePos = clockwisePos.below();

                yield level.getBlockState(clockwisePos).is(this) &&
                        level.getBlockState(bottomPos).is(this) &&
                        level.getBlockState(bottomClockwisePos).is(this);
            }
            case TOP_CLOCKWISE -> {
                BlockPos counterClockwisePos = pos.relative(counterSideDir);
                BlockPos belowPos = pos.below();
                BlockPos bottomClockwisePos = counterClockwisePos.below();

                yield level.getBlockState(counterClockwisePos).is(this) &&
                        level.getBlockState(belowPos).is(this) &&
                        level.getBlockState(bottomClockwisePos).is(this);
            }
            case BOTTOM -> {
                BlockPos abovePos = pos.above();
                BlockPos clockwisePos = pos.relative(sideDir);
                BlockPos bottomClockwisePos = clockwisePos.above();
                yield level.getBlockState(abovePos).is(this) &&
                        level.getBlockState(pos.relative(sideDir)).is(this) &&
                        level.getBlockState(pos.relative(sideDir).above()).is(this);
            }
            case BOTTOM_CLOCKWISE -> {
                yield level.getBlockState(pos.relative(counterSideDir)).is(this) &&
                        level.getBlockState(pos.above()).is(this) &&
                        level.getBlockState(pos.relative(counterSideDir).above()).is(this);
            }
        };

        if (!isValid) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context, @NotNull ItemAbility itemAbility, boolean simulate) {
        BlockState modifiedState = super.getToolModifiedState(state, context, itemAbility, simulate);

        if (modifiedState != null && !simulate) {
            Level level = context.getLevel();
            BlockPos clickedPos = context.getClickedPos();
            propagateBlockChange(level, clickedPos, state, modifiedState.getBlock());
        }

        return modifiedState;
    }

    public void propagateBlockChange(Level level, BlockPos clickedPos, BlockState currentState, Block targetBlock) {
        WideThinDoubleBlock currentPart = currentState.getValue(PART);
        Direction facing = currentState.getValue(FACING);
        Direction sideDir = facing.getClockWise();
        Direction counterSideDir = facing.getCounterClockWise();

        BlockPos mainTopPos = switch (currentPart) {
            case MAIN_TOP -> clickedPos;
            case TOP_CLOCKWISE -> clickedPos.relative(counterSideDir);
            case BOTTOM -> clickedPos.above();
            case BOTTOM_CLOCKWISE -> clickedPos.relative(counterSideDir).above();
        };

        BlockPos[] targetPositions = new BlockPos[]{
                mainTopPos,
                mainTopPos.relative(sideDir),
                mainTopPos.below(),
                mainTopPos.relative(sideDir).below()
        };

        WideThinDoubleBlock[] targetParts = new WideThinDoubleBlock[]{
                WideThinDoubleBlock.MAIN_TOP,
                WideThinDoubleBlock.TOP_CLOCKWISE,
                WideThinDoubleBlock.BOTTOM,
                WideThinDoubleBlock.BOTTOM_CLOCKWISE
        };

        for (int i = 0; i < targetPositions.length; i++) {
            BlockPos targetPos = targetPositions[i];
            BlockState existingState = level.getBlockState(targetPos);
            if (existingState.getBlock() instanceof TamTamBlock) {
                BlockState newSegmentState = targetBlock.defaultBlockState()
                        .setValue(FACING, facing)
                        .setValue(PART, targetParts[i]);
                level.setBlock(targetPos, newSegmentState, 3);
            }
        }
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (Math.random()<ServerConfig.TAMTAM_FULL_MOON_VOLUME_CHANCE.get() && level.getMoonPhase() == 0 && level.isNight()){
            level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING_MOON.get(), SoundSource.RECORDS, ServerConfig.TAMTAM_FULL_MOON_VOLUME_RADIUS.get(), 1F);
        }
        this.changeOverTime(state, level, pos, random);
    }

    @Override
    public WeatheringCopper.@NotNull WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, BlockHitResult hitResult) {
        Direction direction = hitResult.getDirection();
        Direction facing = state.getValue(FACING);
        Direction counterSideDir = facing.getCounterClockWise();
        WideThinDoubleBlock part = state.getValue(PART);
        BlockPos mainTopPos = switch (part) {
            case MAIN_TOP -> pos;
            case TOP_CLOCKWISE -> pos.relative(counterSideDir);
            case BOTTOM -> pos.above();
            case BOTTOM_CLOCKWISE -> pos.relative(counterSideDir).above();
        };
        if (!level.isClientSide && level.getBlockEntity(mainTopPos) instanceof TamTamBlockEntity blockEntity && !blockEntity.swinging && isProperHit(state, direction) && player.getMainHandItem().isEmpty()) {
            blockEntity.startSwing(hitResult.getDirection());
            playSound(level, pos, ServerConfig.TAMTAM_BASIC_VOLUME_RADIUS.get().floatValue());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onProjectileHit(Level level, @NotNull BlockState state, BlockHitResult hit, @NotNull Projectile projectile) {
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
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.@NotNull Builder builder) {
        if (state.getValue(PART) != WideThinDoubleBlock.MAIN_TOP) {
            return Collections.emptyList();
        }
        return super.getDrops(state, builder);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
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
                if (!pos.equals(mainTopPos)) {
                    BlockState mainState = level.getBlockState(mainTopPos);
                    if (mainState.is(this)) {
                        LootParams.Builder builder = new LootParams.Builder((ServerLevel) level)
                                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                                .withParameter(LootContextParams.TOOL, player.getMainHandItem())
                                .withOptionalParameter(LootContextParams.THIS_ENTITY, player);

                        List<ItemStack> drops = mainState.getDrops(builder);
                        for (ItemStack drop : drops) {
                            Block.popResource(level, pos, drop);
                        }
                    }
                }

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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.TAMTAM_BE.get(), TamTamBlockEntity::tick);
    }

    private void playSound(Level level, BlockPos pos, float volume){
        if (level.getBlockState(pos).getBlock().equals(ModBlocks.EXPOSED_TAMTAM.get()) || level.getBlockState(pos).getBlock().equals(ModBlocks.WAXED_EXPOSED_TAMTAM.get())) {
            level.playSound(null, pos, ModSounds.EXPOSED_TAM_TAM_PLAYING.get(), SoundSource.RECORDS, volume, 1F);
        }
        else if (level.getBlockState(pos).getBlock().equals(ModBlocks.WEATHERED_TAMTAM.get()) || level.getBlockState(pos).getBlock().equals(ModBlocks.WAXED_WEATHERED_TAMTAM.get())) {
            level.playSound(null, pos, ModSounds.WEATHERED_TAM_TAM_PLAYING.get(), SoundSource.RECORDS, volume, 1F);
        }
        else if (level.getBlockState(pos).getBlock().equals(ModBlocks.OXIDIZED_TAMTAM.get()) || level.getBlockState(pos).getBlock().equals(ModBlocks.WAXED_OXIDIZED_TAMTAM.get())) {
            level.playSound(null, pos, ModSounds.OXIDIZED_TAM_TAM_PLAYING.get(), SoundSource.RECORDS, volume - 1, 1F);
        }
        else if (Math.random()<0.07) {
            level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING_RARE.get(), SoundSource.RECORDS, volume, 1F);
        }
        else {
            level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING.get(), SoundSource.RECORDS, volume, 1F);
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
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        Rotation rotation = mirror.getRotation(state.getValue(FACING));
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
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
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }


    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.medium_pitch.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.sounding.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.long_crash_sounding.tooltip"));


        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.medium_pitch.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
