package net.star.copperoverthrow.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.star.copperoverthrow.ClientConfig;
import net.star.copperoverthrow.CommonConfig;
import net.star.copperoverthrow.item.ModItems;

@Mod.EventBusSubscriber(modid = "copperoverthrow", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GogglesOverlayLayeredDraw implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!ClientConfig.GOGGLES_GREEN_OVERLAY.get()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack helmetStack = mc.player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmetStack.is(ModItems.COPPER_GLOWING_GOGGLES.get())) {
            return;
        }

        guiGraphics.fillGradient(0, 0, screenWidth, screenHeight, 0x4D067d00, 0x3A067d00);

        if (CommonConfig.GOGGLES_BLINDING.get()) {
            int rawLightLevel = 0;
            CompoundTag tag = helmetStack.getTag();
            if (tag != null && tag.contains("light_level")) {
                rawLightLevel = tag.getInt("light_level");
            }

            int lightLevel = Math.max(0, rawLightLevel - 10);
            float alpha = Mth.clamp(lightLevel / 5.0f, 0.0f, 1.0f);

            int alphaInt = (int) (alpha * 255.0f);
            int color = (alphaInt << 24) | 0xFFFFFF;

            guiGraphics.fill(0, 0, screenWidth, screenHeight, color);
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelow(
                VanillaGuiOverlay.VIGNETTE.id(),
                "goggles_overlay",
                new GogglesOverlayLayeredDraw()
        );
    }
}