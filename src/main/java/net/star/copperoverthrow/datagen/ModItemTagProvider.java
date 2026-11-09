package net.star.copperoverthrow.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModTags;


import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CopperOverthrow.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(Tags.Items.NUGGETS)
                .add(ModItems.COPPER_NUGGET.get());

        tag(ModTags.Items.C_COPPER_NUGGETS)
                .add(ModItems.COPPER_NUGGET.get());

        tag(Tags.Items.ARMORS_HELMETS)
                .add(ModItems.CRASH_HELMET.get())
                .add(ModItems.COPPER_GLOWING_GOGGLES.get());

        tag(Tags.Items.ARMORS_BOOTS)
                .add(ModItems.COPPER_STEPPER_BOOTS.get());

        tag(Tags.Items.ARMORS_LEGGINGS)
                .add(ModItems.COPPER_STEPPER_LEGGINGS.get());

        tag(Tags.Items.ARMORS_CHESTPLATES)
                .add(ModItems.COPPER_EXOSKELETON.get());


        tag(Tags.Items.TOOLS)
                .addTag(ModTags.Items.C_TOOLS_CATCHERS)
                .addTag(ModTags.Items.C_TOOLS_HAMMERS)
                .addTag(ModTags.Items.C_TOOLS_BUILDERS);

        tag(ItemTags.BREAKS_DECORATED_POTS)
                .addTag(ModTags.Items.C_TOOLS_HAMMERS);

        tag(ModTags.Items.C_TOOLS_HAMMERS)
                .add(ModItems.COPPER_HAMMER.get());
        tag(ModTags.Items.C_TOOLS_CATCHERS)
                .add(ModItems.BEE_CATCHER.get());
        tag(ModTags.Items.C_TOOLS_SAWS)
                .add(ModItems.COPPER_HANDSAW.get());
        tag(ModTags.Items.C_TOOLS_BUILDERS)
                .add(ModItems.COPPER_TROWEL.get())
                .add(ModItems.COPPER_CHISEL.get())
                .add(ModItems.COPPER_SEXTANT.get())
                .add(ModItems.COPPER_LONG_RULER.get())
                .add(ModItems.COPPER_FILL_BUCKET.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.COPPER_STEPPER_BOOTS.get())
                .add(ModItems.COPPER_STEPPER_LEGGINGS.get());



    }
}
