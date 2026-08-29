package net.star.copperoverthrow.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CopperOverthrow.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.COPPER_CHISEL.get())
                .addTag(ModTags.Items.C_TOOLS_HAMMERS);
        tag(ItemTags.MINING_ENCHANTABLE)
                .addTag(ModTags.Items.C_TOOLS_HAMMERS);
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.COPPER_STEPPER_BOOTS.get());
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.COPPER_STEPPER_LEGGINGS.get());
        tag(ModTags.Items.HAMMER_ENCHANTABLE)
                .addTag(ModTags.Items.C_TOOLS_HAMMERS);

        tag(Tags.Items.FOODS)
                .add(ModItems.COPPER_COOKIE.get());

        tag(Tags.Items.FOODS_COOKIE)
                .add(ModItems.COPPER_COOKIE.get());

        tag(Tags.Items.TOOLS)
                .addTag(ModTags.Items.C_TOOLS_CATCHERS)
                .addTag(ModTags.Items.C_TOOLS_HAMMERS);

        tag(ItemTags.BREAKS_DECORATED_POTS)
                .addTag(ModTags.Items.C_TOOLS_HAMMERS);
        tag(ModTags.Items.C_TOOLS_HAMMERS)
                .add(ModItems.COPPER_HAMMER.get());
        tag(ModTags.Items.C_TOOLS_CATCHERS)
                .add(ModItems.BEE_CATCHER.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.COPPER_STEPPER_BOOTS.get())
                .add(ModItems.COPPER_STEPPER_LEGGINGS.get());


    }
}
