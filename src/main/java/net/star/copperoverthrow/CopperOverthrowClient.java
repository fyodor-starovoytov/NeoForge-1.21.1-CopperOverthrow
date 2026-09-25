package net.star.copperoverthrow;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.renderer.LogStripperBlockEntityRenderer;
import net.star.copperoverthrow.client.KitchenBellModel;
import net.star.copperoverthrow.client.TamTamModel;
import net.star.copperoverthrow.client.renderer.KitchenBellRenderer;
import net.star.copperoverthrow.client.renderer.TamTamRenderer;
import net.star.copperoverthrow.util.ModItemProperties;

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
}