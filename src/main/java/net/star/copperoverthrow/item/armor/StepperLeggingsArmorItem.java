package net.star.copperoverthrow.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.star.copperoverthrow.ServerConfig;

public class StepperLeggingsArmorItem extends ArmorItem {
    public StepperLeggingsArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(slot));

            builder.put(
                    Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(
                            "Stepper movement speed",
                            ServerConfig.STEPPER_LEGGINGS_SPEED_BOOST.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            builder.put(
                    ForgeMod.SWIM_SPEED.get(),
                    new AttributeModifier(
                            "Stepper swim boost",
                            ServerConfig.STEPPER_LEGGINGS_WATER_MOBILITY.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
