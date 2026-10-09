package net.star.copperoverthrow.item.armor.client.provider;

import net.star.copperoverthrow.item.armor.client.model.ArmorModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * the armor model provider
 */
public interface ArmorModelProvider {

    /**
     * provides a custom armor model.
     * cache the model if possible as it's needed each <i>rendering</i> tick
     * @param living the entity for the model
     * @param stack the stack for the model
     * @param slot the slot for the model
     * @return the model
     */
    ArmorModel getModel(LivingEntity living, ItemStack stack, EquipmentSlot slot);
}
