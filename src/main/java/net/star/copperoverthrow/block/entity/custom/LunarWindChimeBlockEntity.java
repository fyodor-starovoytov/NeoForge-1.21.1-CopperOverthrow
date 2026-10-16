package net.star.copperoverthrow.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LunarWindChimeBlockEntity extends BlockEntity {

    private final Supplier<SoundEvent> CHIME_SOUND = ModSounds.LUNAR_CHIME;
    public int ticks;
    public boolean swinging;
    public double xMotion;
    public double zMotion;
    public double randomX;
    public double randomZ;
    public double randomX2;
    public double randomZ2;
    public double randomX3;
    public double randomZ3;
    private int chimesRemaining = 0;
    private int chimeDelayTicks = 0;
    private double volume = 0.2;
    private boolean willDoIdleSounds = true;

    public LunarWindChimeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LUNAR_WIND_CHIME_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("xMotion", xMotion);
        tag.putDouble("zMotion", zMotion);
        tag.putDouble("randomX", randomX);
        tag.putDouble("randomZ", randomZ);
        tag.putDouble("randomX2", randomX2);
        tag.putDouble("randomZ2", randomZ2);
        tag.putDouble("randomX3", randomX3);
        tag.putDouble("randomZ3", randomZ3);
        tag.putBoolean("swinging", swinging);
        tag.putInt("ticks", ticks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        xMotion = tag.getDouble("xMotion");
        zMotion = tag.getDouble("zMotion");
        randomX = tag.getDouble("randomX");
        randomZ = tag.getDouble("randomZ");
        randomX2 = tag.getDouble("randomX3");
        randomZ2 = tag.getDouble("randomZ3");
        randomX3 = tag.getDouble("randomX2");
        randomZ3 = tag.getDouble("randomZ2");
        swinging = tag.getBoolean("swinging");
        ticks = tag.getInt("ticks");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    public void onOpenAir(boolean isOnOpenAir){
        this.willDoIdleSounds = isOnOpenAir;
    }

    public void startSwing(double xMotion, double zMotion) {
        this.ticks = 0;
        this.swinging = true;
        this.xMotion = xMotion * 10;
        this.zMotion = zMotion * 10;
        this.randomX = Math.random() + zMotion * 2;
        this.randomZ = Math.random() + xMotion * 2;
        this.randomX2 = Math.random() + zMotion * 2;
        this.randomZ2 = Math.random() + xMotion * 2;
        this.randomX3 = Math.random() + zMotion * 2;
        this.randomZ3 = Math.random() + xMotion * 2;


        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void triggerChimeSequence(int chimesCount, double volume) {
        this.chimesRemaining = chimesCount;
        this.chimeDelayTicks = 0;
        this.volume = volume;
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.ticks = 0;
            this.swinging = true;
            return true;
        }
        return super.triggerEvent(id, type);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LunarWindChimeBlockEntity blockEntity) {


        if (!level.isClientSide) {

            if (blockEntity.willDoIdleSounds &&
                    (Math.random() < ServerConfig.WIND_CHIME_IDLE_SOUND_CHANCE.get() / 2
                            || (level.getMoonPhase() == 0 && Math.random() < ServerConfig.WIND_CHIME_IDLE_SOUND_CHANCE.get()))) {
                level.playSound(null, pos, ModSounds.TAM_TAM_PLAYING_MOON.get(), SoundSource.RECORDS, (float) blockEntity.volume, 1);
            }

            if (blockEntity.chimesRemaining > 0) {
                if (blockEntity.chimeDelayTicks <= 0) {
                    float randomPitch = 0.6f + level.random.nextFloat() * 0.2f;
                    level.playSound(null, pos, blockEntity.CHIME_SOUND.get(), SoundSource.RECORDS, (float) blockEntity.volume, randomPitch);

                    int delay = level.random.nextIntBetweenInclusive(0, 4);

                    blockEntity.chimesRemaining--;
                    blockEntity.chimeDelayTicks = delay;
                } else {
                    blockEntity.chimeDelayTicks--;
                }
            }
        }

        if (blockEntity.swinging) {
            blockEntity.ticks++;
        }

        if (blockEntity.ticks >= 148) { // Duration in Ticks
            blockEntity.swinging = false;
            blockEntity.ticks = 0;
            blockEntity.xMotion = 0;
            blockEntity.zMotion = 0;
            blockEntity.randomX = 0;
            blockEntity.randomZ = 0;
            blockEntity.randomX2 = 0;
            blockEntity.randomZ2 = 0;
            blockEntity.randomX3 = 0;
            blockEntity.randomZ3 = 0;
            blockEntity.volume = 0.2;
        }
    }
}