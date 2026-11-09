package net.star.copperoverthrow.util;

import net.minecraft.world.level.block.Block;
import net.star.copperoverthrow.block.ModBlocks;

import java.util.HashMap;
import java.util.Map;

public class ModBlockModifiers {
    public static final Map<Block, Block> OXIDIZABLES = new HashMap<>();
    public static final Map<Block, Block> PREVIOUS_BY_BLOCK = new HashMap<>();

    public static final Map<Block, Block> WAXABLES = new HashMap<>();
    public static final Map<Block, Block> UNWAXABLES = new HashMap<>();

    public static void registerModifiers() {
        addOxidizable(ModBlocks.LOG_STRIPPER.get(), ModBlocks.EXPOSED_LOG_STRIPPER.get());
        addOxidizable(ModBlocks.EXPOSED_LOG_STRIPPER.get(), ModBlocks.WEATHERED_LOG_STRIPPER.get());
        addOxidizable(ModBlocks.WEATHERED_LOG_STRIPPER.get(), ModBlocks.OXIDIZED_LOG_STRIPPER.get());

        addOxidizable(ModBlocks.COPPER_SCAFFOLDING.get(), ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get());
        addOxidizable(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get(), ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get());
        addOxidizable(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get(), ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get());

        addOxidizable(ModBlocks.RAIN_DRUM.get(), ModBlocks.EXPOSED_RAIN_DRUM.get());
        addOxidizable(ModBlocks.EXPOSED_RAIN_DRUM.get(), ModBlocks.WEATHERED_RAIN_DRUM.get());
        addOxidizable(ModBlocks.WEATHERED_RAIN_DRUM.get(), ModBlocks.OXIDIZED_RAIN_DRUM.get());

        addOxidizable(ModBlocks.TINY_RAIN_DRUM.get(), ModBlocks.EXPOSED_TINY_RAIN_DRUM.get());
        addOxidizable(ModBlocks.EXPOSED_TINY_RAIN_DRUM.get(), ModBlocks.WEATHERED_TINY_RAIN_DRUM.get());
        addOxidizable(ModBlocks.WEATHERED_TINY_RAIN_DRUM.get(), ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get());

        addOxidizable(ModBlocks.LARGE_RAIN_DRUM.get(), ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get());
        addOxidizable(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get(), ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get());
        addOxidizable(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get(), ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get());

        addOxidizable(ModBlocks.TAMTAM.get(), ModBlocks.EXPOSED_TAMTAM.get());
        addOxidizable(ModBlocks.EXPOSED_TAMTAM.get(), ModBlocks.WEATHERED_TAMTAM.get());
        addOxidizable(ModBlocks.WEATHERED_TAMTAM.get(), ModBlocks.OXIDIZED_TAMTAM.get());

        addOxidizable(ModBlocks.COPPER_CHAIN.get(), ModBlocks.EXPOSED_COPPER_CHAIN.get());
        addOxidizable(ModBlocks.EXPOSED_COPPER_CHAIN.get(), ModBlocks.WEATHERED_COPPER_CHAIN.get());
        addOxidizable(ModBlocks.WEATHERED_COPPER_CHAIN.get(), ModBlocks.OXIDIZED_COPPER_CHAIN.get());

        addOxidizable(ModBlocks.COPPER_LANTERN.get(), ModBlocks.EXPOSED_COPPER_LANTERN.get());
        addOxidizable(ModBlocks.EXPOSED_COPPER_LANTERN.get(), ModBlocks.WEATHERED_COPPER_LANTERN.get());
        addOxidizable(ModBlocks.WEATHERED_COPPER_LANTERN.get(), ModBlocks.OXIDIZED_COPPER_LANTERN.get());

        addOxidizable(ModBlocks.KITCHEN_BELL.get(), ModBlocks.EXPOSED_KITCHEN_BELL.get());
        addOxidizable(ModBlocks.EXPOSED_KITCHEN_BELL.get(), ModBlocks.WEATHERED_KITCHEN_BELL.get());
        addOxidizable(ModBlocks.WEATHERED_KITCHEN_BELL.get(), ModBlocks.OXIDIZED_KITCHEN_BELL.get());

        // --- WAXABLES ---
        addWaxable(ModBlocks.LOG_STRIPPER.get(), ModBlocks.WAXED_LOG_STRIPPER.get());
        addWaxable(ModBlocks.EXPOSED_LOG_STRIPPER.get(), ModBlocks.WAXED_EXPOSED_LOG_STRIPPER.get());
        addWaxable(ModBlocks.WEATHERED_LOG_STRIPPER.get(), ModBlocks.WAXED_WEATHERED_LOG_STRIPPER.get());
        addWaxable(ModBlocks.OXIDIZED_LOG_STRIPPER.get(), ModBlocks.WAXED_OXIDIZED_LOG_STRIPPER.get());

        addWaxable(ModBlocks.COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_COPPER_SCAFFOLDING.get());
        addWaxable(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get());
        addWaxable(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get());
        addWaxable(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());

        addWaxable(ModBlocks.RAIN_DRUM.get(), ModBlocks.WAXED_RAIN_DRUM.get());
        addWaxable(ModBlocks.EXPOSED_RAIN_DRUM.get(), ModBlocks.WAXED_EXPOSED_RAIN_DRUM.get());
        addWaxable(ModBlocks.WEATHERED_RAIN_DRUM.get(), ModBlocks.WAXED_WEATHERED_RAIN_DRUM.get());
        addWaxable(ModBlocks.OXIDIZED_RAIN_DRUM.get(), ModBlocks.WAXED_OXIDIZED_RAIN_DRUM.get());

        addWaxable(ModBlocks.TINY_RAIN_DRUM.get(), ModBlocks.WAXED_TINY_RAIN_DRUM.get());
        addWaxable(ModBlocks.EXPOSED_TINY_RAIN_DRUM.get(), ModBlocks.WAXED_EXPOSED_TINY_RAIN_DRUM.get());
        addWaxable(ModBlocks.WEATHERED_TINY_RAIN_DRUM.get(), ModBlocks.WAXED_WEATHERED_TINY_RAIN_DRUM.get());
        addWaxable(ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get(), ModBlocks.WAXED_OXIDIZED_TINY_RAIN_DRUM.get());

        addWaxable(ModBlocks.LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_LARGE_RAIN_DRUM.get());
        addWaxable(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_EXPOSED_LARGE_RAIN_DRUM.get());
        addWaxable(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_WEATHERED_LARGE_RAIN_DRUM.get());
        addWaxable(ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get());

        addWaxable(ModBlocks.TAMTAM.get(), ModBlocks.WAXED_TAMTAM.get());
        addWaxable(ModBlocks.EXPOSED_TAMTAM.get(), ModBlocks.WAXED_EXPOSED_TAMTAM.get());
        addWaxable(ModBlocks.WEATHERED_TAMTAM.get(), ModBlocks.WAXED_WEATHERED_TAMTAM.get());
        addWaxable(ModBlocks.OXIDIZED_TAMTAM.get(), ModBlocks.WAXED_OXIDIZED_TAMTAM.get());

        addWaxable(ModBlocks.COPPER_CHAIN.get(), ModBlocks.WAXED_COPPER_CHAIN.get());
        addWaxable(ModBlocks.EXPOSED_COPPER_CHAIN.get(), ModBlocks.WAXED_EXPOSED_COPPER_CHAIN.get());
        addWaxable(ModBlocks.WEATHERED_COPPER_CHAIN.get(), ModBlocks.WAXED_WEATHERED_COPPER_CHAIN.get());
        addWaxable(ModBlocks.OXIDIZED_COPPER_CHAIN.get(), ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN.get());

        addWaxable(ModBlocks.COPPER_LANTERN.get(), ModBlocks.WAXED_COPPER_LANTERN.get());
        addWaxable(ModBlocks.EXPOSED_COPPER_LANTERN.get(), ModBlocks.WAXED_EXPOSED_COPPER_LANTERN.get());
        addWaxable(ModBlocks.WEATHERED_COPPER_LANTERN.get(), ModBlocks.WAXED_WEATHERED_COPPER_LANTERN.get());
        addWaxable(ModBlocks.OXIDIZED_COPPER_LANTERN.get(), ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN.get());

        addWaxable(ModBlocks.KITCHEN_BELL.get(), ModBlocks.WAXED_KITCHEN_BELL.get());
        addWaxable(ModBlocks.EXPOSED_KITCHEN_BELL.get(), ModBlocks.WAXED_EXPOSED_KITCHEN_BELL.get());
        addWaxable(ModBlocks.WEATHERED_KITCHEN_BELL.get(), ModBlocks.WAXED_WEATHERED_KITCHEN_BELL.get());
        addWaxable(ModBlocks.OXIDIZED_KITCHEN_BELL.get(), ModBlocks.WAXED_OXIDIZED_KITCHEN_BELL.get());
    }

    private static void addOxidizable(Block base, Block oxidized) {
        OXIDIZABLES.put(base, oxidized);
        PREVIOUS_BY_BLOCK.put(oxidized, base);
    }

    private static void addWaxable(Block unwaxed, Block waxed) {
        WAXABLES.put(unwaxed, waxed);
        UNWAXABLES.put(waxed, unwaxed);
    }
}
