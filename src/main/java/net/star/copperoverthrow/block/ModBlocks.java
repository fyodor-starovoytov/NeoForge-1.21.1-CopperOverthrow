package net.star.copperoverthrow.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CopperOverthrow.MOD_ID);

//Blocks:
public static final DeferredBlock<Block> COPPER_SCAFFOLDING = registerBlock("copper_scaffolding",
        () -> new Block(BlockBehaviour.Properties.of().strength(1f).noOcclusion().instabreak().sound(SoundType.COPPER).dynamicShape()));
//ПОФИКСИ БАГ С ТЕНЬЮ И БОЛЕЕ ОРАНГЖВАЕВЙЫЙ!!



    private static <T extends Block> DeferredBlock<T> registerBlock (String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    } //Connecting an item to the block

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
