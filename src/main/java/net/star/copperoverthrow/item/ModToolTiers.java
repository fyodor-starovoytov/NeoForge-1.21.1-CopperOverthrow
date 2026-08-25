package net.star.copperoverthrow.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.star.copperoverthrow.util.ModTags;

public class ModToolTiers {

    public static final Tier COPPER = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            512, 4f, 3f, 12, () -> Ingredient.of(Items.COPPER_INGOT));

}
