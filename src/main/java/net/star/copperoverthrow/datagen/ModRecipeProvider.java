package net.star.copperoverthrow.datagen;

import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COPPER_INGOT, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.COPPER_NUGGET.get())
                .unlockedBy("has_copper_nugget", has(ModItems.COPPER_NUGGET.get())).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_CHISEL.get())
                .pattern("  A")
                .pattern(" A ")
                .pattern("B  ")
                //EXTREMELY IMPORTANT TO HAVE '' NOT ""
                .define('B', Items.STICK)
                .define('A', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_CHISEL.get())
                .pattern("A  ")
                .pattern(" A ")
                .pattern("  B")
                .define('B', Items.STICK)
                .define('A', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(recipeOutput, "copperoverthrow:copper_chisel_alt");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_TROWEL.get())
                .pattern("AB ")
                .pattern("AAB")
                //EXTREMELY IMPORTANT TO HAVE '' NOT ""
                .define('B', Items.STICK)
                .define('A', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_TROWEL.get())
                .pattern(" BA")
                .pattern("BAA")
                .define('B', Items.STICK)
                .define('A', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(recipeOutput, "copperoverthrow:copper_trowel_alt");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BEE_CATCHER.get())
                .pattern("  C")
                .pattern(" BA")
                .pattern("B A")
                .define('C', Items.LIGHT_BLUE_WOOL)
                .define('B', Items.STICK)
                .define('A', Items.STRING)
                .unlockedBy("has_string", has(Items.STRING))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BEE_CATCHER.get())
                .pattern("C  ")
                .pattern("AB ")
                .pattern("A B")
                //EXTREMELY IMPORTANT TO HAVE '' NOT ""
                .define('C', Items.LIGHT_BLUE_WOOL)
                .define('B', Items.STICK)
                .define('A', Items.STRING)
                .unlockedBy("has_string", has(Items.STRING))
                .save(recipeOutput, "copperoverthrow:bee_catcher_alt");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HAMMER.get(), 1)
                .pattern("B")
                .pattern("C")
                .define('B', Items.COPPER_BLOCK)
                .define('C', Tags.Items.RODS_BLAZE)
                .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HANDSAW.get(), 1)
                .pattern("  B")
                .pattern(" BA")
                .pattern("CA ")
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('A', ModTags.Items.C_COPPER_NUGGETS)
                .define('C', Tags.Items.RODS_BLAZE)
                .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HANDSAW.get(), 1)
                .pattern("B  ")
                .pattern("AB ")
                .pattern(" AC")
                .define('B', Tags.Items.INGOTS_COPPER)
                .define('A', ModTags.Items.C_COPPER_NUGGETS)
                .define('C', Tags.Items.RODS_BLAZE)
                .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
                .save(recipeOutput, "copperoverthrow:copper_handsaw_alt");



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAIN_DRUM.get(), 2)
                .pattern("CBC")
                .pattern(" A ")
                .define('A', Tags.Items.INGOTS_IRON)
                .define('B', Items.NOTE_BLOCK)
                .define('C', Items.COPPER_BLOCK)
                .unlockedBy("has_note_block", has(Items.NOTE_BLOCK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TINY_RAIN_DRUM.get(), 2)
                .pattern("C")
                .pattern("B")
                .pattern("A")
                .define('A', Tags.Items.INGOTS_IRON)
                .define('B', Items.NOTE_BLOCK)
                .define('C', Items.COPPER_BLOCK)
                .unlockedBy("has_note_block", has(Items.NOTE_BLOCK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LARGE_RAIN_DRUM.get(), 2)
                .pattern("CBC")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.NOTE_BLOCK)
                .define('C', Items.COPPER_BLOCK)
                .unlockedBy("has_note_block", has(Items.NOTE_BLOCK)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_SCAFFOLDING.get(), 6)
                .pattern("ABA")
                .pattern("A A")
                .pattern("A A")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Items.COPPER_TRAPDOOR)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_CHAIN.get(), 1)
                .pattern("B")
                .pattern("A")
                .pattern("B")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', ModTags.Items.C_COPPER_NUGGETS)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TAMTAM.get(), 1)
                .pattern("AA")
                .pattern("BB")
                .pattern("BB")
                .define('A', ModBlocks.COPPER_CHAIN.get())
                .define('B', Items.COPPER_BLOCK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_LANTERN.get(), 1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', ModTags.Items.C_COPPER_NUGGETS)
                .define('B', Items.TORCH)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOG_STRIPPER.get(), 1)
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.COPPER_BLOCK)
                .define('B', Tags.Items.STONES)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KITCHEN_BELL.get(), 1)
                .pattern(" D ")
                .pattern("CBC")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_IRON)
                .define('B', ModTags.Items.C_COPPER_NUGGETS)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('D', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

/*
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_BEEOSPHERE.get(), 1)
                .pattern("CAC")
                .pattern("DBD")
                .pattern("CAC")
                .define('A', Items.COPPER_BLOCK)
                .define('B', Items.BEEHIVE)
                .define('C', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB)).save(recipeOutput);
*/
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_COOKIE.get(), 1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Items.COOKIE)
                .unlockedBy("has_cookie", has(Items.COOKIE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRASH_HELMET.get(), 1)
                .pattern("CAC")
                .pattern("ABA")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Items.HONEYCOMB)
                .define('C', Tags.Items.DYES_YELLOW)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_STEPPER_BOOTS.get(), 1)
                .pattern("A A")
                .pattern("A A")
                .pattern("BCB")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Items.STICKY_PISTON)
                .define('C', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_STEPPER_LEGGINGS.get(), 1)
                .pattern("BCB")
                .pattern("A A")
                .pattern("A A")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Items.PISTON)
                .define('C', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 9)
                        .requires(Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

//        addWaxingRecipe(recipeOutput, ModBlocks.LOG_STRIPPER.get(), ModBlocks.WAXED_LOG_STRIPPER.get(), "log_stripper");
//        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_LOG_STRIPPER.get(), ModBlocks.WAXED_EXPOSED_LOG_STRIPPER.get(), "exposed_log_stripper");
//        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_LOG_STRIPPER.get(), ModBlocks.WAXED_WEATHERED_LOG_STRIPPER.get(), "weathered_log_stripper");
//        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_LOG_STRIPPER.get(), ModBlocks.WAXED_OXIDIZED_LOG_STRIPPER.get(), "oxidized_log_stripper");

        addWaxingRecipe(recipeOutput, ModBlocks.COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_COPPER_SCAFFOLDING.get(), "copper_scaffolding");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get(), "exposed_copper_scaffolding");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get(), "weathered_copper_scaffolding");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get(), ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get(), "oxidized_copper_scaffolding");

        addWaxingRecipe(recipeOutput, ModBlocks.RAIN_DRUM.get(), ModBlocks.WAXED_RAIN_DRUM.get(), "rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_RAIN_DRUM.get(), ModBlocks.WAXED_EXPOSED_RAIN_DRUM.get(), "exposed_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_RAIN_DRUM.get(), ModBlocks.WAXED_WEATHERED_RAIN_DRUM.get(), "weathered_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_RAIN_DRUM.get(), ModBlocks.WAXED_OXIDIZED_RAIN_DRUM.get(), "oxidized_rain_drum");

        addWaxingRecipe(recipeOutput, ModBlocks.TINY_RAIN_DRUM.get(), ModBlocks.WAXED_TINY_RAIN_DRUM.get(), "tiny_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_TINY_RAIN_DRUM.get(), ModBlocks.WAXED_EXPOSED_TINY_RAIN_DRUM.get(), "exposed_tiny_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_TINY_RAIN_DRUM.get(), ModBlocks.WAXED_WEATHERED_TINY_RAIN_DRUM.get(), "weathered_tiny_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get(), ModBlocks.WAXED_OXIDIZED_TINY_RAIN_DRUM.get(), "oxidized_tiny_rain_drum");

        addWaxingRecipe(recipeOutput, ModBlocks.LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_LARGE_RAIN_DRUM.get(), "large_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_EXPOSED_LARGE_RAIN_DRUM.get(), "exposed_large_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_WEATHERED_LARGE_RAIN_DRUM.get(), "weathered_large_rain_drum");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get(), ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get(), "oxidized_large_rain_drum");

        addWaxingRecipe(recipeOutput, ModBlocks.TAMTAM.get(), ModBlocks.WAXED_TAMTAM.get(), "tamtam");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_TAMTAM.get(), ModBlocks.WAXED_EXPOSED_TAMTAM.get(), "exposed_tamtam");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_TAMTAM.get(), ModBlocks.WAXED_WEATHERED_TAMTAM.get(), "weathered_tamtam");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_TAMTAM.get(), ModBlocks.WAXED_OXIDIZED_TAMTAM.get(), "oxidized_tamtam");

        addWaxingRecipe(recipeOutput, ModBlocks.COPPER_CHAIN.get(), ModBlocks.WAXED_COPPER_CHAIN.get(), "copper_chain");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_COPPER_CHAIN.get(), ModBlocks.WAXED_EXPOSED_COPPER_CHAIN.get(), "exposed_copper_chain");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_COPPER_CHAIN.get(), ModBlocks.WAXED_WEATHERED_COPPER_CHAIN.get(), "weathered_copper_chain");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_COPPER_CHAIN.get(), ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN.get(), "oxidized_copper_chain");

        addWaxingRecipe(recipeOutput, ModBlocks.COPPER_LANTERN.get(), ModBlocks.WAXED_COPPER_LANTERN.get(), "copper_lantern");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_COPPER_LANTERN.get(), ModBlocks.WAXED_EXPOSED_COPPER_LANTERN.get(), "exposed_copper_lantern");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_COPPER_LANTERN.get(), ModBlocks.WAXED_WEATHERED_COPPER_LANTERN.get(), "weathered_copper_lantern");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_COPPER_LANTERN.get(), ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN.get(), "oxidized_copper_lantern");

        addWaxingRecipe(recipeOutput, ModBlocks.KITCHEN_BELL.get(), ModBlocks.WAXED_KITCHEN_BELL.get(), "kitchen_bell");
        addWaxingRecipe(recipeOutput, ModBlocks.EXPOSED_KITCHEN_BELL.get(), ModBlocks.WAXED_EXPOSED_KITCHEN_BELL.get(), "exposed_kitchen_bell");
        addWaxingRecipe(recipeOutput, ModBlocks.WEATHERED_KITCHEN_BELL.get(), ModBlocks.WAXED_WEATHERED_KITCHEN_BELL.get(), "weathered_kitchen_bell");
        addWaxingRecipe(recipeOutput, ModBlocks.OXIDIZED_KITCHEN_BELL.get(), ModBlocks.WAXED_OXIDIZED_KITCHEN_BELL.get(), "oxidized_kitchen_bell");


    }


    protected void addWaxingRecipe(RecipeOutput recipeOutput, ItemLike unwaxed, ItemLike waxed, String unwaxedName) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, waxed)
                .requires(unwaxed)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_" + unwaxedName, has(unwaxed))
                .save(recipeOutput);
    }
}
