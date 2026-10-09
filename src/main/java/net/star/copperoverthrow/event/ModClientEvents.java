package net.star.copperoverthrow.event;

import net.minecraft.world.item.ArmorItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.armor.AbstractExtendedArmorItem;
import net.star.copperoverthrow.item.armor.client.ArmorClientExtension;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.provider.ArmorModelProvider;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

import java.util.Map;

public class ModClientEvents {

    @EventBusSubscriber(modid = CopperOverthrow.MOD_ID)
    public class ModClientEventbusEvents {

        @SubscribeEvent
        public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CrashHelmetModel::createBodyLayer, CrashHelmetModel::new)), ModItems.CRASH_HELMET);
        }

        @SuppressWarnings("unchecked")
        private static <T extends AbstractExtendedArmorItem> void registerArmorExtension(Map<ArmorItem.Type, DeferredItem<T>> map, RegisterClientExtensionsEvent event, ArmorModelProvider provider) {
            event.registerItem(new ArmorClientExtension(provider), map.values().toArray(DeferredItem[]::new));
        }
    }

}
