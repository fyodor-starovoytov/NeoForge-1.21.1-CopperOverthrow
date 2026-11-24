package net.star.copperoverthrow.event;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.star.copperoverthrow.ClientConfig;
import net.star.copperoverthrow.item.ModItems;

@EventBusSubscriber(modid = "copperoverthrow", value = Dist.CLIENT)
public class GogglesOverlay implements LayeredDraw.Layer {

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
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.CAMERA_OVERLAYS,
                ResourceLocation.fromNamespaceAndPath("copperoverthrow", "goggles_overlay"),
                new GogglesOverlay()
        );
    }
}