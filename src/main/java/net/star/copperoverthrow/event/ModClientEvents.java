package net.star.copperoverthrow.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.armor.client.ArmorClientExtension;
import net.star.copperoverthrow.item.armor.client.model.CopperExoskeletonModel;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.model.GlowingGogglesModel;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

        @SubscribeEvent
        public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CrashHelmetModel::createBodyLayer, CrashHelmetModel::new)), ModItems.CRASH_HELMET);
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(GlowingGogglesModel::createBodyLayer, GlowingGogglesModel::new)), ModItems.COPPER_GLOWING_GOGGLES);
            event.registerItem(new ArmorClientExtension(new SimpleModelProvider(CopperExoskeletonModel::createBodyLayer, CopperExoskeletonModel::new)), ModItems.COPPER_EXOSKELETON);

            event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_SEXTANT.get());
            event.registerItem(CustomSpyglassArmposeExtension.INSTANCE, ModItems.COPPER_LONG_RULER.get());
        }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (event.getHand() != InteractionHand.OFF_HAND) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        if (player.swinging && player.swingingArm == InteractionHand.MAIN_HAND) {
            ItemStack mainHandStack = player.getMainHandItem();
            ItemStack offHandStack = event.getItemStack();

            if (shouldDualSwing(mainHandStack, offHandStack)) {
                float swingProgress = player.getAttackAnim(event.getPartialTick());

                HumanoidArm offHandArm = player.getMainArm().getOpposite();

                applyVanillaSwingTransform(event.getPoseStack(), offHandArm, swingProgress);
            }
        }
    }

    private static boolean shouldDualSwing(ItemStack mainHand, ItemStack offHand) {
        return mainHand.is(ModItems.COPPER_DOUBLE_PICKAXES.get()) && offHand.is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get());
    }

    private static void applyVanillaSwingTransform(PoseStack poseStack, HumanoidArm arm, float swingProgress) {
        int side = (arm == HumanoidArm.RIGHT) ? 1 : -1;

        float f = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) side * (45.0F + f * -20.0F)));

        float f1 = Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) side * f1 * -10));
        poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -60.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees((float) side * 10.0F));
    }
}
