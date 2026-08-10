package net.star.copperoverthrow.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    //Delete first two parameters "Set<Item> explosionResistant, FeatureFlagSet enabledFeatures,"
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.COPPER_SCAFFOLDING.get());

        //Can create own drop methods by copying from the source and redacting them.
        /* add(ModBlocks.COPPER_SCAFFOLDING.get(),
                block -> createOreDrop(ModBlocks.COPPER_SCAFFOLDING.get(), ModItems.COPPER_CHISEL.get()));

         */
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        //Getting the list of all the blocks registered
        //and transforming it into the way LootTable provider can read it
        //REQUIRES EVERY BLOCK TO HAVE LOOTTABLE
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
