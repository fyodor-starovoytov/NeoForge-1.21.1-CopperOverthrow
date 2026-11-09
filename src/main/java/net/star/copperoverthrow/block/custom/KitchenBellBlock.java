package net.star.copperoverthrow.block.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.custom.KitchenBellBlockEntity;
import net.star.copperoverthrow.util.ModBlockModifiers;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class KitchenBellBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, WeatheringCopper {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape SHAPE_BASE = Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0);
    protected static final VoxelShape SHAPE_HAT = Block.box(4.0, 3.0, 4.0, 12.0, 9.0, 12.0);
    private final int TICKS_TO_STAY_PRESSED;
    private final Supplier<SoundEvent> PRESS_SOUND;
    private final String TOOLTIP_SOUNDING;
    private final WeatherState weatherState;


    public KitchenBellBlock(BlockSetType type, int ticksToStayPressed, Properties properties, String tooltip_sounding, Supplier<SoundEvent> pressSound, WeatherState weatherState) {
        super(properties.sound(type.soundType()));
        this.registerDefaultState(
                this.stateDefinition.any().setValue(POWERED, Boolean.FALSE)
        );
        this.TICKS_TO_STAY_PRESSED = ticksToStayPressed;
        this.PRESS_SOUND = pressSound;
        this.TOOLTIP_SOUNDING = tooltip_sounding;
        this.weatherState = weatherState;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.or(SHAPE_BASE, SHAPE_HAT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(POWERED)) {
            return InteractionResult.CONSUME;
        }
        if (!level.isClientSide
                && level.getBlockEntity(pos) instanceof KitchenBellBlockEntity blockEntity
                && !(player.getMainHandItem().getItem() instanceof AxeItem)
                && !(player.getMainHandItem().getItem() instanceof HoneycombItem)
                && !(player.getOffhandItem().getItem() instanceof AxeItem)
                && !(player.getOffhandItem().getItem() instanceof HoneycombItem)) {
            blockEntity.startSwing(hit.getDirection());

            float finalVolume = (float) (ServerConfig.DESK_BELL_VOLUME_RAIDUS.get() * 1);
            level.playSound(null,
                    pos,
                    PRESS_SOUND.get(),
                    SoundSource.RECORDS,
                    finalVolume,
                    1);

            this.press(state, level, pos, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onProjectileHit(Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos pos = hit.getBlockPos();
            if (projectile.mayInteract(level, pos)
                    && projectile.mayInteract(level, pos)
                    && projectile.getDeltaMovement().length() > 0.3
                    && level.getBlockEntity(pos) instanceof KitchenBellBlockEntity blockEntity
            ) {
                blockEntity.startSwing(hit.getDirection());
                float finalVolume = 0.2f * (float) projectile.getDeltaMovement().length();
                level.playSound(null,
                        pos,
                        PRESS_SOUND.get(),
                        SoundSource.RECORDS,
                        finalVolume,
                        1);
            }
        }
    }

    private void press(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE), 3);
        this.updateNeighbours(level, pos);
        level.scheduleTick(pos, this, this.TICKS_TO_STAY_PRESSED + (int) jammed(this.TICKS_TO_STAY_PRESSED));
        level.gameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
    }

    private float jammed(int press_ticks){
        float chanceToBeJammed = ((float) press_ticks) / 100 / 5;
        if (Math.random() < chanceToBeJammed && ServerConfig.JAMMING.get()){
            return (float) 200 / press_ticks;
        }
        return 0;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.KITCHEN_BELL_BE.get(), KitchenBellBlockEntity::tick);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            if (state.getValue(POWERED)) {
                this.updateNeighbours(level, pos);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public int getSignal(BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
        return blockState.getValue(POWERED) && Direction.DOWN == side ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(POWERED)) {
            this.checkPressed(state, level, pos);
        }
    }

    protected void checkPressed(BlockState state, Level level, BlockPos pos) {
        AbstractArrow abstractarrow = level.getEntitiesOfClass(AbstractArrow.class, state.getShape(level, pos).bounds().move(pos)).stream().findFirst().orElse(null);
        boolean flag = abstractarrow != null;
        boolean flag1 = state.getValue(POWERED);
        if (flag != flag1) {
            level.setBlock(pos, state.setValue(POWERED, flag), 3);
            this.updateNeighbours(level, pos);
            level.gameEvent(abstractarrow, flag ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        }

        if (flag) {
            level.scheduleTick(new BlockPos(pos), this, this.TICKS_TO_STAY_PRESSED);
        }
    }

    private void updateNeighbours(Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.relative(Direction.DOWN), this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new KitchenBellBlockEntity(blockPos, blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.randomTick(state, level, pos, random);
    }

    @Override
    public @NotNull WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.copperoverthrow.high_pitch.tooltip"));
            tooltip.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.copperoverthrow.sounding.tooltip"));
            tooltip.add(Component.translatable(TOOLTIP_SOUNDING));

        }
        else {
            tooltip.add(Component.translatable("tooltip.copperoverthrow.high_pitch.tooltip"));
            tooltip.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public Optional<BlockState> getNext(BlockState state) {
        return java.util.Optional.ofNullable(ModBlockModifiers.OXIDIZABLES.get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }
}
