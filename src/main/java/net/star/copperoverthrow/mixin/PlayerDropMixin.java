package net.star.copperoverthrow.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.star.copperoverthrow.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class PlayerDropMixin {
    @Shadow
    public abstract Slot getSlot(int slotId);
    @Shadow public abstract ItemStack getCarried();

    @Inject(
            method = "doClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void copperoverthrow$lockCrowbarSlot(
            int slotId,
            int button,
            ClickType clickType,
            Player player,
            CallbackInfo ci
    ) {
        if (this.getCarried().is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get())) {
            ci.cancel();
            return;
        }

        if (slotId >= 0) {
            Slot slot = this.getSlot(slotId);
            if (slot != null && slot.hasItem() && slot.getItem().is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get())) {
                ci.cancel();
                return;
            }
        }

        if (clickType == ClickType.SWAP) {
            ItemStack swapTarget = button == 40
                    ? player.getOffhandItem()
                    : player.getInventory().getItem(button);

            if (swapTarget.is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get())) {
                ci.cancel();
            }
        }
    }
}
