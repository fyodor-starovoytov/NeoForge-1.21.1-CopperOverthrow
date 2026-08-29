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

public class StepperBootsArmorItem extends ArmorItem {
    public StepperBootsArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {

        ItemAttributeModifiers base = super.getDefaultAttributeModifiers();

        return base
                .withModifierAdded(
                Attributes.STEP_HEIGHT,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_boots_step_height"),
                        0.5,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.FEET
        )
                .withModifierAdded(
                Attributes.SAFE_FALL_DISTANCE,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_boots_fall_distance"),
                        3.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.FEET
        );
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
