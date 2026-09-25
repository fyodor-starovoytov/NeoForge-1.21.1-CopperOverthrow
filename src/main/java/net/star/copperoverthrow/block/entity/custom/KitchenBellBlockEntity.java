package net.star.copperoverthrow.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.block.entity.ModBlockEntities;

public class KitchenBellBlockEntity extends BlockEntity {
    public int ticks;
    public boolean swinging;
    public Direction clickDirection = Direction.NORTH;

    public KitchenBellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KITCHEN_BELL_BE.get(), pos, state);
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
            this.ticks = 0;
            this.swinging = true;
            return true;
        }
        return super.triggerEvent(id, type);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, KitchenBellBlockEntity blockEntity) {
        if (blockEntity.swinging) {
            blockEntity.ticks++;
        }

        if (blockEntity.ticks >= 20) { //Duration in Ticks
            blockEntity.swinging = false;
            blockEntity.ticks = 0;
        }
    }
}




