package net.star.copperoverthrow;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
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

import java.util.ArrayList;
import java.util.List;

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

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.isUsingItem()) return;

        ItemStack stack = event.getItemStack();

        if (stack.is(ModItems.COPPER_HANDSAW.get())) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.scale(0,0,0);
/*
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
        */
        }

    }
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null || !player.isUsingItem()) return;

        ItemStack useStack = player.getUseItem();
        if (!useStack.is(ModItems.COPPER_HANDSAW.get())) return;

        // 1. Get safe 1.21.1 partial ticks from DeltaTracker
        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);

        HitResult hitResult = player.pick(5.0D, partialTick, false);
        if (!(hitResult instanceof BlockHitResult blockHit) || hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();

        // 2. Subtract Camera Position for precise World-Space Rendering
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 hitPos = blockHit.getLocation();
        Vec3 offset = hitPos.subtract(cameraPos);

        // Position directly at the target block contact point
        poseStack.translate(offset.x, offset.y, offset.z);

        // 3. Apply Sawing Motion
        float remainingTicks = player.getUseItemRemainingTicks() - partialTick;
        float useTicks = useStack.getUseDuration(player) - remainingTicks;
        float pushPull = Mth.sin(useTicks * 1.2F) * 0.24F;
        float subtleTilt = Mth.cos(useTicks * 1.2F) * 2.5F;

        Direction face = blockHit.getDirection();
        Direction playerFace = player.getDirection();

        // Apply face-alignment rotations
        applyFaceTransforms(poseStack, face, playerFace, pushPull);

        poseStack.mulPose(Axis.XP.rotationDegrees(subtleTilt));

        BlockPos targetPos = blockHit.getBlockPos();

// Get brightness levels using player.level()
        List<Integer> lightLevel = getLightLevel(player, face, targetPos);

        int packedLight = LightTexture.pack(lightLevel.get(0), lightLevel.get(1));

        mc.getItemRenderer().renderStatic(
                useStack,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                packedLight, // Target block ambient brightness
                OverlayTexture.NO_OVERLAY,
                poseStack,
                mc.renderBuffers().bufferSource(),
                player.level(),
                0
        );

        poseStack.popPose();
    }

    private static void applyFaceTransforms(PoseStack poseStack, Direction face, Direction playerFace, float pushPull) {
        switch (face) {
            case NORTH -> {
                switch (playerFace) {
                    case WEST, NORTH, SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.translate(0, pushPull,0.162);
                    }
                    case EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.translate(0,  pushPull, 0.162);
                    }
                }
            }
            case SOUTH -> {
                switch (playerFace) {
                    case EAST, NORTH, SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.translate(0, pushPull,0.162);
                    }
                    case WEST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.translate(0,  pushPull, 0.162);
                    }
/*
                    case  -> {
                        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                        poseStack.translate(0,  pushPull, 0.165);
                    }
*/
                }
            }
            case WEST -> {
                switch (playerFace) {
                    case SOUTH, WEST, EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F * 3));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case NORTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                }
            }
            case EAST -> {
                switch (playerFace) {
                    case NORTH, WEST, EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F * 3));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                }
            }
            case UP -> {
                switch (playerFace) {
                    case NORTH -> {
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case WEST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                }
            }
            case DOWN -> {
                switch (playerFace) {
                    case NORTH -> {
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case SOUTH -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F + pushPull * 0.05F, pushPull);
                    }
                    case WEST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                    case EAST -> {
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
                        poseStack.translate(0, -0.05F, pushPull);
                    }
                }
            }
        }
    }


    private static List<Integer> getLightLevel(LocalPlayer player, Direction face, BlockPos targetPos) {
        List<Integer> light = new ArrayList<>();
        switch (face) {
            case NORTH -> {
                int blockLight = player.level().getBrightness(LightLayer.BLOCK, targetPos.north());
                int skyLight = player.level().getBrightness(LightLayer.SKY, targetPos.north());
                light.add(0, blockLight);
                light.add(1, skyLight);
            }
            case SOUTH -> {
                int blockLight = player.level().getBrightness(LightLayer.BLOCK, targetPos.south());
                int skyLight = player.level().getBrightness(LightLayer.SKY, targetPos.south());
                light.add(0, blockLight);
                light.add(1, skyLight);
            }
            case WEST -> {
                int blockLight = player.level().getBrightness(LightLayer.BLOCK, targetPos.west());
                int skyLight = player.level().getBrightness(LightLayer.SKY, targetPos.west());
                light.add(0, blockLight);
                light.add(1, skyLight);
            }
            case EAST -> {
                int blockLight = player.level().getBrightness(LightLayer.BLOCK, targetPos.east());
                int skyLight = player.level().getBrightness(LightLayer.SKY, targetPos.east());
                light.add(0, blockLight);
                light.add(1, skyLight);
            }
            case UP -> {
                int blockLight = player.level().getBrightness(LightLayer.BLOCK, targetPos.above());
                int skyLight = player.level().getBrightness(LightLayer.SKY, targetPos.above());
                light.add(0, blockLight);
                light.add(1, skyLight);
            }
            case DOWN -> {
                int blockLight = player.level().getBrightness(LightLayer.BLOCK, targetPos.below());
                int skyLight = player.level().getBrightness(LightLayer.SKY, targetPos.below());
                light.add(0, blockLight);
                light.add(1, skyLight);
            }
        }
        return light;
    }
}