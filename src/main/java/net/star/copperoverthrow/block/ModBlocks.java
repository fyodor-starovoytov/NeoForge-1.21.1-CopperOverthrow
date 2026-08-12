package net.star.copperoverthrow.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.custom.CopperScaffoldingBlock;
import net.star.copperoverthrow.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CopperOverthrow.MOD_ID);

//Blocks:
public static final DeferredBlock<CopperScaffoldingBlock> COPPER_SCAFFOLDING = registerBlock("copper_scaffolding",
        () -> new CopperScaffoldingBlock(BlockBehaviour.Properties.of()
                .strength(3f)
                .noCollission()
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .destroyTime(0.25f)
                .isValidSpawn(Blocks::never)
                .mapColor(DyeColor.ORANGE)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.COPPER)
                .dynamicShape()));



    private static <T extends Block> DeferredBlock<T> registerBlock (String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    } //Connecting an item to the block

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        if (name.equals("copper_scaffolding")) {
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
