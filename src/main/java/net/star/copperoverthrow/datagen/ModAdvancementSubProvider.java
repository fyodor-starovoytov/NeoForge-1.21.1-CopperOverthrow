package net.star.copperoverthrow.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.item.ModItems;

import java.util.function.Consumer;

public class ModAdvancementSubProvider implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement copperIngotAdv = Advancement.Builder.advancement()
                .parent(new ResourceLocation("story/upgrade_tools"))
                .display(
                        Items.COPPER_INGOT,
                        Component.translatable("advancements.copperoverthrow.copper_ingot.title"),
                        Component.translatable("advancements.copperoverthrow.copper_ingot.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_copper_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "copper_ingot"), existingFileHelper);


        Advancement copperScaffolding = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModBlocks.COPPER_SCAFFOLDING.get(),
                        Component.translatable("advancements.copperoverthrow.copper_scaffolding.title"),
                        Component.translatable("advancements.copperoverthrow.copper_scaffolding.description"),
                        null,
                        FrameType.TASK, true,
                        true,
                        false
                )
                .addCriterion("has_scaffolding", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COPPER_SCAFFOLDING.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "copper_scaffolding").toString());

        Advancement builderReady = Advancement.Builder.advancement()
                .parent(copperScaffolding)
                .display(
                        ModItems.COPPER_CROWBAR.get(),
                        Component.translatable("advancements.copperoverthrow.builder_ready.title"),
                        Component.translatable("advancements.copperoverthrow.builder_ready.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_builder", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_TROWEL.get(), ModItems.COPPER_CHISEL.get(), ModItems.COPPER_CROWBAR.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "builder").toString());

        Advancement artist = Advancement.Builder.advancement()
                .parent(copperScaffolding)
                .display(
                        ModItems.COPPER_FILL_BUCKET.get(),
                        Component.translatable("advancements.copperoverthrow.artist.title"),
                        Component.translatable("advancements.copperoverthrow.artist.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_artist", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_FILL_BUCKET.get(), ModItems.COPPER_SEXTANT.get(), ModItems.COPPER_LONG_RULER.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "artist").toString());

        Advancement logStripper = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModBlocks.LOG_STRIPPER.get(),
                        Component.translatable("advancements.copperoverthrow.log_stripper.title"),
                        Component.translatable("advancements.copperoverthrow.log_stripper.description"),
                        null,
                        FrameType.TASK, true,
                        true,
                        false
                )
                .addCriterion("has_stripper", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.LOG_STRIPPER.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "log_stripper").toString());

        Advancement copperHandsaw = Advancement.Builder.advancement()
                .parent(logStripper)
                .display(
                        ModItems.COPPER_HANDSAW.get(),
                        Component.translatable("advancements.copperoverthrow.copper_handsaw.title"),
                        Component.translatable("advancements.copperoverthrow.copper_handsaw.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_handsaw", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_HANDSAW.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "copper_handsaw").toString());

        Advancement stepperSet = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModItems.COPPER_STEPPER_BOOTS.get(),
                        Component.translatable("advancements.copperoverthrow.stepper_set.title"),
                        Component.translatable("advancements.copperoverthrow.stepper_set.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_stepper", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_STEPPER_BOOTS.get(), ModItems.COPPER_STEPPER_LEGGINGS.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "stepper_set").toString());

        Advancement miningSet = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModItems.COPPER_GLOWING_GOGGLES.get(),
                        Component.translatable("advancements.copperoverthrow.mining_set.title"),
                        Component.translatable("advancements.copperoverthrow.mining_set.description"),
                        null,
                        FrameType.TASK, true,
                        true,
                        false
                )
                .addCriterion("has_mining", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_GLOWING_GOGGLES.get(), ModItems.COPPER_EXOSKELETON.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "mining_set").toString());


        Advancement crashHelmet = Advancement.Builder.advancement()
                .parent(copperScaffolding)
                .display(
                        ModItems.CRASH_HELMET.get(),
                        Component.translatable("advancements.copperoverthrow.crash_helmet.title"),
                        Component.translatable("advancements.copperoverthrow.crash_helmet.description"),
                        null,
                        FrameType.TASK, true,
                        true,
                        false
                )
                .addCriterion("has_crash", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRASH_HELMET.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "crash_helmet").toString());


        Advancement copperHammer = Advancement.Builder.advancement()
                .parent(miningSet)
                .display(
                        ModItems.COPPER_HAMMER.get(),
                        Component.translatable("advancements.copperoverthrow.copper_hammer.title"),
                        Component.translatable("advancements.copperoverthrow.copper_hammer.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_hammer", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_HAMMER.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "copper_hammer").toString());

        Advancement musical = Advancement.Builder.advancement()
                .parent(copperIngotAdv)
                .display(
                        ModBlocks.LARGE_RAIN_DRUM.get(),
                        Component.translatable("advancements.copperoverthrow.musical.title"),
                        Component.translatable("advancements.copperoverthrow.musical.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_musical", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ModBlocks.RAIN_DRUM.get(),
                        ModBlocks.TINY_RAIN_DRUM.get(),
                        ModBlocks.LARGE_RAIN_DRUM.get(),
                        ModBlocks.TAMTAM.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "musical").toString());

        Advancement avatar = Advancement.Builder.advancement()
                .parent(musical)
                .display(
                        ModBlocks.BAMBOO_WIND_CHIME.get(),
                        Component.translatable("advancements.copperoverthrow.avatar.title"),
                        Component.translatable("advancements.copperoverthrow.avatar.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("has_avatar", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ModBlocks.BAMBOO_WIND_CHIME.get(),
                        ModBlocks.LUNAR_WIND_CHIME.get(),
                        ModBlocks.RED_WIND_CHIME.get()))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "avatar").toString());

        Advancement orchestra = Advancement.Builder.advancement()
                .parent(musical)
                .display(
                        ModBlocks.WAXED_OXIDIZED_LARGE_RAIN_DRUM.get(),
                        Component.translatable("advancements.copperoverthrow.orchestra.title"),
                        Component.translatable("advancements.copperoverthrow.orchestra.description"),
                        null,
                        FrameType.CHALLENGE,
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
                .rewards(AdvancementRewards.Builder.recipe(new ResourceLocation(CopperOverthrow.MOD_ID, "experience"))
                        .addExperience(300))
                .save(saver, new ResourceLocation(CopperOverthrow.MOD_ID, "orchestra").toString());
    }

}
