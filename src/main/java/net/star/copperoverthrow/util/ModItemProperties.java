package net.star.copperoverthrow.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;

import java.util.List;

public class ModItemProperties {
    public static void addCustomItemProperties(){

        ItemProperties.register(ModItems.BEE_CATCHER.get(), ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "1bee"),
                (stack, level, entity, seed) -> stack.getOrDefault(ModDataComponents.CAUGHT_BEES, List.of()).size() == 1 ? 1f : 0f);

        ItemProperties.register(ModItems.BEE_CATCHER.get(), ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "2bees"),
                (stack, level, entity, seed) -> stack.getOrDefault(ModDataComponents.CAUGHT_BEES, List.of()).size() == 2 ? 1f : 0f);

        ItemProperties.register(ModItems.BEE_CATCHER.get(), ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "3bees"),
                (stack, level, entity, seed) -> stack.getOrDefault(ModDataComponents.CAUGHT_BEES, List.of()).size() == 3 ? 1f : 0f);


        ItemProperties.register(ModItems.COPPER_HANDSAW.get(), ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "using_saw"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1f : 0f);

        ItemProperties.register(ModItems.COPPER_SEXTANT.get(), ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "using_tape"),
                (stack, level, entity, seed) -> stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null ? 1f : 0f);

        ItemProperties.register(ModItems.COPPER_LONG_RULER.get(), ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "using_ruler"),
                (stack, level, entity, seed) -> stack.get(ModDataComponents.TEMPORAL_INITIAL_BLOCKPOS) != null ? 1f : 0f);

    }

}
