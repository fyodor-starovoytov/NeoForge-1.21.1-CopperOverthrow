package net.star.copperoverthrow.item.armor.client;

import net.star.copperoverthrow.item.armor.client.model.ArmorModel;
import net.star.copperoverthrow.item.armor.client.provider.ArmorModelProvider;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ArmorClientExtension implements IClientItemExtensions {

    private final ArmorModelProvider provider;

    public ArmorClientExtension(ArmorModelProvider provider) {
        this.provider = provider;
    }

    @Override
    public @NotNull ArmorModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
        ArmorModel armorModel = provider.getModel(living, stack, slot);
        armorModel.partVisible(slot);
        armorModel.crouching = original.crouching;
        armorModel.riding = original.riding;
        armorModel.young = original.young;
        return armorModel;
    }

    @Override
    public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        //fixes visibility bug because forge pain
        ArmorModel model = getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
        copyModelProperties(original, model);
        return model;
    }

    @SuppressWarnings("unchecked")
    private <T extends LivingEntity> void copyModelProperties(HumanoidModel<T> original, ArmorModel replacement) {
        original.copyPropertiesTo((HumanoidModel<T>) replacement);
        replacement.rightBoot.copyFrom(original.rightLeg);
        replacement.leftBoot.copyFrom(original.leftLeg);
    }
}
