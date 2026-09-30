package net.star.copperoverthrow;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.renderer.LogStripperBlockEntityRenderer;
import net.star.copperoverthrow.client.KitchenBellModel;
import net.star.copperoverthrow.client.TamTamModel;
import net.star.copperoverthrow.client.renderer.KitchenBellRenderer;
import net.star.copperoverthrow.client.renderer.TamTamRenderer;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod(value = CopperOverthrow.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class CopperOverthrowClient {

    public CopperOverthrowClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

        ModItemProperties.addCustomItemProperties();
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.LOG_STRIPPER_BE.get(), LogStripperBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SWINGING_BE.get(), TamTamRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.KITCHEN_BELL_BE.get(), KitchenBellRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TamTamModel.LAYER_LOCATION, TamTamModel::createBodyLayer);
        event.registerLayerDefinition(KitchenBellModel.LAYER_LOCATION, KitchenBellModel::createBodyLayer);
    }

            private static final ResourceLocation OVERLAY_TEXTURE =
                    ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/gui/waxed_overlay.png");

    @SubscribeEvent
    public static void registerItemDecorators(RegisterItemDecorationsEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            event.register(item, (guiGraphics, font, stack, x, y) -> {
                if (isWaxedByName(stack)) {
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();

                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0, 0, 200.0F);

                    guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, 16, 16, 16, 16);

                    guiGraphics.pose().popPose();
                    RenderSystem.disableBlend();
                }

                return false;
            });
        }
    }

    public static boolean isWaxedByName(ItemStack stack) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String path = id.getPath();

        return path.startsWith("waxed_") || path.contains("_waxed_") || path.contains("_waxed");
    }
}