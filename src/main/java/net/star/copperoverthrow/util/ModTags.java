package net.star.copperoverthrow.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.star.copperoverthrow.CopperOverthrow;

public class ModTags {

     public static class Blocks{
        public static final TagKey<Block> NEEDS_COPPER_TOOL = createTag(CopperOverthrow.MOD_ID ,"needs_copper_tool");
        public static final TagKey<Block> MINEABLE_WITH_HAMMER = createTag(CopperOverthrow.MOD_ID ,"mineable_with_hammer");

        public static final TagKey<Block> RAIN_DRUMS = createTag("forge", "musical_blocks/rain_drums");
        public static final TagKey<Block> PLATES = createTag("forge", "musical_blocks/plates");
        public static final TagKey<Block> WIND_CHIMES = createTag("forge", "musical_blocks/wind_chimes");
        public static final TagKey<Block> KITCHEN_BELLS = createTag("forge", "musical_blocks/bells/kitchen_bells");
        public static final TagKey<Block> BELLS = createTag("forge", "musical_blocks/bells");
        public static final TagKey<Block> MUSICAL_BLOCKS = createTag("forge", "musical_blocks");

        public static final TagKey<Block> COPPER_LANTERNS = createTag("forge", "lights/copper_lanterns");
        public static final TagKey<Block> LIGHTS = createTag("forge", "lights");

        public static final TagKey<Block> LOG_STRIPPING_STATION = createTag(CopperOverthrow.MOD_ID, "log_stripping_stations");

         private static TagKey<Block> createTag(String namespace, String path){
            return BlockTags.create(ResourceLocation.tryBuild(namespace, path));
        }
    }

    public static class Items{
        public static final TagKey<Item> C_TOOLS_HAMMERS = createTag("forge", "tools/hammers");
        public static final TagKey<Item> C_TOOLS_SAWS = createTag("forge", "tools/saws");
        public static final TagKey<Item> C_TOOLS_CATCHERS = createTag("forge", "tools/catchers");
        public static final TagKey<Item> C_TOOLS_BUILDERS = createTag("forge", "tools/builders");

        public static final TagKey<Item> C_COPPER_NUGGETS = createTag("forge", "nuggets/copper");

        public static final TagKey<Item> HAMMER_ENCHANTABLE = createTag("forge", "hammer/enchantable");


        private static TagKey<Item> createTag(String namespace, String path){
            return ItemTags.create(new ResourceLocation(namespace, path));
        }
    }
}
