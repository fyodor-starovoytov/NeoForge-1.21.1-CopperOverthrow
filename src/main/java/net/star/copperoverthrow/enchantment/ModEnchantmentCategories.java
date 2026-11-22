package net.star.copperoverthrow.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.star.copperoverthrow.item.custom.HammerItem;

public class ModEnchantmentCategories {
    public static final EnchantmentCategory HAMMER = EnchantmentCategory.create("HAMMER", item -> item instanceof HammerItem);
}