package net.star.copperoverthrow.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CopperOverthrow.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<CustomData>>> CAUGHT_BEES =
            register("caught_bees", builder -> builder.persistent(CustomData.CODEC.listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> TEMPORAL_INITIAL_BLOCKPOS =
            register("temporal_initial_blockpos",builder -> builder.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> TEMPORAL_SECONDARY_BLOCKPOS =
            register("temporal_secondary_blockpos",builder -> builder.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Direction>> TEMPORAL_DIRECTION =
            register("temporal_direction",builder -> builder.persistent(Direction.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<BlockPos>>> TEMPORAL_BLOCKPOS_LIST =
            register("temporal_blockpos_list",builder -> builder.persistent(BlockPos.CODEC.listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<BlockPos>>> TEMPORAL_SKETCH_BLOCKPOS_LIST =
            register("temporal_sketch_blockpos_list",builder -> builder.persistent(BlockPos.CODEC.listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> IS_USED =
            register("is_used",builder -> builder.persistent(Codec.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> TEMPORARY_LAST_REGISTERED_BLOCK =
            register("last_registered_block",builder -> builder.persistent(BlockPos.CODEC));





    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name,
                () -> builderOperator.apply(DataComponentType.builder()).build());}

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
