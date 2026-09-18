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
import net.star.copperoverthrow.block.custom.MultiBlockTallDecoration;
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

        dropForBottomPartOnly(ModBlocks.RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.EXPOSED_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WEATHERED_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.OXIDIZED_RAIN_DRUM.get());

        dropForBottomPartOnly(ModBlocks.TINY_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.EXPOSED_TINY_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WEATHERED_TINY_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get());

        dropForBottomPartOnly(ModBlocks.LARGE_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get());

        dropForBottomPartOnly(ModBlocks.WAXED_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_EXPOSED_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_WEATHERED_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_OXIDIZED_RAIN_DRUM.get());

        dropForBottomPartOnly(ModBlocks.WAXED_TINY_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_EXPOSED_TINY_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_WEATHERED_TINY_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_OXIDIZED_TINY_RAIN_DRUM.get());

        dropForBottomPartOnly(ModBlocks.WAXED_LARGE_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_EXPOSED_LARGE_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_WEATHERED_LARGE_RAIN_DRUM.get());
        dropForBottomPartOnly(ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get());

        dropSelf(ModBlocks.COPPER_CHAIN.get());
        dropSelf(ModBlocks.EXPOSED_COPPER_CHAIN.get());
        dropSelf(ModBlocks.WEATHERED_COPPER_CHAIN.get());
        dropSelf(ModBlocks.OXIDIZED_COPPER_CHAIN.get());
        dropSelf(ModBlocks.WAXED_WEATHERED_COPPER_CHAIN.get());
        dropSelf(ModBlocks.WAXED_COPPER_CHAIN.get());
        dropSelf(ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN.get());
        dropSelf(ModBlocks.WAXED_EXPOSED_COPPER_CHAIN.get());

        dropSelf(ModBlocks.LOG_STRIPPER.get());
        dropSelf(ModBlocks.COPPER_BEEOSPHERE.get());

        dropSelf(ModBlocks.TAMTAM.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    protected void dropForBottomPartOnly(Block block) {
        this.add(block, createSinglePropConditionTable(
                block,
                MultiBlockTallDecoration.PART,
                MultiBlockTallDecoration.TripleBlockPart.BOTTOM
        ));
    }
}
