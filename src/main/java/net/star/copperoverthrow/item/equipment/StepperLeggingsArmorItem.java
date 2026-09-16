package net.star.copperoverthrow.item.equipment;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;

public class StepperLeggingsArmorItem extends ArmorItem {
    public StepperLeggingsArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {

        ItemAttributeModifiers base = super.getDefaultAttributeModifiers();

        return base
                .withModifierAdded(
                        Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_leggings_movement_speed"),
                                ServerConfig.STEPPER_LEGGINGS_SPEED_BOOST.get(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.LEGS
                )
                .withModifierAdded(
                        Attributes.WATER_MOVEMENT_EFFICIENCY,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_leggings_water_efficiency"),
                                ServerConfig.STEPPER_LEGGINGS_WATER_MOBILITY.get(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.LEGS
                );
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
