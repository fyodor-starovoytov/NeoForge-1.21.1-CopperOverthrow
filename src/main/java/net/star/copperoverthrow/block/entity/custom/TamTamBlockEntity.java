package net.star.copperoverthrow.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.block.entity.ModBlockEntities;

public class TamTamBlockEntity extends BlockEntity {
    public int ticks;
    public boolean swinging;
    public Direction clickDirection = Direction.NORTH;

    public TamTamBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TAMTAM_BE.get(), pos, state);
    }

    public void startSwing(Direction direction) {
        this.clickDirection = direction;
        this.ticks = 0;
        this.swinging = true;

        if (this.level != null && !this.level.isClientSide) {
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, direction.get3DDataValue());
        }
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.clickDirection = Direction.from3DDataValue(type);
            this.ticks = 0;
            this.swinging = true;
            return true;
        }
        return super.triggerEvent(id, type);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TamTamBlockEntity blockEntity) {
        if (blockEntity.swinging) {
            blockEntity.ticks++;
        }

        if (blockEntity.ticks >= 400) { //Duration in Ticks
            blockEntity.swinging = false;
            blockEntity.ticks = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("ticks", ticks);
        tag.putBoolean("swinging", swinging);
        tag.putInt("clickDirection", clickDirection.get3DDataValue());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ticks = tag.getInt("ticks");
        swinging = tag.getBoolean("swinging");
        clickDirection = Direction.from3DDataValue(tag.getInt("clickDirection"));
    }
}




