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

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

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
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);


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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOG_STRIPPER.get(), 1)
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.COPPER_BLOCK)
                .define('B', Tags.Items.STONES)
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_BEEOSPHERE.get(), 1)
                .pattern("CAC")
                .pattern("DBD")
                .pattern("CAC")
                .define('A', Items.COPPER_BLOCK)
                .define('B', Items.BEEHIVE)
                .define('C', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_COOKIE.get(), 1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_COPPER)
                .define('B', Items.COOKIE)
                .unlockedBy("has_cookie", has(Items.COOKIE)).save(recipeOutput);

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
    }

    protected void addWaxingRecipe(RecipeOutput recipeOutput, ItemLike unwaxed, ItemLike waxed, String unwaxedName) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, waxed)
                .requires(unwaxed)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_" + unwaxedName, has(unwaxed))
                .save(recipeOutput);
    }
}