package net.star.copperoverthrow.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.armor.client.ArmorClientExtension;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CrashHelmetModel::createBodyLayer, CrashHelmetModel::new)), ModItems.CRASH_HELMET);

        event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_TAPE_MEASURE.get());
        event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_LONG_RULER.get());
    }
}
