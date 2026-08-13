package net.star.copperoverthrow.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import net.star.copperoverthrow.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.OXIDIZABLES)
                .add(ModBlocks.COPPER_SCAFFOLDING.getId(), new Oxidizable(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.getId(), new Oxidizable(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.getId(), new Oxidizable(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get()), false);

        this.builder(NeoForgeDataMaps.WAXABLES)
                .add(ModBlocks.COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get()), false);
    }
}
