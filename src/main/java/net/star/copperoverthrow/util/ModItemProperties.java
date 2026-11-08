package net.star.copperoverthrow.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;

public class ModItemProperties {
    public static void addCustomItemProperties(){

        ItemProperties.register(ModItems.BEE_CATCHER.get(), new ResourceLocation(CopperOverthrow.MOD_ID, "1bee"),
                (stack, level, entity, seed) -> stack.hasTag() && stack.getTag().contains("caught_bees", Tag.TAG_LIST) && stack.getTag().getList("caught_bees", Tag.TAG_COMPOUND).size() == 1 ? 1f : 0f);

        ItemProperties.register(ModItems.BEE_CATCHER.get(), new ResourceLocation(CopperOverthrow.MOD_ID, "2bees"),
                (stack, level, entity, seed) -> stack.hasTag() && stack.getTag().contains("caught_bees", Tag.TAG_LIST) && stack.getTag().getList("caught_bees", Tag.TAG_COMPOUND).size() == 2 ? 1f : 0f);

        ItemProperties.register(ModItems.BEE_CATCHER.get(), new ResourceLocation(CopperOverthrow.MOD_ID, "3bees"),
                (stack, level, entity, seed) -> stack.hasTag() && stack.getTag().contains("caught_bees", Tag.TAG_LIST) && stack.getTag().getList("caught_bees", Tag.TAG_COMPOUND).size() == 3 ? 1f : 0f);

        ItemProperties.register(ModItems.COPPER_HANDSAW.get(), new ResourceLocation(CopperOverthrow.MOD_ID, "using_saw"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1f : 0f);

        ItemProperties.register(ModItems.COPPER_SEXTANT.get(), new ResourceLocation(CopperOverthrow.MOD_ID, "using_tape"),
                (stack, level, entity, seed) -> stack.hasTag() && stack.getTag().contains("temporal_initial_blockpos") ? 1f : 0f);

        ItemProperties.register(ModItems.COPPER_LONG_RULER.get(), new ResourceLocation(CopperOverthrow.MOD_ID, "using_ruler"),
                (stack, level, entity, seed) -> stack.hasTag() && stack.getTag().contains("temporal_initial_blockpos") ? 1f : 0f);
    }
}
