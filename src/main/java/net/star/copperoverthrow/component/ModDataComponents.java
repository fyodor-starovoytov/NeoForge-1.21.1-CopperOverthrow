package net.star.copperoverthrow.component;

import java.util.List;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;

import javax.xml.datatype.DatatypeConstants;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(CopperOverthrow.MOD_ID);



    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<CustomData>>> CAUGHT_BEES =
            register("caught_bees", builder -> builder.persistent(CustomData.CODEC.listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> TEMPORAL_INITIAL_BLOCKPOS =
            register("temporal_initial_blockpos",builder -> builder.networkSynchronized(BlockPos.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> TEMPORAL_SECONDARY_BLOCKPOS =
            register("temporal_secondary_blockpos",builder -> builder.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Direction>> TEMPORAL_DIRECTION =
            register("temporal_direction",builder -> builder.persistent(Direction.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<BlockPos>>> TEMPORAL_BLOCKPOS_LIST =
            register("temporal_blockpos_list",builder -> builder.persistent(BlockPos.CODEC.listOf()));





    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name,
                () -> builderOperator.apply(DataComponentType.builder()).build());}

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
