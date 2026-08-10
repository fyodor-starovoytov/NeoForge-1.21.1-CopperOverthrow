package net.star.copperoverthrow.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CopperOverthrow.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Creating item models out of textures without having to create manually a .json file
        basicItem(ModItems.COPPER_NUGGET.get());
    }
}
