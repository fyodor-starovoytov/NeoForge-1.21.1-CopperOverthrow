package net.star.copperoverthrow.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.item.ModItems;

import java.util.function.Consumer;

public class ModAdvancementSubProvider implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper) {
        AdvancementHolder copperIngotAdv = Advancement.Builder.advancement()
                .parent(ResourceLocation.withDefaultNamespace("story/mine_stone"))
                .display(
                        Items.COPPER_INGOT,
                        Component.translatable("advancements.copperoverthrow.copper_ingot.title"),
                        Component.translatable("advancements.copperoverthrow.copper_ingot.description"),
                        null, // Background is null since it's attached to an existing parent tab
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_handsaw", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copper_ingot"), existingFileHelper);

        AdvancementHolder copperScaffolding = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModBlocks.COPPER_SCAFFOLDING.get(),
                        Component.translatable("advancements.copperoverthrow.copper_scaffolding.title"),
                        Component.translatable("advancements.copperoverthrow.copper_scaffolding.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_scaffolding", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COPPER_SCAFFOLDING.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copper_scaffolding"), existingFileHelper);
    }
}
