package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class KitchenBellBlock extends BaseEntityBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    private final int ticksToStayPressed;

    public static final MapCodec<KitchenBellBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.INT.fieldOf("ticks_to_stay_pressed").forGetter(block -> block.ticksToStayPressed),
                        propertiesCodec()
                        //WeatheringCopper.WeatherState.CODEC.fieldOf("weather_state").forGetter(TamTamBlock::getAge)
                ).apply(instance, (ticks, props) -> new KitchenBellBlock(BlockSetType.COPPER, ticks, props))
    );

    public KitchenBellBlock(BlockSetType type, int ticksToStayPressed, BlockBehaviour.Properties properties) {
        super(properties.sound(type.soundType()));
        this.registerDefaultState(
                this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false))
        );
        this.ticksToStayPressed = ticksToStayPressed;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(POWERED)) {
            return InteractionResult.CONSUME;
        } else {
            this.press(state, level, pos, player);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    protected void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (explosion.canTriggerBlocks() && !state.getValue(POWERED)) {
            this.press(state, level, pos, null);
        }

        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }

    public void press(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(true)), 3);
        this.updateNeighbours(state, level, pos);
        level.scheduleTick(pos, this, this.ticksToStayPressed);
        level.gameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            if (state.getValue(POWERED)) {
                this.updateNeighbours(state, level, pos);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) && Direction.DOWN == side ? 15 : 0;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            this.checkPressed(state, level, pos);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && !state.getValue(POWERED)) {
            this.checkPressed(state, level, pos);
        }
    }

    protected void checkPressed(BlockState state, Level level, BlockPos pos) {
        AbstractArrow abstractarrow = level.getEntitiesOfClass(AbstractArrow.class, state.getShape(level, pos).bounds().move(pos)).stream().findFirst().orElse(null);
        boolean flag = abstractarrow != null;
        boolean flag1 = state.getValue(POWERED);
        if (flag != flag1) {
            level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(flag)), 3);
            this.updateNeighbours(state, level, pos);
            level.gameEvent(abstractarrow, flag ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        }

        if (flag) {
            level.scheduleTick(new BlockPos(pos), this, this.ticksToStayPressed);
        }
    }

    private void updateNeighbours(BlockState state, Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.relative(Direction.DOWN), this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
