package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModTags;

import java.util.List;

public class DoublePickaxesLeftItem extends DiggerItem {


    public DoublePickaxesLeftItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity instanceof Player player) {
                boolean isOffhand = (slotId == Inventory.SLOT_OFFHAND);
                ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (!mainHandStack.is(ModItems.COPPER_DOUBLE_PICKAXES.get()) || player.getMainHandItem().get(ModDataComponents.IS_USED) == null){
                    stack.setCount(0);
                }

                if (!isOffhand) {
                    stack.setCount(0);
                }
            }
        }
    }
}