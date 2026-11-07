package net.star.copperoverthrow.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.star.copperoverthrow.util.ModTags;

public class ModToolTiers {

    public static final Tier COPPER = new ForgeTier(1,
            999, 4f, 3f, 12, ModTags.Blocks.NEEDS_COPPER_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));

}
