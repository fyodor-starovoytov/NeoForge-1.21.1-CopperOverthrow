package net.star.copperoverthrow;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.renderer.LogStripperBlockEntityRenderer;
import net.star.copperoverthrow.client.KitchenBellModel;
import net.star.copperoverthrow.client.TamTamModel;
import net.star.copperoverthrow.client.renderer.KitchenBellRenderer;
import net.star.copperoverthrow.client.renderer.TamTamRenderer;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModItemProperties;
import net.minecraft.resources.ResourceLocation;

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

            private static final ResourceLocation OVERLAY_TEXTURE =
                    ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/gui/waxed_overlay.png");

    @SubscribeEvent
    public static void registerItemDecorators(RegisterItemDecorationsEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            event.register(item, (guiGraphics, font, stack, x, y) -> {
                if (isWaxedByName(stack)) {
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();

                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0, 0, 200.0F);

                    guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, 16, 16, 16, 16);

                    guiGraphics.pose().popPose();
                    RenderSystem.disableBlend();
                }

                return false;
            });
        }
    }

    public static boolean isWaxedByName(ItemStack stack) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String path = id.getPath();

        return path.startsWith("waxed_") || path.contains("_waxed_") || path.contains("_waxed");
    }

    /*@SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.isUsingItem()) return;

        ItemStack stack = event.getItemStack();

        if (stack.is(ModItems.COPPER_HANDSAW.get())) {

            //HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(player, p_281111_ -> !p_281111_.isSpectator() && p_281111_.isPickable(), player.blockInteractionRange();
            // 1. Calculate smooth elapsed time in float ticks (includes frame interpolation)
            float remainingTicks = player.getUseItemRemainingTicks() - event.getPartialTick();
            float maxTicks = stack.getUseDuration(player);
            float useTicks = maxTicks - remainingTicks;

            // 2. Determine hand side (Mainhand vs Offhand, Left vs Right)
            boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
            boolean isRightArm = (isMainHand && player.getMainArm() == HumanoidArm.RIGHT) ||
                    (!isMainHand && player.getMainArm() == HumanoidArm.LEFT);
            float sideTranslate = isRightArm ? 0.5F : -0.5F;
            float sideSign = isRightArm ? 1F : -1F;

            PoseStack poseStack = event.getPoseStack();

            float pushPull = -1.7f + Mth.sin(useTicks * 0.6F) * 2F;

            float tilt = Mth.cos((float) ((Math.PI/2) + useTicks * 0.6F)) * 10F;

            float centerX = sideTranslate;
            float centerY = -0.50F;
            float centerZ = -0.7F + pushPull;

            poseStack.translate(centerX, centerY, centerZ);

            poseStack.mulPose(Axis.ZP.rotationDegrees(-sideSign*5));
            poseStack.mulPose(Axis.XP.rotationDegrees(tilt));
            poseStack.mulPose(Axis.YP.rotationDegrees(tilt * sideSign * 1.2f));
        }
    }*/

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.isUsingItem()) return;

        ItemStack useStack = player.getUseItem();
        if (!useStack.is(ModItems.COPPER_HANDSAW.get())) return;

        // Hide off-hand pass so saw only renders once
        if (event.getHand() != player.getUsedItemHand()) {
            event.setCanceled(true);
            return;
        }

        float partialTick = event.getPartialTick();

        // 1. Raycast up to 5 blocks away to find target block
        HitResult hitResult = player.pick(5.0D, partialTick, false);
        if (!(hitResult instanceof BlockHitResult blockHit) || hitResult.getType() != HitResult.Type.BLOCK) {
            return; // Fallback to normal hand rendering if not looking at a block
        }

        PoseStack poseStack = event.getPoseStack();

        // 2. Reset matrix to Camera Space
        poseStack.last().pose().identity();
        poseStack.last().normal().identity();

        // 3. Rotate matrix from Camera View into World-Aligned Directions (North/South/East/West/Up/Down)
        float xRot = player.getViewXRot(partialTick);
        float yRot = player.getViewYRot(partialTick);

        poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot + 180.0F));

        // 4. Translate directly to the 3D hit point relative to player eye position
        Vec3 eyePos = player.getEyePosition(partialTick);
        Vec3 hitPos = blockHit.getLocation();
        Vec3 offset = hitPos.subtract(eyePos);

        poseStack.translate(offset.x, offset.y, offset.z);

        float remainingTicks = player.getUseItemRemainingTicks() - partialTick;
        float useTicks = useStack.getUseDuration(player) - remainingTicks;
        float pushPull = Mth.sin(useTicks * 1.2F) * 0.24F; // Stroke length
        float subtleTilt = Mth.cos(useTicks * 1.2F) * 2.5F;

        // 6. Align saw orientation based on the targeted block face
        Direction face = blockHit.getDirection();
        Direction playerFace = player.getDirection();

        switch (face) {
            case NORTH -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                poseStack.translate(0, -0.05F, pushPull);
            }
            case SOUTH -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-45F));
                poseStack.translate(0, 0.05F, -pushPull);
            }
            case WEST -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F*3));
                poseStack.translate(0, -0.05F,pushPull);
            }
            case EAST -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F*3));
                poseStack.translate(0, -0.05F,pushPull);
            }
            case UP -> {
                switch (playerFace){
                    case NORTH -> {
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                    case SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                    case WEST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                    case EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                }

            }
            case DOWN -> {
                switch (playerFace){
                    case NORTH -> {
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                    case SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F + pushPull*0.05,pushPull);
                    }
                    case WEST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                    case EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F,pushPull);
                    }
                }

            }
        }

        // Apply upright tool orientation and sawing tilt
        poseStack.mulPose(Axis.XP.rotationDegrees(subtleTilt));
    }

    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        if (player.isUsingItem() && player.getUseItem().is(ModItems.COPPER_HANDSAW.get())) {
            event.setCanceled(true);
        }
    }
}