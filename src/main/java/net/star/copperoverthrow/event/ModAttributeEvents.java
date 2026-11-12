package net.star.copperoverthrow.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.item.armor.CopperExoskeletonArmorItem;
import net.star.copperoverthrow.item.armor.CrashHelmetArmorItem;
import net.star.copperoverthrow.item.armor.StepperBootsArmorItem;
import net.star.copperoverthrow.item.armor.StepperLeggingsArmorItem;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID)
public class ModAttributeEvents {

    @SubscribeEvent
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        if (event.getItemStack().getItem() instanceof StepperLeggingsArmorItem) {
            event.addModifier(
                    Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_leggings_movement_speed"),
                            ServerConfig.STEPPER_LEGGINGS_SPEED_BOOST.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.LEGS
            );

            event.addModifier(
                    Attributes.WATER_MOVEMENT_EFFICIENCY,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_leggings_water_efficiency"),
                            ServerConfig.STEPPER_LEGGINGS_WATER_MOBILITY.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.LEGS
            );
        }

        if (event.getItemStack().getItem() instanceof StepperBootsArmorItem) {
            event.addModifier(
                    Attributes.STEP_HEIGHT,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_boots_step_height"),
                            ServerConfig.STEPPER_BOOTS_STEP_HEIGHT.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.FEET
            );

            event.addModifier(
                    Attributes.SAFE_FALL_DISTANCE,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_boots_fall_distance"),
                            ServerConfig.STEPPER_BOOTS_SAFE_FALL_DISTANCE.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.FEET
            );
        }

        if (event.getItemStack().getItem() instanceof CrashHelmetArmorItem) {
            event.addModifier(
                    ModAttributes.HEAD_IMPACT_RESISTANCE,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "head_impact_resistance"),
                            ServerConfig.CRASH_HELMET_HEAD_IMPACT_RESISTANCE.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.HEAD
            );
        }

        if (event.getItemStack().getItem() instanceof CopperExoskeletonArmorItem) {
            event.addModifier(
                    Attributes.BLOCK_INTERACTION_RANGE,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "long_reach"),
                            ServerConfig.EXOSKELETON_REACH.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.CHEST
            );

            event.addModifier(
                    Attributes.BLOCK_BREAK_SPEED,
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "break_speed"),
                            ServerConfig.EXOSKELETON_BLOCK_BREAK_SPEED.get(),
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    EquipmentSlotGroup.CHEST
            );
        }
    }
}