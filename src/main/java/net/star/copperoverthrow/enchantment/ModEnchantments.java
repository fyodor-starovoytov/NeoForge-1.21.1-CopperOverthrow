package net.star.copperoverthrow.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.util.ModTags;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> AREAL = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "areal"));

    public static final ResourceKey<Enchantment> TUNNELING = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "tunneling"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, AREAL, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ModTags.Items.HAMMER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(20),
                        Enchantment.constantCost(65),
                        10,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE)));

        register(context, TUNNELING, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ModTags.Items.HAMMER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(20),
                        Enchantment.constantCost(65),
                        10,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE)));
    }



    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}