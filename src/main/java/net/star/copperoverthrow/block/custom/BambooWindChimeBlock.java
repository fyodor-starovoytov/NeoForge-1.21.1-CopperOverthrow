package net.star.copperoverthrow.block.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.custom.BambooWindChimeBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BambooWindChimeBlock extends AbstractWindChimeBlock {

    public BambooWindChimeBlock(Properties properties, Supplier<SoundEvent> chimeSound) {
        super(properties, chimeSound);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(UPPER) ?  new BambooWindChimeBlockEntity(pos, state) : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.BAMBOO_WIND_CHIME_BE.get(), BambooWindChimeBlockEntity::tick);
    }

    @Override
    public void entityInside(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (level.isClientSide()) return;

        BlockPos targetPos = state.getValue(UPPER) ? pos : pos.above();

        if (level.getBlockEntity(targetPos) instanceof BambooWindChimeBlockEntity blockEntity) {
            if (!blockEntity.swinging) {
                double dx = entity.getX() - entity.xo;
                double dz = entity.getZ() - entity.zo;
                boolean entityIsMoving = (dx * dx + dz * dz) > 0.001D;

                double volume = (Math.abs(dx) + Math.abs(dz)) * 30;

                if (entityIsMoving) {
                    int chimeCount = level.random.nextInt(3, 8);
                    blockEntity.triggerChimeSequence(chimeCount, volume);
                    blockEntity.startSwing(dx, dz);
                }
            }

            blockEntity.onOpenAir(isBlockObstructed(pos, level));

        }
    }

    @Override
    protected void onExplosionHit(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Explosion explosion, @NotNull BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (level.isClientSide()) return;

        BlockPos targetPos = state.getValue(UPPER) ? pos : pos.above();

        if (level.getBlockEntity(targetPos) instanceof BambooWindChimeBlockEntity blockEntity) {
            if (!blockEntity.swinging) {
                blockEntity.startSwing(explosion.radius()/10, explosion.radius()/10);
                int chimeCount = level.random.nextInt(3, 6);
                blockEntity.triggerChimeSequence(chimeCount, explosion.radius()/3);
            }

            blockEntity.onOpenAir(isBlockObstructed(pos, level));
        }
    }

    private boolean isBlockObstructed(BlockPos pos, Level level){

        Vec3 vec = Vec3.atCenterOf(pos);
        Vec3 vecEast = Vec3.atCenterOf(pos.east(ServerConfig.CHIME_CHECK_RANGE.get()));
        Vec3 vecSouth = Vec3.atCenterOf(pos.east(ServerConfig.CHIME_CHECK_RANGE.get()));
        Vec3 vecWest = Vec3.atCenterOf(pos.east(ServerConfig.CHIME_CHECK_RANGE.get()));
        Vec3 vecNorth = Vec3.atCenterOf(pos.east(ServerConfig.CHIME_CHECK_RANGE.get()));

        ClipContext contextEast = new ClipContext(
                vec, vecEast,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                CollisionContext.empty()
        );
        ClipContext contextSouth = new ClipContext(
                vec, vecSouth,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                CollisionContext.empty()
        );
        ClipContext contextNorth = new ClipContext(
                vec, vecNorth,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                CollisionContext.empty()
        );
        ClipContext contextWest = new ClipContext(
                vec, vecWest,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                CollisionContext.empty()
        );

        BlockHitResult hitSouth = level.clip(contextSouth);
        BlockHitResult hitNorth = level.clip(contextNorth);
        BlockHitResult hitWest = level.clip(contextWest);
        BlockHitResult hitEast = level.clip(contextEast);
        boolean isObstructed = hitSouth.getType() != HitResult.Type.MISS && hitEast.getType() != HitResult.Type.MISS && hitWest.getType() != HitResult.Type.MISS && hitNorth.getType() != HitResult.Type.MISS;

        return !isObstructed;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.medium_pitch.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.sounding.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.lower_resonance_sounding.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.medium_pitch.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}