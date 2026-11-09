package net.star.copperoverthrow.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.star.copperoverthrow.item.custom.HammerItem; // Replace with your actual hammer class
import net.star.copperoverthrow.util.ModTags;

public class ModEnchantmentCategories {
    public static final EnchantmentCategory HAMMER = EnchantmentCategory.create("HAMMER", item -> item instanceof HammerItem);
}