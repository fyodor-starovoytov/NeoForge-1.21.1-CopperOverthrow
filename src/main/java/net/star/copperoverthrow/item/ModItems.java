package net.star.copperoverthrow.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.effect.ModEffects;
import net.star.copperoverthrow.item.custom.BeeCatcherItem;
import net.star.copperoverthrow.item.custom.CopperChiselItem;
import net.star.copperoverthrow.item.equipment.ModArmorMaterials;
import net.star.copperoverthrow.item.equipment.StepperArmorItem;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CopperOverthrow.MOD_ID);

    public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(new Properties()));

    public static final DeferredItem<Item> BEE_CATCHER = ITEMS.register("bee_catcher",
            () -> new BeeCatcherItem(
                    new Properties().stacksTo(1)));

    public static final DeferredItem<Item> COPPER_CHISEL = ITEMS.register("copper_chisel",
            () -> new CopperChiselItem(new Properties().durability(256).stacksTo(1)));

    public static final DeferredItem<Item> COPPER_COOKIE = ITEMS.register("copper_cookie",
            () -> new Item(new Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(2.0F)
                    .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
                    .effect(new MobEffectInstance(ModEffects.CONDUCTIVITY_EFFECT, 1200, 0, false, false, true), 1.0F)
                    .alwaysEdible()
                    .build())));

    public static final DeferredItem<ArmorItem> COPPER_STEPPER_BOOTS = ITEMS.register("copper_stepper_boots",
            () -> new StepperArmorItem(ModArmorMaterials.STEPPER_BOOTS_ARMOR, ArmorItem.Type.BOOTS,
                    new Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
