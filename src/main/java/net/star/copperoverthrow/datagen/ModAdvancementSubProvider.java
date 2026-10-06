package net.star.copperoverthrow.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
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
import net.star.copperoverthrow.util.ModTags;

import java.util.function.Consumer;

public class ModAdvancementSubProvider implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper) {
        AdvancementHolder copperIngotAdv = Advancement.Builder.advancement()
                .parent(ResourceLocation.withDefaultNamespace("story/upgrade_tools"))
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
                .addCriterion("has_copper_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
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

        AdvancementHolder copperChisel = Advancement.Builder.advancement()
                .parent(copperScaffolding)
                .display(
                        ModItems.COPPER_CHISEL.get(),
                        Component.translatable("advancements.copperoverthrow.copper_chisel.title"),
                        Component.translatable("advancements.copperoverthrow.copper_chisel.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_chisel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_CHISEL.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copper_chisel"), existingFileHelper);

        AdvancementHolder copperTrowel = Advancement.Builder.advancement()
                .parent(copperScaffolding)
                .display(
                        ModItems.COPPER_TROWEL.get(),
                        Component.translatable("advancements.copperoverthrow.copper_trowel.title"),
                        Component.translatable("advancements.copperoverthrow.copper_trowel.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_trowel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_TROWEL.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copper_trowel"), existingFileHelper);

        AdvancementHolder logStripper = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModBlocks.LOG_STRIPPER.get(),
                        Component.translatable("advancements.copperoverthrow.log_stripper.title"),
                        Component.translatable("advancements.copperoverthrow.log_stripper.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_stripper", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.LOG_STRIPPER.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "log_stripper"), existingFileHelper);

        AdvancementHolder copperHandsaw = Advancement.Builder.advancement()
                .parent(logStripper)
                .display(
                        ModItems.COPPER_HANDSAW.get(),
                        Component.translatable("advancements.copperoverthrow.copper_handsaw.title"),
                        Component.translatable("advancements.copperoverthrow.copper_handsaw.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_handsaw", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_HANDSAW.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copper_handsaw"), existingFileHelper);

        AdvancementHolder stepperSet = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModItems.COPPER_STEPPER_BOOTS.get(),
                        Component.translatable("advancements.copperoverthrow.stepper_set.title"),
                        Component.translatable("advancements.copperoverthrow.stepper_set.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_stepper", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_STEPPER_BOOTS.get(), ModItems.COPPER_STEPPER_LEGGINGS.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "stepper_set"), existingFileHelper);

        AdvancementHolder copperHammer = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModItems.COPPER_HAMMER.get(),
                        Component.translatable("advancements.copperoverthrow.copper_hammer.title"),
                        Component.translatable("advancements.copperoverthrow.copper_hammer.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_hammer", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_HAMMER.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "copper_hammer"), existingFileHelper);

        AdvancementHolder musical = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModBlocks.LARGE_RAIN_DRUM,
                        Component.translatable("advancements.copperoverthrow.musical.title"),
                        Component.translatable("advancements.copperoverthrow.musical.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_musical", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ModBlocks.RAIN_DRUM.get(),
                        ModBlocks.TINY_RAIN_DRUM.get(),
                        ModBlocks.LARGE_RAIN_DRUM.get(),
                        ModBlocks.TAMTAM.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "musical"), existingFileHelper);

        AdvancementHolder orchestra = Advancement.Builder.advancement()
                .parent(musical)
                .display(
                        ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM,
                        Component.translatable("advancements.copperoverthrow.orchestra.title"),
                        Component.translatable("advancements.copperoverthrow.orchestra.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        true
                )
                .addCriterion("has_orchestra", InventoryChangeTrigger.TriggerInstance.hasItems(

                        ModBlocks.RAIN_DRUM.get(),
                        ModBlocks.EXPOSED_RAIN_DRUM.get(),
                        ModBlocks.WEATHERED_RAIN_DRUM.get(),
                        ModBlocks.OXIDIZED_RAIN_DRUM.get(),
                        ModBlocks.WAXED_RAIN_DRUM.get(),
                        ModBlocks.WAXED_EXPOSED_RAIN_DRUM.get(),
                        ModBlocks.WAXED_WEATHERED_RAIN_DRUM.get(),
                        ModBlocks.WAXED_OXIDIZED_RAIN_DRUM.get(),

                        ModBlocks.TINY_RAIN_DRUM.get(),
                        ModBlocks.EXPOSED_TINY_RAIN_DRUM.get(),
                        ModBlocks.WEATHERED_TINY_RAIN_DRUM.get(),
                        ModBlocks.OXIDIZED_TINY_RAIN_DRUM.get(),
                        ModBlocks.WAXED_TINY_RAIN_DRUM.get(),
                        ModBlocks.WAXED_EXPOSED_TINY_RAIN_DRUM.get(),
                        ModBlocks.WAXED_WEATHERED_TINY_RAIN_DRUM.get(),
                        ModBlocks.WAXED_OXIDIZED_TINY_RAIN_DRUM.get(),

                        ModBlocks.LARGE_RAIN_DRUM.get(),
                        ModBlocks.EXPOSED_LARGE_RAIN_DRUM.get(),
                        ModBlocks.WEATHERED_LARGE_RAIN_DRUM.get(),
                        ModBlocks.OXIDIZED_LARGE_RAIN_DRUM.get(),
                        ModBlocks.WAXED_LARGE_RAIN_DRUM.get(),
                        ModBlocks.WAXED_EXPOSED_LARGE_RAIN_DRUM.get(),
                        ModBlocks.WAXED_WEATHERED_LARGE_RAIN_DRUM.get(),
                        ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get(),

                        ModBlocks.TAMTAM.get(),
                        ModBlocks.EXPOSED_TAMTAM.get(),
                        ModBlocks.WEATHERED_TAMTAM.get(),
                        ModBlocks.OXIDIZED_TAMTAM.get(),
                        ModBlocks.WAXED_TAMTAM.get(),
                        ModBlocks.WAXED_EXPOSED_TAMTAM.get(),
                        ModBlocks.WAXED_WEATHERED_TAMTAM.get(),
                        ModBlocks.WAXED_OXIDIZED_TAMTAM.get()
                ))
                .rewards(AdvancementRewards.Builder.recipe(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "experience"))
                        .addExperience(300))
                .save(saver, ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "orchestra"), existingFileHelper);
    }
}
