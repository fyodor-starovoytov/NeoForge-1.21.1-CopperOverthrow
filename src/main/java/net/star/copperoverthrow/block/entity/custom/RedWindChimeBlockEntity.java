package net.star.copperoverthrow.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.block.entity.ModBlockEntities;

import javax.annotation.Nullable;

public class RedWindChimeBlockEntity extends BlockEntity {

    public int ticks;
    public boolean swinging;
    public double xMotion;
    public double zMotion;
    public double randomX;
    public double randomZ;

    public RedWindChimeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIND_CHIME_BE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("xMotion", xMotion);
        tag.putDouble("zMotion", zMotion);
        tag.putDouble("randomX", randomX);
        tag.putDouble("randomZ", randomZ);
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

    public void startSwing(double xMotion, double zMotion) {
        this.ticks = 0;
        this.swinging = true;
        this.xMotion = xMotion * 10;
        this.zMotion = zMotion * 10;
        this.randomX = Math.random() * zMotion * 5;
        this.randomZ = Math.random() * xMotion * 5;

        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
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

    public static void tick(Level level, BlockPos pos, BlockState state, RedWindChimeBlockEntity blockEntity) {
        if (blockEntity.swinging) {
            blockEntity.ticks++;
        }

        if (blockEntity.ticks >= 148) { //Duration in Ticks
            blockEntity.swinging = false;
            blockEntity.ticks = 0;
            blockEntity.xMotion = 0;
            blockEntity.zMotion = 0;
            blockEntity.randomX = 0;
            blockEntity.randomZ = 0;
        }
    }
}