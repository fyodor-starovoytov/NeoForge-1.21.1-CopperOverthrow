package net.star.copperoverthrow.block.entity.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.star.copperoverthrow.block.entity.ModBlockEntities;

import java.util.List;

public class CopperBeeosphereBlockEntity extends BeehiveBlockEntity {

    private final List<CopperBeeosphereBlockEntity.BeeData> stored = Lists.newArrayList();

    public CopperBeeosphereBlockEntity(BlockPos pos, BlockState state) {super(pos, state);}
    @Override
    public BlockEntityType<?> getType() {return ModBlockEntities.COPPER_BEEOSPHERE_BE.get();}

    @Override
    public void addOccupant(Entity occupant) {
        if (this.stored.size() < 3) {
            this.storeBee(CopperBeeosphereBlockEntity.Occupant.of(occupant));
            if (this.level != null) {
                BlockPos blockpos = this.getBlockPos();
                this.level
                        .playSound(
                                null,
                                (double)blockpos.getX(),
                                (double)blockpos.getY(),
                                (double)blockpos.getZ(),
                                SoundEvents.BEEHIVE_ENTER,
                                SoundSource.BLOCKS,
                                1.0F,
                                1.0F
                        );
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(occupant, this.getBlockState()));
            }
            super.setChanged();
        }
    }

    static class BeeData {
        private final CopperBeeosphereBlockEntity.Occupant occupant;

        BeeData(CopperBeeosphereBlockEntity.Occupant occupant) {
            this.occupant = occupant;
        }
    }
}