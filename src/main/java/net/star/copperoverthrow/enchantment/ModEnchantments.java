package net.star.copperoverthrow.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.enchantment.custom.ArealEnchantment;
import net.star.copperoverthrow.enchantment.custom.TunnelingEnchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CopperOverthrow.MOD_ID);

    public static final RegistryObject<Enchantment> AREAL = ENCHANTMENTS.register("areal",
            () -> new ArealEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

    public static final RegistryObject<Enchantment> TUNNELING = ENCHANTMENTS.register("tunneling",
            () -> new TunnelingEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
}