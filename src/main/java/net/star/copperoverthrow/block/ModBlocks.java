package net.star.copperoverthrow.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.custom.*;
import net.star.copperoverthrow.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CopperOverthrow.MOD_ID);

    public static final DeferredBlock<Block> LOG_STRIPPER = registerBlock("log_stripper",
            () -> new LogStripperBlock(
                    BlockBehaviour.Properties.of()
                            .strength(2f)
                            .isValidSpawn(Blocks::never)
                            .mapColor(DyeColor.ORANGE)
                            .sound(SoundType.COPPER)));

    public static final DeferredBlock<Block> COPPER_BEEOSPHERE = registerBlock("copper_beeosphere",
            () -> new CopperBeeosphereBlock(
                    BlockBehaviour.Properties.of()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .mapColor(DyeColor.ORANGE)
                            .sound(SoundType.COPPER)));

    public static final DeferredBlock<Block> COPPER_CHAIN = registerBlock("copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<Block> EXPOSED_COPPER_CHAIN = registerBlock("exposed_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<Block> WEATHERED_COPPER_CHAIN = registerBlock("weathered_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<Block> OXIDIZED_COPPER_CHAIN = registerBlock("oxidized_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.OXIDIZED));
    public static final DeferredBlock<Block> WAXED_COPPER_CHAIN = registerBlock("waxed_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_CHAIN = registerBlock("waxed_exposed_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_CHAIN = registerBlock("waxed_weathered_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_CHAIN = registerBlock("waxed_oxidized_copper_chain",
            () -> new CopperChainBlock(
                    BlockBehaviour.Properties.of()
                            .forceSolidOn()
                            .strength(2f)
                            .noOcclusion()
                            .isValidSpawn(Blocks::never)
                            .sound(SoundType.CHAIN),
                    WeatheringCopper.WeatherState.OXIDIZED));

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

    public static final DeferredBlock<Block> RAIN_DRUM = registerBlock("medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE       )
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.clean_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> EXPOSED_RAIN_DRUM = registerBlock("exposed_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.low_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WEATHERED_RAIN_DRUM = registerBlock("weathered_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.GREEN)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.lower_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> OXIDIZED_RAIN_DRUM = registerBlock("oxidized_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.lowest_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_RAIN_DRUM = registerBlock("waxed_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE       )
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.clean_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_EXPOSED_RAIN_DRUM = registerBlock("waxed_exposed_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.low_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_WEATHERED_RAIN_DRUM = registerBlock("waxed_weathered_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.GREEN)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.lower_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_RAIN_DRUM = registerBlock("waxed_oxidized_medium_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED,
                    1.6f, 1f, 5, 11,3,
                    "tooltip.copperoverthrow.medium_pitch.tooltip",
                    "tooltip.copperoverthrow.lowest_resonance_sounding.tooltip"
            ));

    public static final DeferredBlock<Block> LARGE_RAIN_DRUM = registerBlock("low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE       )
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.clean_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> EXPOSED_LARGE_RAIN_DRUM = registerBlock("exposed_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.low_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WEATHERED_LARGE_RAIN_DRUM = registerBlock("weathered_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.lower_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> OXIDIZED_LARGE_RAIN_DRUM = registerBlock("oxidized_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.lowest_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_LARGE_RAIN_DRUM = registerBlock("waxed_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE       )
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.clean_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_EXPOSED_LARGE_RAIN_DRUM = registerBlock("waxed_exposed_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.low_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_WEATHERED_LARGE_RAIN_DRUM = registerBlock("waxed_weathered_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.lower_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_LARGE_RAIN_DRUM = registerBlock("waxed_oxidized_low_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED,
                    1f, 1.8f, 3, 13, 2,
                    "tooltip.copperoverthrow.low_pitch.tooltip",
                    "tooltip.copperoverthrow.lowest_resonance_sounding.tooltip"
            ));

    public static final DeferredBlock<Block> TINY_RAIN_DRUM = registerBlock("high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE       )
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.clean_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> EXPOSED_TINY_RAIN_DRUM = registerBlock("exposed_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.low_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WEATHERED_TINY_RAIN_DRUM = registerBlock("weathered_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.lower_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> OXIDIZED_TINY_RAIN_DRUM = registerBlock("oxidized_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.lowest_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_TINY_RAIN_DRUM = registerBlock("waxed_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE       )
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.clean_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_EXPOSED_TINY_RAIN_DRUM = registerBlock("waxed_exposed_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.low_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_WEATHERED_TINY_RAIN_DRUM = registerBlock("waxed_weathered_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.lower_resonance_sounding.tooltip"
            ));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_TINY_RAIN_DRUM = registerBlock("waxed_oxidized_high_note_rain_drum",
            () -> new RainDrumBlockTall(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .mapColor(DyeColor.ORANGE)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED,
                    9f, 1.2f, 6, 10, 3,
                    "tooltip.copperoverthrow.high_pitch.tooltip",
                    "tooltip.copperoverthrow.lowest_resonance_sounding.tooltip"
            ));

    public static final DeferredBlock<Block> EXPOSED_TAMTAM = registerBlock("exposed_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<Block> WEATHERED_TAMTAM = registerBlock("weathered_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<Block> OXIDIZED_TAMTAM = registerBlock("oxidized_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED));
    public static final DeferredBlock<Block> TAMTAM = registerBlock("tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<Block> WAXED_TAMTAM = registerBlock("waxed_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<Block> WAXED_EXPOSED_TAMTAM = registerBlock("waxed_exposed_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<Block> WAXED_WEATHERED_TAMTAM = registerBlock("waxed_weathered_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_TAMTAM = registerBlock("waxed_oxidized_tamtam",
            () -> new TamTamBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.IGNORE)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED));

    public static final DeferredBlock<Block> COPPER_LANTERN = registerBlock("copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 13)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<Block> EXPOSED_COPPER_LANTERN = registerBlock("exposed_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 12)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<Block> WEATHERED_COPPER_LANTERN = registerBlock("weathered_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 10)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<Block> OXIDIZED_COPPER_LANTERN = registerBlock("oxidized_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 9)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED));

    public static final DeferredBlock<Block> WAXED_COPPER_LANTERN = registerBlock("waxed_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 13)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.UNAFFECTED));
    public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_LANTERN = registerBlock("waxed_exposed_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 12)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.EXPOSED));
    public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_LANTERN = registerBlock("waxed_weathered_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 10)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.WEATHERED));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_LANTERN = registerBlock("waxed_oxidized_copper_lantern",
            () -> new CopperLanternBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .lightLevel(p_187433_ -> 9)
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER),
                    WeatheringCopper.WeatherState.OXIDIZED));

    public static final DeferredBlock<Block> KITCHEN_BELL = registerBlock("kitchen_bell",
            () -> new KitchenBellBlock(
                    BlockSetType.COPPER,
                    60,
                    BlockBehaviour.Properties.of()
                    .strength(2f)
                    .noOcclusion()
                    .forceSolidOn()
                    .requiresCorrectToolForDrops()
                    .isValidSpawn(Blocks::never)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.COPPER)));


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
