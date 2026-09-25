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
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.getId(), new Oxidizable(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get()), false)

                .add(ModBlocks.RAIN_DRUM.getId(), new Oxidizable(ModBlocks.EXPOSED_RAIN_DRUM.get()), false)
                .add(ModBlocks.EXPOSED_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.WEATHERED_RAIN_DRUM.get()), false)
                .add(ModBlocks.WEATHERED_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.OXIDIZED_RAIN_DRUM.get()), false)

                .add(ModBlocks.TINY_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.EXPOSED_TINY_RAIN_DRUM.get()), false)
                .add(ModBlocks.EXPOSED_TINY_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.WEATHERED_TINY_RAIN_DRUM.get()), false)
                .add(ModBlocks.WEATHERED_TINY_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get()), false)

                .add(ModBlocks.LARGE_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get()), false)
                .add(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get()), false)
                .add(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.getId(), new Oxidizable(ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get()), false)

                .add(ModBlocks.TAMTAM.getId(), new Oxidizable(ModBlocks.EXPOSED_TAMTAM.get()), false)
                .add(ModBlocks.EXPOSED_TAMTAM.getId(), new Oxidizable(ModBlocks.WEATHERED_TAMTAM.get()), false)
                .add(ModBlocks.WEATHERED_TAMTAM.getId(), new Oxidizable(ModBlocks.OXIDIZED_TAMTAM.get()), false)

                .add(ModBlocks.COPPER_CHAIN.getId(), new Oxidizable(ModBlocks.EXPOSED_COPPER_CHAIN.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_CHAIN.getId(), new Oxidizable(ModBlocks.WEATHERED_COPPER_CHAIN.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_CHAIN.getId(), new Oxidizable(ModBlocks.OXIDIZED_COPPER_CHAIN.get()), false)

                .add(ModBlocks.COPPER_LANTERN.getId(), new Oxidizable(ModBlocks.EXPOSED_COPPER_LANTERN.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_LANTERN.getId(), new Oxidizable(ModBlocks.WEATHERED_COPPER_LANTERN.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_LANTERN.getId(), new Oxidizable(ModBlocks.OXIDIZED_COPPER_LANTERN.get()), false)

        ;



        this.builder(NeoForgeDataMaps.WAXABLES)
                .add(ModBlocks.COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get()), false)
                .add(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get()), false)

                .add(ModBlocks.RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_RAIN_DRUM.get()), false)
                .add(ModBlocks.EXPOSED_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_RAIN_DRUM.get()), false)
                .add(ModBlocks.WEATHERED_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_RAIN_DRUM.get()), false)
                .add(ModBlocks.OXIDIZED_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_RAIN_DRUM.get()), false)

                .add(ModBlocks.TINY_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_TINY_RAIN_DRUM.get()), false)
                .add(ModBlocks.EXPOSED_TINY_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_TINY_RAIN_DRUM.get()), false)
                .add(ModBlocks.WEATHERED_TINY_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_TINY_RAIN_DRUM.get()), false)
                .add(ModBlocks.OXIDIZED_TINY_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_TINY_RAIN_DRUM.get()), false)

                .add(ModBlocks.LARGE_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_LARGE_RAIN_DRUM.get()), false)
                .add(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_LARGE_RAIN_DRUM.get()), false)
                .add(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_LARGE_RAIN_DRUM.get()), false)
                .add(ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get()), false)

                .add(ModBlocks.TAMTAM.getId(), new Waxable(ModBlocks.WAXED_TAMTAM.get()), false)
                .add(ModBlocks.EXPOSED_TAMTAM.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_TAMTAM.get()), false)
                .add(ModBlocks.WEATHERED_TAMTAM.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_TAMTAM.get()), false)
                .add(ModBlocks.OXIDIZED_TAMTAM.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_TAMTAM.get()), false)

                .add(ModBlocks.COPPER_CHAIN.getId(), new Waxable(ModBlocks.WAXED_COPPER_CHAIN.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_CHAIN.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_COPPER_CHAIN.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_CHAIN.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_COPPER_CHAIN.get()), false)
                .add(ModBlocks.OXIDIZED_COPPER_CHAIN.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN.get()), false)

                .add(ModBlocks.COPPER_LANTERN.getId(), new Waxable(ModBlocks.WAXED_COPPER_LANTERN.get()), false)
                .add(ModBlocks.EXPOSED_COPPER_LANTERN.getId(), new Waxable(ModBlocks.WAXED_EXPOSED_COPPER_LANTERN.get()), false)
                .add(ModBlocks.WEATHERED_COPPER_LANTERN.getId(), new Waxable(ModBlocks.WAXED_WEATHERED_COPPER_LANTERN.get()), false)
                .add(ModBlocks.OXIDIZED_COPPER_LANTERN.getId(), new Waxable(ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN.get()), false)

        ;
    }
}
