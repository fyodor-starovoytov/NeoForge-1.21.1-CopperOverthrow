package net.star.copperoverthrow.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.HTML;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CopperOverthrow.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.COPPER_CHISEL.get());
        tag(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.COPPER_STEPPER_BOOTS.get());


        tag(Tags.Items.FOODS)
                .add(ModItems.COPPER_COOKIE.get());

        tag(Tags.Items.FOODS_COOKIE)
                .add(ModItems.COPPER_COOKIE.get());

        tag(Tags.Items.TOOLS)
                .add(ModItems.BEE_CATCHER.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.COPPER_STEPPER_BOOTS.get());
    }
}
