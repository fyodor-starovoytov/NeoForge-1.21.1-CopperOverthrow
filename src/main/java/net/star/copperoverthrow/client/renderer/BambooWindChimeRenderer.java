package net.star.copperoverthrow.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.custom.BambooWindChimeBlockEntity;
import net.star.copperoverthrow.client.BambooWindChimeModel;

@OnlyIn(Dist.CLIENT)
public class BambooWindChimeRenderer implements BlockEntityRenderer<BambooWindChimeBlockEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/bamboo_wind_chime.png");

    private final ModelPart root;
    private final ModelPart cup;
    private final ModelPart tube0;
    private final ModelPart tube1;
    private final ModelPart tube2;
    private final ModelPart tube3;
    private final ModelPart tube4;
    private final ModelPart tube6;
    private final ModelPart tube7;
    private final ModelPart tube8;
    public BambooWindChimeRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart bakedRoot = context.bakeLayer(BambooWindChimeModel.LAYER_LOCATION);
        this.root = bakedRoot.getChild("root");
        this.cup = this.root.getChild("cup");
        ModelPart tubesPart = this.root.getChild("tubes");
        this.tube0 = tubesPart.getChild("tube0");
        this.tube1 = tubesPart.getChild("tube1");
        this.tube2 = tubesPart.getChild("tube2");
        this.tube3 = tubesPart.getChild("tube3");
        this.tube4 = tubesPart.getChild("tube4");
        this.tube6 = tubesPart.getChild("tube6");
        this.tube7 = tubesPart.getChild("tube7");
        this.tube8 = tubesPart.getChild("tube8");

    }

    public void render(BambooWindChimeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        poseStack.pushPose();

        poseStack.translate(0.5D, 1.0D, 0.5D);
        poseStack.scale(1F, -1.0F, -1F);
        float time = (float) blockEntity.getLevel().getGameTime() + partialTick;

        float idleSpeed = 0.05F;
        float idleDegree = 0.05F;

        this.root.xRot = Mth.sin(time * idleSpeed) * idleDegree;
        this.root.zRot = Mth.cos(time * idleSpeed) * idleDegree;
        this.root.yRot = Mth.sin(time * idleSpeed) * idleDegree;
        this.root.y = 0.0F + Mth.cos(time * idleSpeed * 2.0F) * 0.1F;

        this.cup.xRot = -Mth.sin(time * idleSpeed/2) * idleDegree/2;
        this.cup.zRot = -Mth.cos(time * idleSpeed/2) * idleDegree/2;
        this.cup.yRot = -Mth.sin(time * idleSpeed/2) * idleDegree/2;
        this.cup.y = 0.0F + Mth.cos(time * idleSpeed * 2.0F) * 0.05F;

        rotateTubesIdle(time, idleSpeed, idleDegree);



        if (blockEntity.swinging) {
            float swingTime = (float) blockEntity.ticks + partialTick;

            float xSign = Math.max(Math.min((float) blockEntity.xMotion, (float) Math.toRadians(ServerConfig.WIND_CHIME_MAX_ROTATION.get())), (float) -Math.toRadians(ServerConfig.WIND_CHIME_MAX_ROTATION.get()));
            float zSign = Math.max(Math.min((float) blockEntity.zMotion, (float) Math.toRadians(ServerConfig.WIND_CHIME_MAX_ROTATION.get())), (float) -Math.toRadians(ServerConfig.WIND_CHIME_MAX_ROTATION.get()));

            rotateTubesActive(swingTime, zSign, xSign,
                    (float) blockEntity.randomX,
                    (float) blockEntity.randomZ,
                    (float) blockEntity.randomZ2,
                    (float) blockEntity.randomX2,
                    (float) blockEntity.randomZ3,
                    (float) blockEntity.randomX3);
        }

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.root.render(poseStack, consumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }

    private void rotateTubesIdle(float time, float idleSpeed, float idleDegree){
        this.tube0.xRot = -Mth.sin(time * idleSpeed + 0.45f * 3) * idleDegree / 1.15f;
        this.tube0.zRot = -Mth.cos(time * idleSpeed - 0.31f * 3) * idleDegree / 1.25f;
        this.tube0.yRot = Mth.sin(time * idleSpeed + 0.2f) * idleDegree * 1.35f;

        this.tube1.xRot = -Mth.sin(time * idleSpeed - 0.15f * 3) * idleDegree / 1.22f;
        this.tube1.zRot = -Mth.cos(time * idleSpeed + 0.38f * 3) * idleDegree / 1.18f;
        this.tube1.yRot = -Mth.cos(time * idleSpeed - 0.1f) * idleDegree * 1.45f;

        this.tube2.xRot = -Mth.sin(time * idleSpeed - 0.28f*3) * idleDegree / 1.24f;
        this.tube2.zRot = -Mth.cos(time * idleSpeed + 0.12f*3) * idleDegree / 1.12f;
        this.tube2.yRot = -Mth.sin(time * idleSpeed) * idleDegree * 1.25f;

        this.tube3.xRot = -Mth.sin(time * idleSpeed + 0.52f * 3) * idleDegree / 1.19f;
        this.tube3.zRot = -Mth.cos(time * idleSpeed - 0.18f * 3) * idleDegree / 1.28f;
        this.tube3.yRot = Mth.sin(time * idleSpeed + 0.4f) * idleDegree * 1.55f;

        this.tube4.xRot = -Mth.sin(time * idleSpeed + 0.17f*3) * idleDegree / 1.17f;
        this.tube4.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 1.22f;
        this.tube4.yRot = -Mth.cos(time * idleSpeed) * idleDegree * 1.6f;

        this.tube6.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 1.14f;
        this.tube6.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 1.21f;
        this.tube6.yRot = Mth.cos(time * idleSpeed) * idleDegree * 1.4f;

        this.tube7.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 1.25f;
        this.tube7.zRot = -Mth.cos(time * idleSpeed - 0.22f*3) * idleDegree / 1.12f;
        this.tube7.yRot = Mth.sin(time * idleSpeed) * idleDegree * 1.8f;

        this.tube8.xRot = -Mth.sin(time * idleSpeed - 0.41f * 3) * idleDegree / 1.28f;
        this.tube8.zRot = -Mth.cos(time * idleSpeed + 0.29f * 3) * idleDegree / 1.15f;
        this.tube8.yRot = -Mth.sin(time * idleSpeed + 0.5f) * idleDegree * 1.7f;
    }
    private void rotateTubesActive(float time, float zMotion, float xMotion, float rX, float rZ, float rX2, float rZ2, float rX3, float rZ3){
        float swing = (1.5f * Mth.sin(time / (1.5f * (float) Math.PI))) / (6.0f + time / 4.0F);
        float xRotAngle = swing * (-zMotion);
        float zRotAngle = swing * (-xMotion);

        this.root.xRot = xRotAngle/2 * rZ;
        this.root.zRot = zRotAngle/2 * rX;

        this.cup.y = zRotAngle/2 * rX;

        float t0 = time + 0.5f;
        float t1 = time + 1.2f;
        float t3 = time + 1.8f;
        float t8 = time + 2.4f;

        float swing0 = (1.5f * Mth.sin(t0 / (1.5f * (float) Math.PI))) / (6.0f + t0 / 4.0F);
        float swing1 = (1.5f * Mth.sin(t1 / (1.5f * (float) Math.PI))) / (6.0f + t1 / 4.0F);
        float swing3 = (1.5f * Mth.sin(t3 / (1.5f * (float) Math.PI))) / (6.0f + t3 / 4.0F);
        float swing8 = (1.5f * Mth.sin(t8 / (1.5f * (float) Math.PI))) / (6.0f + t8 / 4.0F);

        this.tube0.xRot = swing0 * (-zMotion) * rX;
        this.tube0.zRot = swing0 * (-xMotion) * rZ;

        this.tube1.xRot = swing1 * (-zMotion) * rX2;
        this.tube1.zRot = swing1 * (-xMotion) * rZ2;

        this.tube2.xRot = xRotAngle * rX;
        this.tube2.zRot = zRotAngle * rZ;

        this.tube3.xRot = swing3 * (-zMotion) * rX3;
        this.tube3.zRot = swing3 * (-xMotion) * rZ3;

        this.tube4.xRot = xRotAngle * rX2;
        this.tube4.zRot = zRotAngle * rZ2;

        this.tube6.xRot = xRotAngle * (rX2 * rX3);
        this.tube6.zRot = zRotAngle * (rZ2 * rZ3);

        this.tube7.xRot = xRotAngle * rX3;
        this.tube7.zRot = zRotAngle * rZ3;

        this.tube8.xRot = swing8 * (-zMotion) * (rX * rX3);
        this.tube8.zRot = swing8 * (-xMotion) * (rZ * rZ3);
    }
}