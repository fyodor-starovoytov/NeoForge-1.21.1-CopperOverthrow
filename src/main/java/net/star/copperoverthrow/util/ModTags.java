package net.star.copperoverthrow.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.star.copperoverthrow.CopperOverthrow;

public class ModTags {

     public static class Blocks{
        public static final TagKey<Block> NEEDS_COPPER_TOOL = createTag("needs_copper_tool");
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = createTag("incorrect_for_copper_tool");
        public static final TagKey<Block> MINEABLE_WITH_HAMMER = createTag("mineable_with_hammer");


        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, name));
        }
    }

    public static class Items{
        public static final TagKey<Item> C_TOOLS_HAMMERS = createTag("c", "tools/hammers");
        // public static final TagKey<Item> BEE_CATCHER_TOOL = createTag(CopperOverthrow.MOD_ID, "bee_catcher");
        public static final TagKey<Item> C_TOOLS_CATCHERS = createTag("c", "tools/catchers");

        public static final TagKey<Item> HAMMER_ENCHANTABLE = createTag("c", "hammer/enchantable");


        private static TagKey<Item> createTag(String namespace, String path){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
    }
}
