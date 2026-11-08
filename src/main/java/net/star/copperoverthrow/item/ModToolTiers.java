package net.star.copperoverthrow.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.util.ModTags;

import java.util.List;

public class ModToolTiers {

    public static final Tier COPPER = TierSortingRegistry.registerTier(
            new ForgeTier(1,
                    999, 4f, 3f, 12, ModTags.Blocks.NEEDS_COPPER_TOOL,
                    () -> Ingredient.of(Items.COPPER_INGOT)),
            new ResourceLocation(CopperOverthrow.MOD_ID, "copper"), List.of(Tiers.STONE), List.of());
}
