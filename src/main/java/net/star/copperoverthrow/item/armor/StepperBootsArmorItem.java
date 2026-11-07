package net.star.copperoverthrow.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import net.minecraftforge.common.ForgeMod;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.attribute.ModAttributes;

public class StepperBootsArmorItem extends ArmorItem {
    public StepperBootsArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.FEET) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(slot));

            builder.put(
                    ForgeMod.STEP_HEIGHT_ADDITION.get(),
                    new AttributeModifier(
                            "Stepper step-height",
                            ServerConfig.STEPPER_BOOTS_STEP_HEIGHT.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            builder.put(
                    ModAttributes.SAFE_FALL_DISTANCE.get(),
                    new AttributeModifier(
                            "Stepper safe fall distance",
                            ServerConfig.STEPPER_BOOTS_SAFE_FALL_DISTANCE.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }


}
