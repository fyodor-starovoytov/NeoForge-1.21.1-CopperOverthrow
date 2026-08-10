package net.star.copperoverthrow.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CopperOverthrow.MOD_ID  , exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Only for easy blocks with texture having the same name as model
        //blockWithItem(ModBlocks.COPPER_SCAFFOLDING);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
