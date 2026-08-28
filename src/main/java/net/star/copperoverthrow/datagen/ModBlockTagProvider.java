package net.star.copperoverthrow.datagen;

import com.jcraft.jorbis.Block;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CopperOverthrow.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COPPER_SCAFFOLDING.get())
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.COPPER_SCAFFOLDING.get())
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());

        tag(BlockTags.CLIMBABLE)
                .add(ModBlocks.COPPER_SCAFFOLDING.get())
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());

        tag(BlockTags.FALL_DAMAGE_RESETTING)
                .add(ModBlocks.COPPER_SCAFFOLDING.get())
                .add(ModBlocks.EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.OXIDIZED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_EXPOSED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_WEATHERED_COPPER_SCAFFOLDING.get())
                .add(ModBlocks.WAXED_OXIDIZED_COPPER_SCAFFOLDING.get());

        tag(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(BlockTags.STONE_BRICKS)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.STONE_ORE_REPLACEABLES)
                .addTag(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        tag(ModTags.Blocks.MINEABLE_WITH_HAMMER)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE);


        tag(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .remove(ModTags.Blocks.NEEDS_COPPER_TOOL);
        //NO
    }
}
