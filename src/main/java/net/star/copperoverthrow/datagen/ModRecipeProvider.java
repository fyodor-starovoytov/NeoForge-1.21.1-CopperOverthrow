package net.star.copperoverthrow.datagen;

import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
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
                //EXTREMELY IMPORTANT TO HAVE '' NOT ""
                .define('B', Items.STICK)
                .define('A', Items.COPPER_INGOT)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                // HAVE TO DEFINE THE NAME IF RECIPE OUTPUT HAS MULTIPLE RECIPES
                .save(recipeOutput, "copperoverthrow:copper_chisel_alt");


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_SCAFFOLDING.get(), 6)
                .pattern("ABA")
                .pattern("A A")
                .pattern("A A")
                //EXTREMELY IMPORTANT TO HAVE '' NOT ""
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.COPPER_GRATE)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

    }
}
