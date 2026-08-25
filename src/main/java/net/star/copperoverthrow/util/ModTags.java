package net.star.copperoverthrow.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
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

}
