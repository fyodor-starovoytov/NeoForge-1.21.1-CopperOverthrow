package net.star.copperoverthrow.event;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.star.copperoverthrow.ClientConfig;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.armor.GlowingGogglesArmorItem;
import org.apache.logging.log4j.core.jmx.Server;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class GogglesOverlayLayeredDraw implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!ClientConfig.GOGGLES_GREEN_OVERLAY.getAsBoolean()){return;}

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack helmetStack = mc.player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmetStack.is(ModItems.COPPER_GLOWING_GOGGLES.get())) {
            return;
        }

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        guiGraphics.fillGradient(0, 0, width, height, 0x4D067d00, 0x3A067d00);

        int lightStrength = GlowingGogglesArmorItem.getLight(mc.player);

        if (helmetStack.has(ModDataComponents.LIGHT_LEVEL) && ServerConfig.GOGGLES_BLINDING.getAsBoolean()){
            if (helmetStack.get(ModDataComponents.LIGHT_LEVEL) == 12) {
                guiGraphics.fill(0, 0, width, height, 0x70FFFFFF);
            }
            if (helmetStack.get(ModDataComponents.LIGHT_LEVEL) == 13) {
                guiGraphics.fill(0, 0, width, height, 0xB3FFFFFF);
            }
            if (helmetStack.get(ModDataComponents.LIGHT_LEVEL) == 14) {
                guiGraphics.fill(0, 0, width, height, 0xCCFFFFFF);
            }
            if (helmetStack.get(ModDataComponents.LIGHT_LEVEL) == 15) {
                guiGraphics.fill(0, 0, width, height, 0xFFFFFFFF);
            }
        }

    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.CAMERA_OVERLAYS,
                ResourceLocation.fromNamespaceAndPath("copperoverthrow", "goggles_overlay"),
                new GogglesOverlayLayeredDraw()
        );
    }
}