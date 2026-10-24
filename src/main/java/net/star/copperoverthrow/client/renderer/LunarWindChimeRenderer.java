package net.star.copperoverthrow.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.custom.LunarWindChimeBlockEntity;
import net.star.copperoverthrow.client.LunarWindChimeModel;

public class LunarWindChimeRenderer implements BlockEntityRenderer<LunarWindChimeBlockEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/lunar_wind_chime.png");

    private final ModelPart root;
    private final ModelPart top_coin;
    private final ModelPart cup;
    private final ModelPart tube2;
    private final ModelPart tube6;
    private final ModelPart tube7;
    private final ModelPart tube4;
    private final ModelPart tube5;

    public LunarWindChimeRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart bakedRoot = context.bakeLayer(LunarWindChimeModel.LAYER_LOCATION);
        this.root = bakedRoot.getChild("root");
        this.cup = this.root.getChild("cup");
        this.top_coin = this.root.getChild("top_coin");
        ModelPart tubesPart = this.root.getChild("tubes");
        this.tube2 = tubesPart.getChild("tube0");
        this.tube6 = tubesPart.getChild("tube1");
        this.tube7 = tubesPart.getChild("tube2");
        this.tube4 = tubesPart.getChild("tube3");
        this.tube5 = tubesPart.getChild("tube4");
    }

    @Override
    public void render(LunarWindChimeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        poseStack.pushPose();

        poseStack.translate(0.5D, 1.0D, 0.5D);
        poseStack.scale(1F, -1.0F, -1F);
        float time = (float) blockEntity.getLevel().getGameTime() + partialTick;

        float idleSpeed = 0.05F;
        float idleDegree = 0.05F;
        float idleRotationDegree = (float) Math.toRadians(360);

        BlockPos pos = blockEntity.getBlockPos();
        float posOffset = (Math.abs(pos.hashCode()) % 1000) * 0.628F;

        this.root.yRot = Mth.sin(time * idleSpeed / 40.0F + posOffset) * idleRotationDegree;
        this.root.y = 0.0F + Mth.cos(time * idleSpeed * 2.0F) * 0.05F;

        this.top_coin.yRot = -Mth.cos(time * idleSpeed/100) * idleRotationDegree;
        this.top_coin.y = 0.0F + Mth.cos(time * idleSpeed * 2.0F) * 0.02F;

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
        this.tube2.xRot = -Mth.sin(time * idleSpeed - 0.28f*3) * idleDegree / 3.14f;
        this.tube2.zRot = -Mth.cos(time * idleSpeed + 0.12f*3) * idleDegree / 3.02f;
        this.tube2.yRot = -Mth.sin(time * idleSpeed) * idleDegree * 2.5f;

        this.tube6.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 3.00f;
        this.tube6.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 2.61f ;
        this.tube6.yRot = Mth.cos(time * idleSpeed) * idleDegree * 2.5f;

        this.tube7.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 2.75f;
        this.tube7.zRot = -Mth.cos(time * idleSpeed - 0.22f*3) * idleDegree / 3.12f;
        this.tube7.yRot = Mth.sin(time * idleSpeed) * idleDegree * 2.5f;

        this.tube4.xRot = -Mth.sin(time * idleSpeed + 0.17f*3) * idleDegree / 3.2f;
        this.tube4.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 3.0f;
        this.tube4.yRot = -Mth.cos(time * idleSpeed) * idleDegree * 2.9f;;

        this.tube5.xRot = -Mth.sin(time * idleSpeed + 0.17f*3) * idleDegree / 2.4f;
        this.tube5.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 2.8f;
        this.tube5.yRot = -Mth.cos(time * idleSpeed) * idleDegree * 2.5f;
    }
    private void rotateTubesActive(float time, float zMotion, float xMotion, float rX, float rZ, float rX2, float rZ2, float rX3, float rZ3){
        float swing = (1.5f * Mth.sin(time / (1.5f * (float) Math.PI))) / (6.0f + time / 4.0F);
        float motionMagnitude = (float) Math.sqrt(xMotion * xMotion + zMotion * zMotion);
        float xRotAngle = swing * motionMagnitude;

        this.tube2.zRot = xRotAngle * (0.5f + Math.abs(rX * rZ) * 0.5f);
        this.tube4.zRot = xRotAngle * (0.5f + Math.abs(rX2 * rZ2) * 0.5f);
        this.tube7.zRot = xRotAngle * (0.5f + Math.abs(rX3 * rZ3) * 0.5f);
        this.tube5.zRot = xRotAngle * (0.5f + Math.abs(rZ2 * rX3) * 0.5f);
    }
}
/*
@Override
public void render(LunarWindChimeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

    poseStack.pushPose();

    poseStack.translate(0.5D, 1.0D, 0.5D);
    poseStack.scale(1F, -1.0F, -1F);
    float time = (float) blockEntity.getLevel().getGameTime() + partialTick;

    float idleSpeed = 0.05F;
    float idleDegree = 0.05F;
    float idleRotationDegree = 360F * Mth.DEG_TO_RAD; // Fixed: converted 360 degrees to radians

    // Fixed: Use block position's hash combined with a small multiplier to prevent floating-point precision jitter at high coordinates
    BlockPos pos = blockEntity.getBlockPos();
    float posOffset = (Math.abs(pos.hashCode() % 10000) * 0.000628F);

    this.root.yRot = Mth.sin(time * idleSpeed / 800.0F + posOffset) * idleRotationDegree;
    this.root.y = 0.0F + Mth.cos(time * idleSpeed * 2.0F) * 0.05F;

    this.top_coin.yRot = -Mth.cos(time * idleSpeed / 780.0F) * idleRotationDegree;
    this.top_coin.y = 0.0F + Mth.cos(time * idleSpeed * 2.0F) * 0.02F;

    rotateTubesIdle(time, idleSpeed, idleDegree);

    if (blockEntity.swinging) {
        float swingTime = (float) blockEntity.ticks + partialTick;

        float maxRot = (float) ServerConfig.WIND_CHIME_MAX_ROTATION.get() / 25.0F;
        float xSign = Mth.clamp((float) blockEntity.xMotion, -maxRot, maxRot);
        float zSign = Mth.clamp((float) blockEntity.zMotion, -maxRot, maxRot);

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
    this.tube2.xRot = -Mth.sin(time * idleSpeed - 0.28f * 3) * idleDegree / 3.14f;
    this.tube2.zRot = -Mth.cos(time * idleSpeed + 0.12f * 3) * idleDegree / 3.02f;
    this.tube2.yRot = -Mth.sin(time * idleSpeed) * idleDegree * 2.5f;

    this.tube6.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 3.00f;
    this.tube6.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 2.61f;
    this.tube6.yRot = Mth.cos(time * idleSpeed) * idleDegree * 2.5f;

    this.tube7.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 2.75f;
    this.tube7.zRot = -Mth.cos(time * idleSpeed - 0.22f * 3) * idleDegree / 3.12f;
    this.tube7.yRot = Mth.sin(time * idleSpeed) * idleDegree * 2.5f;

    this.tube4.xRot = -Mth.sin(time * idleSpeed + 0.17f * 3) * idleDegree / 3.2f;
    this.tube4.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 3.0f;
    this.tube4.yRot = -Mth.cos(time * idleSpeed) * idleDegree / 2.9f;

    this.tube5.xRot = -Mth.sin(time * idleSpeed + 0.17f * 3) * idleDegree / 2.4f;
    this.tube5.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 2.8f;
    this.tube5.yRot = -Mth.cos(time * idleSpeed) * idleDegree / 2.5f;
}


 */