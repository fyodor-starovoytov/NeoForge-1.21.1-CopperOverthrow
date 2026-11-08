package net.star.copperoverthrow.item;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.effect.ModEffects;
import net.star.copperoverthrow.item.armor.*;
import net.star.copperoverthrow.item.custom.*;
import net.star.copperoverthrow.util.ModTags;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CopperOverthrow.MOD_ID);

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            ()-> new Item(new Properties()));

    public static final RegistryObject<Item> BEE_CATCHER = ITEMS.register("bee_catcher",
            () -> new BeeCatcherItem(
                    new Properties().stacksTo(1)));

    public static final RegistryObject<Item> COPPER_SEXTANT = ITEMS.register("copper_sextant",
            () -> new CopperTapeMeasureItem(new Properties().durability(256).stacksTo(1), ModBlocks.HOLOGRAM_BLOCK.get()));

    public static final RegistryObject<Item> COPPER_FILL_BUCKET = ITEMS.register("copper_fill_bucket",
            () -> new CopperFillBucketItem(new Properties().durability(727).stacksTo(1)));

    public static final RegistryObject<Item> COPPER_LONG_RULER = ITEMS.register("copper_long_ruler",
            () -> new CopperLongRuler(new Properties().durability(256).stacksTo(1), ModBlocks.HOLOGRAM_BLOCK.get()));

    public static final RegistryObject<Item> COPPER_CHISEL = ITEMS.register("copper_chisel",
            () -> new CopperChiselItem(new Properties().durability(256).stacksTo(1)));

    public static final RegistryObject<Item> COPPER_TROWEL = ITEMS.register("copper_trowel",
            () -> new CopperTrowelItem(new Properties().durability(256).stacksTo(1)));

    public static final RegistryObject<Item> COPPER_COOKIE = ITEMS.register("copper_cookie",
            () -> new Item(new Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(2.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F)
                    .effect(() -> new MobEffectInstance(ModEffects.CONDUCTIVITY_EFFECT.get(), 1200, 0, false, false, true), 1.0F)
                    .alwaysEat()
                    .build())));

    public static final RegistryObject<StepperLeggingsArmorItem> COPPER_STEPPER_LEGGINGS = ITEMS.register("copper_stepper_leggings",
            () -> new StepperLeggingsArmorItem(ModArmorMaterials.STEPPER, ArmorItem.Type.LEGGINGS, new Properties()));

    public static final RegistryObject<StepperBootsArmorItem> COPPER_STEPPER_BOOTS = ITEMS.register("copper_stepper_boots",
            () -> new StepperBootsArmorItem(ModArmorMaterials.STEPPER, ArmorItem.Type.BOOTS,
                    new Properties()));

    public static final RegistryObject<CrashHelmetArmorItem> CRASH_HELMET = ITEMS.register("crash_helmet",
            () -> new CrashHelmetArmorItem(ModArmorMaterials.CRASH, ArmorItem.Type.HELMET,
                    new Properties()));

    public static final RegistryObject<CopperExoskeletonArmorItem> COPPER_EXOSKELETON = ITEMS.register("copper_exoskeleton",
            () -> new CopperExoskeletonArmorItem(ModArmorMaterials.STEPPER, ArmorItem.Type.CHESTPLATE,
                    new Properties()));

    public static final RegistryObject<GlowingGogglesArmorItem> COPPER_GLOWING_GOGGLES = ITEMS.register("copper_glowing_goggles",
            () -> new GlowingGogglesArmorItem(ModArmorMaterials.STEPPER, ArmorItem.Type.HELMET,
                    new Properties()));

    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new HammerItem( 4f, 2f, ModToolTiers.COPPER,new Item.Properties()));

    public static final RegistryObject<Item> COPPER_HANDSAW = ITEMS.register("copper_handsaw",
            () -> new HandsawItem(new Properties().durability(727).stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
