package net.star.copperoverthrow.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.entity.ModEntities;
import net.star.copperoverthrow.entity.client.FireflyModel;
import net.star.copperoverthrow.entity.client.FireflyRenderer;
import net.star.copperoverthrow.entity.client.ModModelLayers;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.armor.client.ArmorClientExtension;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CrashHelmetModel::createBodyLayer, CrashHelmetModel::new)), ModItems.CRASH_HELMET);

        event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_SEXTANT.get());
        event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_LONG_RULER.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.FIREFLY.get(), FireflyRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.FIREFLY, FireflyModel::createBodyLayer);
    }
}
