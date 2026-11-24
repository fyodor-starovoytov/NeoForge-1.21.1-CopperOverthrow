package net.star.copperoverthrow.event;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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


        if (ServerConfig.GOGGLES_BLINDING.getAsBoolean()) {
            int lightLevel = Math.max(0, helmetStack.getOrDefault(ModDataComponents.LIGHT_LEVEL.get(), 0) - 10);

            float alpha = Mth.clamp(lightLevel / 5.0f, 0.0f, 1.0f);

            int alphaInt = (int) (alpha * 255.0f);
            int color = (alphaInt << 24) | 0xFFFFFF;

            guiGraphics.fill(0, 0, width, height, color);
        }

    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelow(
                VanillaGuiLayers.CAMERA_OVERLAYS,
                ResourceLocation.fromNamespaceAndPath("copperoverthrow", "goggles_overlay"),
                new GogglesOverlayLayeredDraw()
        );
    }
}