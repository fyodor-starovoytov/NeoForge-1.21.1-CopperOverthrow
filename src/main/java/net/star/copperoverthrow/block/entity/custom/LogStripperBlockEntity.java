package net.star.copperoverthrow.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.block.entity.ModBlockEntities;

public class LogStripperBlockEntity extends BlockEntity implements Container {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public LogStripperBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LOG_STRIPPER_BE.get(), pos, blockState);
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }




    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()){
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.get(slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        setChanged();
        return ContainerHelper.removeItem(inventory, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        setChanged();
        return ContainerHelper.takeItem(inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (getItem(0).getItem().equals(stack.getItem())){
            int itemCount = getItem(0).getCount();
            if (itemCount >= 64){
                return;
            }
            if (itemCount + stack.getCount() >= 64){
                inventory.set(slot, stack.copyWithCount(64));
            }
            else {
                inventory.set(slot, stack.copyWithCount(stack.getCount()
                        + getItem(0).getCount()));
            }
        }
        else {inventory.set(slot, stack.copyWithCount(stack.getCount()));}

        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, inventory);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, inventory);

    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
