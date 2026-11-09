package net.star.copperoverthrow.item.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.star.copperoverthrow.CopperOverthrow;

import java.util.function.Supplier;


public enum ModArmorMaterials implements ArmorMaterial {

        STEPPER("stepper", 15, new int[]{1, 4, 4, 1}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, ()-> Ingredient.of(Tags.Items.INGOTS_COPPER)),
        CRASH("crash", 25, new int[]{2, 5, 4, 2}, 7, SoundEvents.ARMOR_EQUIP_GENERIC, 2f, 0f, ()-> Ingredient.of(Tags.Items.INGOTS_COPPER));



    private final String name;
        private final int durabilityMultiplier;
        private final int[] protectionAmounts;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> ingredientSupplier;

        private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

        ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.ingredientSupplier = ingredientSupplier;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.ingredientSupplier.get();
    }

    @Override
    public String getName() {
        return CopperOverthrow.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
