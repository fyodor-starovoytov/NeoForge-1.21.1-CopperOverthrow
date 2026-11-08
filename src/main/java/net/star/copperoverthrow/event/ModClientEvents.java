package net.star.copperoverthrow.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.armor.client.model.CopperExoskeletonModel;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.model.GlowingGogglesModel;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

@Mod.EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

        @SubscribeEvent
        public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CrashHelmetModel::createBodyLayer, CrashHelmetModel::new)), ModItems.CRASH_HELMET);
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(GlowingGogglesModel::createBodyLayer, GlowingGogglesModel::new)), ModItems.COPPER_GLOWING_GOGGLES);
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CopperExoskeletonModel::createBodyLayer, CopperExoskeletonModel::new)), ModItems.COPPER_EXOSKELETON);

            event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_SEXTANT.get());
            event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_LONG_RULER.get());
        }
}
