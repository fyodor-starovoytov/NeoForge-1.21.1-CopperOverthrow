package net.star.copperoverthrow.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.entity.custom.*;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CopperOverthrow.MOD_ID);

    public static final Supplier<BlockEntityType<LogStripperBlockEntity>> LOG_STRIPPER_BE =
            BLOCK_ENTITIES.register("log_stripper_be", ()-> BlockEntityType.Builder.of(
                    LogStripperBlockEntity::new,
                            ModBlocks.LOG_STRIPPER.get(),
                            ModBlocks.EXPOSED_LOG_STRIPPER.get(),
                            ModBlocks.WEATHERED_LOG_STRIPPER.get(),
                            ModBlocks.OXIDIZED_LOG_STRIPPER.get(),
                            ModBlocks.WAXED_LOG_STRIPPER.get(),
                            ModBlocks.WAXED_EXPOSED_LOG_STRIPPER.get(),
                            ModBlocks.WAXED_WEATHERED_LOG_STRIPPER.get(),
                            ModBlocks.WAXED_OXIDIZED_LOG_STRIPPER.get()
                    )
                    .build(null));

    public static final Supplier<BlockEntityType<CopperBeeosphereBlockEntity>> COPPER_BEEOSPHERE_BE =
            BLOCK_ENTITIES.register("copper_beeosphere_be", ()-> BlockEntityType.Builder.of(
                            CopperBeeosphereBlockEntity::new, ModBlocks.COPPER_BEEOSPHERE.get())
                    .build(null));

    public static final Supplier<BlockEntityType<TamTamBlockEntity>> TAMTAM_BE =
            BLOCK_ENTITIES.register("tamtam_be", ()-> BlockEntityType.Builder.of(
                            TamTamBlockEntity::new,
                            ModBlocks.TAMTAM.get(),
                            ModBlocks.EXPOSED_TAMTAM.get(),
                            ModBlocks.WEATHERED_TAMTAM.get(),
                            ModBlocks.OXIDIZED_TAMTAM.get(),
                            ModBlocks.WAXED_TAMTAM.get(),
                            ModBlocks.WAXED_EXPOSED_TAMTAM.get(),
                            ModBlocks.WAXED_WEATHERED_TAMTAM.get(),
                            ModBlocks.WAXED_OXIDIZED_TAMTAM.get())
                    .build(null));

    public static final Supplier<BlockEntityType<RedWindChimeBlockEntity>> RED_WIND_CHIME_BE =
            BLOCK_ENTITIES.register("red_wind_chime_be", ()-> BlockEntityType.Builder.of(
                            RedWindChimeBlockEntity::new,
                            ModBlocks.RED_WIND_CHIME.get())
                    .build(null));
    public static final Supplier<BlockEntityType<BambooWindChimeBlockEntity>> BAMBOO_WIND_CHIME_BE =
            BLOCK_ENTITIES.register("bamboo_wind_chime_be", ()-> BlockEntityType.Builder.of(
                            BambooWindChimeBlockEntity::new,
                            ModBlocks.RED_WIND_CHIME.get())
                    .build(null));
    public static final Supplier<BlockEntityType<LunarWindChimeBlockEntity>> LUNAR_WIND_CHIME_BE =
            BLOCK_ENTITIES.register("lunar_wind_chime_be", ()-> BlockEntityType.Builder.of(
                            LunarWindChimeBlockEntity::new,
                            ModBlocks.LUNAR_WIND_CHIME.get())
                    .build(null));


    public static final Supplier<BlockEntityType<KitchenBellBlockEntity>> KITCHEN_BELL_BE =
            BLOCK_ENTITIES.register("kitchen_bell_be", () -> BlockEntityType.Builder.of(
                            KitchenBellBlockEntity::new,
                            ModBlocks.KITCHEN_BELL.get(),
                            ModBlocks.EXPOSED_KITCHEN_BELL.get(),
                            ModBlocks.WEATHERED_KITCHEN_BELL.get(),
                            ModBlocks.OXIDIZED_KITCHEN_BELL.get(),
                            ModBlocks.WAXED_KITCHEN_BELL.get(),
                            ModBlocks.WAXED_EXPOSED_KITCHEN_BELL.get(),
                            ModBlocks.WAXED_WEATHERED_KITCHEN_BELL.get(),
                            ModBlocks.WAXED_OXIDIZED_KITCHEN_BELL.get())
                    .build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
