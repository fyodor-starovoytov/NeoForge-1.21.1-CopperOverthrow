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
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.entity.custom.RedWindChimeBlockEntity;
import net.star.copperoverthrow.client.RedWindChimeModel;

@OnlyIn(Dist.CLIENT)
public class WindChimeRenderer implements BlockEntityRenderer<RedWindChimeBlockEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/red_wind_chime.png");

    private final ModelPart root;
    private final ModelPart cup;
    private final ModelPart tube2;
    private final ModelPart tube6;
    private final ModelPart tube7;
    private final ModelPart tube4;
    public WindChimeRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart bakedRoot = context.bakeLayer(RedWindChimeModel.LAYER_LOCATION);
        this.root = bakedRoot.getChild("root");
        this.cup = this.root.getChild("cup");
        ModelPart tubesPart = this.root.getChild("tubes");
        this.tube2 = tubesPart.getChild("tube2");
        this.tube6 = tubesPart.getChild("tube6");
        this.tube7 = tubesPart.getChild("tube7");
        this.tube4 = tubesPart.getChild("tube4");

    }

    public void render(RedWindChimeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

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

            System.out.println("Swing Rendered");
            System.out.println(blockEntity.xMotion);

            float xSign = ((float) blockEntity.xMotion);
            float zSign = ((float) blockEntity.zMotion);

            rotateTubesActive(swingTime, zSign, xSign);

        } else {
            cancelTubeRotation();
        }

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.root.render(poseStack, consumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }

    private void rotateTubesIdle(float time, float idleSpeed, float idleDegree){
        this.tube2.xRot = -Mth.sin(time * idleSpeed - 0.28f*3) * idleDegree / 1.24f;
        this.tube2.zRot = -Mth.cos(time * idleSpeed + 0.12f*3) * idleDegree / 1.12f;
        this.tube2.yRot = -Mth.sin(time * idleSpeed) * idleDegree * 1.25f ;

        this.tube6.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 1.14f;
        this.tube6.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 1.21f ;
        this.tube6.yRot = Mth.cos(time * idleSpeed) * idleDegree * 1.4f;

        this.tube7.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 1.25f;
        this.tube7.zRot = -Mth.cos(time * idleSpeed - 0.22f*3) * idleDegree / 1.12f;
        this.tube7.yRot = Mth.sin(time * idleSpeed) * idleDegree* 1.8f;

        this.tube4.xRot = -Mth.sin(time * idleSpeed + 0.17f*3) * idleDegree / 1.17f;
        this.tube4.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 1.22f;
        this.tube4.yRot = -Mth.cos(time * idleSpeed) * idleDegree * 1.6f;
    }
    private void rotateTubesActive(float time, float zMotion, float xMotion){
        float swing = (Mth.sin(time - (float) Math.PI) / ((time - (float) Math.PI)*8));
        float xRotAngle = swing * (xMotion);
        float zRotAngle = swing * (zMotion);

        this.tube2.yRot = 0;
        this.tube2.xRot = xRotAngle;
        this.tube2.zRot = zRotAngle;

        this.tube4.yRot = 0;
        this.tube4.xRot = xRotAngle;
        this.tube4.zRot = zRotAngle;

        this.tube7.yRot = 0;
        this.tube7.xRot = xRotAngle;
        this.tube7.zRot = zRotAngle;

        this.tube6.yRot = 0;
        this.tube6.xRot = xRotAngle;
        this.tube6.zRot = zRotAngle;
    }

    private void cancelTubeRotation(){
        this.tube6.xRot = 0.0F;
        this.tube6.zRot = 0.0F;
        this.tube6.yRot = 0.0F;

        this.tube2.xRot = 0.0F;
        this.tube2.zRot = 0.0F;
        this.tube2.yRot = 0.0F;

        this.tube7.xRot = 0.0F;
        this.tube7.zRot = 0.0F;
        this.tube7.yRot = 0.0F;

        this.tube4.xRot = 0.0F;
        this.tube4.zRot = 0.0F;
        this.tube4.yRot = 0.0F;


    }
}