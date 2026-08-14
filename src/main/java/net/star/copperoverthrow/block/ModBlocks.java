package net.star.copperoverthrow.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.custom.ConductBlock;
import net.star.copperoverthrow.block.custom.CopperScaffoldingBlock;
import net.star.copperoverthrow.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CopperOverthrow.MOD_ID);

public static final DeferredBlock<CopperScaffoldingBlock> COPPER_SCAFFOLDING = registerBlock("copper_scaffolding",
        () -> new CopperScaffoldingBlock(
                BlockBehaviour.Properties.of()
                .strength(3f)
                .noCollission()
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .destroyTime(0.10f)
                .isValidSpawn(Blocks::never)
                .mapColor(DyeColor.ORANGE)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.COPPER)
                .dynamicShape()
                .randomTicks(), WeatheringCopper.WeatherState.UNAFFECTED));

    public static final DeferredBlock<CopperScaffoldingBlock> EXPOSED_COPPER_SCAFFOLDING = registerBlock("exposed_copper_scaffolding",
            () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noCollission()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .destroyTime(0.20f)
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)
                    .dynamicShape()
                    .randomTicks(), WeatheringCopper.WeatherState.EXPOSED));

    public static final DeferredBlock<CopperScaffoldingBlock> WEATHERED_COPPER_SCAFFOLDING = registerBlock("weathered_copper_scaffolding",
            () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noCollission()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .destroyTime(0.40f)
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.GREEN)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)
                    .dynamicShape()
                    .speedFactor(0.95f)
                    .randomTicks(), WeatheringCopper.WeatherState.WEATHERED));

    public static final DeferredBlock<CopperScaffoldingBlock> OXIDIZED_COPPER_SCAFFOLDING = registerBlock("oxidized_copper_scaffolding",
            () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noCollission()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .destroyTime(0.60f)
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.GREEN)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)
                    .dynamicShape()
                    .speedFactor(0.90f)
                    .randomTicks(), WeatheringCopper.WeatherState.OXIDIZED));



    public static final DeferredBlock<CopperScaffoldingBlock> WAXED_COPPER_SCAFFOLDING = registerBlock("waxed_copper_scaffolding",
            () -> new CopperScaffoldingBlock(
                    BlockBehaviour.Properties.of()
                            .strength(3f)
                            .noCollission()
                            .noOcclusion()
                            .requiresCorrectToolForDrops()
                            .destroyTime(0.10f)
                            .isValidSpawn(Blocks::never)
                            .mapColor(DyeColor.ORANGE)
                            .pushReaction(PushReaction.DESTROY)
                            .sound(SoundType.COPPER)
                            .dynamicShape()
                            .randomTicks(), WeatheringCopper.WeatherState.UNAFFECTED));

    public static final DeferredBlock<CopperScaffoldingBlock> WAXED_EXPOSED_COPPER_SCAFFOLDING = registerBlock("waxed_exposed_copper_scaffolding",
            () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noCollission()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .destroyTime(0.20f)
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)
                    .dynamicShape()
                    .randomTicks(), WeatheringCopper.WeatherState.EXPOSED));

    public static final DeferredBlock<CopperScaffoldingBlock> WAXED_WEATHERED_COPPER_SCAFFOLDING = registerBlock("waxed_weathered_copper_scaffolding",
            () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noCollission()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .destroyTime(0.40f)
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.GREEN)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)
                    .dynamicShape()
                    .speedFactor(0.95f)
                    .randomTicks(), WeatheringCopper.WeatherState.WEATHERED));

    public static final DeferredBlock<CopperScaffoldingBlock> WAXED_OXIDIZED_COPPER_SCAFFOLDING = registerBlock("waxed_oxidized_copper_scaffolding",
            () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noCollission()
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .destroyTime(0.60f)
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.GREEN)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)
                    .dynamicShape()
                    .speedFactor(0.90f)
                    .randomTicks(), WeatheringCopper.WeatherState.OXIDIZED));

    public static final DeferredBlock<ConductBlock> CONDUCT_BLOCK = registerBlock("conduct_block",
            () -> new ConductBlock(BlockBehaviour.Properties.of()
                    .replaceable()
                    .noCollission()
                    .noOcclusion()
                    .noLootTable()
                    .noTerrainParticles()
                    .air()));


    private static <T extends Block> DeferredBlock<T> registerBlock (String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        if (name.equals("copper_scaffolding") || name.equals("exposed_copper_scaffolding") || name.equals("weathered_copper_scaffolding")|| name.equals("oxidized_copper_scaffolding")) {
            ModItems.ITEMS.register(name, () -> new ScaffoldingBlockItem(block.get(), new Item.Properties()));
        }
        else {
            ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
