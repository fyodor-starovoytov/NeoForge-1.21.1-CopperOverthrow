package net.star.copperoverthrow.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CopperOverthrow.MOD_ID);


    public static final Supplier<CreativeModeTab> COPPEROVERTHROW_ITEMS_TAB = CREATIVE_MODE_TAB.register("copperoverthrow_items_tab",
            () -> CreativeModeTab.builder()

                    .icon(() -> new ItemStack(ModItems.COPPER_CHISEL.get()))
                    .title(Component.translatable("creativetab.copperoverthrow.copperoverthrow_items"))
                    .displayItems((itemDisplayParameters, output) -> {

                        //output.accept(ModItems.COPPER_NUGGET);
                        output.accept(ModItems.COPPER_CHISEL);
                        output.accept(ModItems.COPPER_HAMMER);
                        output.accept(ModItems.COPPER_STEPPER_LEGGINGS);
                        output.accept(ModItems.COPPER_STEPPER_BOOTS);
                        output.accept(ModItems.COPPER_COOKIE);
                        output.accept(ModItems.BEE_CATCHER);


                    }).build());


    public static final Supplier<CreativeModeTab> COPPEROVERTHROW_BLOCK_TAB = CREATIVE_MODE_TAB.register("copperoverthrow_blocks_tab",
            () -> CreativeModeTab.builder()

                    .icon(() -> new ItemStack(ModBlocks.COPPER_SCAFFOLDING.get()))

                    //Defines the order of the TABS with blocks being after items ONLY IF THERE IS MULTIPLE TABS
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copperoverthrow_items_tab"))
                    .title(Component.translatable("creativetab.copperoverthrow.copperoverthrow_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.EXPOSED_COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.WEATHERED_COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.WAXED_COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING);
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING);

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
