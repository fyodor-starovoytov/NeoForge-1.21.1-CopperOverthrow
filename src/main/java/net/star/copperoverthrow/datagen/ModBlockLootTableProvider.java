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
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.WAXED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());
        dropSelf(ModBlocks.LOG_STRIPPER.get());
        dropSelf(ModBlocks.COPPER_BEEOSPHERE.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
