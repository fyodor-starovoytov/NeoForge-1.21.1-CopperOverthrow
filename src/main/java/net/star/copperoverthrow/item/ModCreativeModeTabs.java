package net.star.copperoverthrow.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CopperOverthrow.MOD_ID);


    public static final Supplier<CreativeModeTab> COPPEROVERTHROW_ITEMS_TAB = CREATIVE_MODE_TAB.register("copperoverthrow_items_tab",
            () -> CreativeModeTab.builder()

                    .icon(() -> new ItemStack(ModItems.COPPER_HAMMER.get()))
                    .title(Component.translatable("creativetab.copperoverthrow.copperoverthrow_items"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.COPPER_NUGGET.get());

                        output.accept(ModItems.COPPER_CHISEL.get());
                        output.accept(ModItems.COPPER_TROWEL.get());
                        output.accept(ModItems.COPPER_LONG_RULER.get());
                        output.accept(ModItems.COPPER_SEXTANT.get());
                        output.accept(ModItems.COPPER_FILL_BUCKET.get());

                        output.accept(ModItems.COPPER_HAMMER.get());
                        output.accept(ModItems.COPPER_HANDSAW.get());

                        output.accept(ModItems.COPPER_GLOWING_GOGGLES.get());
                        output.accept(ModItems.CRASH_HELMET.get());
                        output.accept(ModItems.COPPER_EXOSKELETON.get());
                        output.accept(ModItems.COPPER_STEPPER_LEGGINGS.get());
                        output.accept(ModItems.COPPER_STEPPER_BOOTS.get());

                        output.accept(ModItems.COPPER_COOKIE.get());

                        output.accept(ModItems.BEE_CATCHER.get());


                    }).build());


    public static final Supplier<CreativeModeTab> COPPEROVERTHROW_BLOCK_TAB = CREATIVE_MODE_TAB.register("copperoverthrow_blocks_tab",
            () -> CreativeModeTab.builder()

                    .icon(() -> new ItemStack(ModBlocks.TAMTAM.get()))

                    .withTabsBefore(ResourceLocation.tryBuild(CopperOverthrow.MOD_ID, "copperoverthrow_items_tab"))
                    .title(Component.translatable("creativetab.copperoverthrow.copperoverthrow_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.LOG_STRIPPER.get());
                        output.accept(ModBlocks.TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.RAIN_DRUM.get());
                        output.accept(ModBlocks.LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.TAMTAM.get());
                        output.accept(ModBlocks.KITCHEN_BELL.get());
                        output.accept(ModBlocks.COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.COPPER_CHAIN.get());
                        output.accept(ModBlocks.COPPER_LANTERN.get());

                        output.accept(ModBlocks.EXPOSED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.EXPOSED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.EXPOSED_RAIN_DRUM.get());
                        output.accept(ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.EXPOSED_TAMTAM.get());
                        output.accept(ModBlocks.EXPOSED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.EXPOSED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.EXPOSED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.WEATHERED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.WEATHERED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.WEATHERED_RAIN_DRUM.get());
                        output.accept(ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.WEATHERED_TAMTAM.get());
                        output.accept(ModBlocks.WEATHERED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.WEATHERED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.WEATHERED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.OXIDIZED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.OXIDIZED_RAIN_DRUM.get());
                        output.accept(ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.OXIDIZED_TAMTAM.get());
                        output.accept(ModBlocks.OXIDIZED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.OXIDIZED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.OXIDIZED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.WAXED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.WAXED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_TAMTAM.get());
                        output.accept(ModBlocks.WAXED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.WAXED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.WAXED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.WAXED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.WAXED_EXPOSED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_TAMTAM.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.WAXED_WEATHERED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_TAMTAM.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.WAXED_OXIDIZED_LOG_STRIPPER.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_TINY_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_TAMTAM.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_KITCHEN_BELL.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN.get());

                        output.accept(ModBlocks.HOLOGRAM_BLOCK.get());
                        output.accept(ModBlocks.INVERTED_HOLOGRAM_BLOCK.get());

                        output.accept(ModBlocks.RED_WIND_CHIME.get());
                        output.accept(ModBlocks.LUNAR_WIND_CHIME.get());
                        output.accept(ModBlocks.BAMBOO_WIND_CHIME.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
