package net.star.copperoverthrow.datagen;

import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
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
                .define('A', Items.COPPER_INGOT)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_CHISEL.get())
                .pattern("A  ")
                .pattern(" A ")
                .pattern("  B")
                .define('B', Items.STICK)
                .define('A', Items.COPPER_INGOT)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(recipeOutput, "copperoverthrow:copper_chisel_alt");

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


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_SCAFFOLDING.get(), 6)
                .pattern("ABA")
                .pattern("A A")
                .pattern("A A")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.COPPER_TRAPDOOR)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_COOKIE.get(), 1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.COOKIE)
                .unlockedBy("has_cookie", has(Items.COOKIE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_STEPPER_BOOTS.get(), 1)
                .pattern("A A")
                .pattern("A A")
                .pattern("BCB")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.FLINT)
                .define('C', Items.SLIME_BALL)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HAMMER.get(), 1)
                .pattern("B")
                .pattern("C")
                .pattern("A")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.COPPER_BLOCK)
                .define('C', Items.STICK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WAXED_COPPER_SCAFFOLDING)
                .requires(ModBlocks.COPPER_SCAFFOLDING)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_copper_scaffolding", has(ModBlocks.COPPER_SCAFFOLDING)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING)
                .requires(ModBlocks.EXPOSED_COPPER_SCAFFOLDING)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_copper_scaffolding", has(ModBlocks.COPPER_SCAFFOLDING)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING)
                .requires(ModBlocks.WEATHERED_COPPER_SCAFFOLDING)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_copper_scaffolding", has(ModBlocks.COPPER_SCAFFOLDING)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING)
                .requires(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_copper_scaffolding", has(ModBlocks.COPPER_SCAFFOLDING)).save(recipeOutput);
    }
}