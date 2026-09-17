package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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
    private static final VoxelShape SHAPE = Block.box(0.0, 1.0, 7, 16.0, 16.0, 9);

    public TamTamBlock(Properties properties) {
        super(properties);
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
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof TamTamBlockEntity blockEntity && !blockEntity.swinging) {
            blockEntity.startSwing(hitResult.getDirection());
            playSound(level, pos, ServerConfig.TAMTAM_BASIC_VOLUME_RADIUS.get().floatValue());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide && level.getBlockEntity(hit.getBlockPos()) instanceof TamTamBlockEntity blockEntity && !blockEntity.swinging) {
            BlockPos pos = hit.getBlockPos();
            if (projectile.mayInteract(level, pos)
                    && projectile.mayBreak(level)
                    && projectile.getDeltaMovement().length() > 0.6) {
                float finalVolume = (float) (ServerConfig.TAMTAM_BASIC_VOLUME_RADIUS.get() * 0.2 * projectile.getDeltaMovement().length());
                playSound(level, pos, finalVolume);
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.SWINGING_BE.get(), TamTamBlockEntity::tick);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
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
}
