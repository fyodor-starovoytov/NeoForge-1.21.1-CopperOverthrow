package net.star.copperoverthrow.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.entity.custom.CopperBeeosphereBlockEntity;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CopperOverthrow.MOD_ID);

    public static final Supplier<BlockEntityType<LogStripperBlockEntity>> LOG_STRIPPER_BE =
            BLOCK_ENTITIES.register("log_stripper_be", ()-> BlockEntityType.Builder.of(
                    LogStripperBlockEntity::new, ModBlocks.LOG_STRIPPER.get())
                    .build(null));

    public static final Supplier<BlockEntityType<CopperBeeosphereBlockEntity>> COPPER_BEEOSPHERE_BE =
            BLOCK_ENTITIES.register("copper_beeosphere_be", ()-> BlockEntityType.Builder.of(
                            CopperBeeosphereBlockEntity::new, ModBlocks.COPPER_BEEOSPHERE.get())
                    .build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
