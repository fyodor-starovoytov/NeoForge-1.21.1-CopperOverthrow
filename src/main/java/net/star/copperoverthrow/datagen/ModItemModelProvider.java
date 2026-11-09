package net.star.copperoverthrow.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CopperOverthrow.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.COPPER_NUGGET.get());
        basicItem(ModItems.COPPER_COOKIE.get());
        handheldItem(ModItems.COPPER_CHISEL.get());
        handheldItem(ModItems.COPPER_FILL_BUCKET.get());

        basicItem(ModItems.COPPER_STEPPER_BOOTS.get());
        basicItem(ModItems.COPPER_STEPPER_LEGGINGS.get());
        basicItem(ModItems.CRASH_HELMET.get());
        basicItem(ModItems.COPPER_GLOWING_GOGGLES.get());
        basicItem(ModItems.COPPER_EXOSKELETON.get());
    }

    private ItemModelBuilder handheldItem(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        return withExistingParent(id.getPath(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(CopperOverthrow.MOD_ID, "item/" + id.getPath()));
    }
}
