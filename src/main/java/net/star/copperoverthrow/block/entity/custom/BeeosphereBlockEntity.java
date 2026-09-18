package net.star.copperoverthrow.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.component.ModDataComponents;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BeeosphereBlockEntity extends BlockEntity {
    private List<CustomData> inventory = NonNullList.withSize(3, CustomData.EMPTY);

    public BeeosphereBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COPPER_BEEOSPHERE_BE.get(), pos, blockState);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public void saveData(CustomData customData) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()) {
                inventory.set(i, customData);
                setChanged();
                break;
            }
        }
    }
    public int getAmountOfFreeSlots(){
        int freeSlots = 0;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isEmpty()){
                freeSlots++;
            }
        }

        return freeSlots;
    }
}
