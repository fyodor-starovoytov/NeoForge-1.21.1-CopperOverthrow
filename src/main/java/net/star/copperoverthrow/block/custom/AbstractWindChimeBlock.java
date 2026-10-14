package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.ClientConfig;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.custom.RedWindChimeBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class AbstractWindChimeBlock extends BaseEntityBlock {
    public static final MapCodec<AbstractWindChimeBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("press_sound").forGetter(block -> block.CHIME_SOUND.get())
            ).apply(instance, (properties, sound) -> new AbstractWindChimeBlock(properties, () -> sound))
    );
    private final Supplier<SoundEvent> CHIME_SOUND;
    public static final BooleanProperty UPPER = BooleanProperty.create("upper");

    protected static final VoxelShape TOP_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    protected static final VoxelShape BOTTOM_SHAPE = Block.box(4.0, 10.0, 4.0, 12.0, 16.0, 12.0);

    public AbstractWindChimeBlock(Properties properties, Supplier<SoundEvent> chimeSound) {
        super(properties);
        this.CHIME_SOUND = chimeSound;
        this.registerDefaultState(this.stateDefinition.any().setValue(UPPER, true));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(UPPER) ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UPPER);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(UPPER) ? new RedWindChimeBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();

        if (blockpos.getY() > level.getMinBuildHeight() && level.getBlockState(blockpos.below()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(UPPER, true);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide()) {
            BlockPos belowPos = pos.below();
            level.setBlock(belowPos, state.setValue(UPPER, false), 3);
            level.blockUpdated(pos, Blocks.AIR);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(UPPER)) {
            BlockPos abovePos = pos.above();
            return level.getBlockState(abovePos).isFaceSturdy(level, abovePos, Direction.DOWN);
        } else {
            BlockState aboveState = level.getBlockState(pos.above());
            return aboveState.is(this) && aboveState.getValue(UPPER);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.playSound(null, pos, CHIME_SOUND.get(), SoundSource.RECORDS,1, 1);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (!level.isClientSide()) {
                boolean isUpper = state.getValue(UPPER);
                BlockPos otherPos = isUpper ? pos.below() : pos.above();
                BlockState otherState = level.getBlockState(otherPos);

                if (otherState.is(this)) {
                    level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(null, 2001, otherPos, Block.getId(otherState));
                    level.playSound(null, otherPos, CHIME_SOUND.get(), SoundSource.RECORDS,1, 1);
                }
            }}
        super.onRemove(state, level, pos, newState, isMoving);
    }

    public void idlePlayChimeSound(Level level, BlockPos pos){
        double chancePerTick = ClientConfig.WIND_CHIME_IDLE_SOUND_CHANCE.get();

        float randomPitch = 0.8f + level.random.nextFloat() * 0.4f;

        if (level.isThundering()){
            if (Math.random() < chancePerTick * 4){
                level.playLocalSound(pos, CHIME_SOUND.get(), SoundSource.RECORDS, 1, randomPitch, false);
            }
        } else if (Math.random() < chancePerTick) {
            level.playLocalSound(pos, CHIME_SOUND.get(), SoundSource.RECORDS, 1, randomPitch, false);
        }
    }
}