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

        rotateTubes(time, idleSpeed, idleDegree);

        if (blockEntity.swinging) {
            float swingTime = (float) blockEntity.ticks + partialTick;
            BlockPos pos = blockEntity.getBlockPos();
            float xSign = ((pos.getX() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;
            float zSign = ((pos.getY() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;

            this.root.xRot += (Mth.sin(swingTime) / swingTime) * xSign;
            this.root.zRot += (Mth.sin((float) (swingTime + (Math.PI / 2))) / swingTime) * zSign;
        } else {
            this.root.xRot = 0.0F;
        }

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.root.render(poseStack, consumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }

    private void rotateTubes(float time, float idleSpeed, float idleDegree){
        this.tube2.xRot = -Mth.sin(time * idleSpeed - 0.28f) * idleDegree / 1.24f;
        this.tube2.zRot = -Mth.cos(time * idleSpeed + 0.12f) * idleDegree / 1.12f;

        this.tube6.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 1.14f;
        this.tube6.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 1.21f ;

        this.tube7.xRot = -Mth.sin(time * idleSpeed) * idleDegree / 1.25f;
        this.tube7.zRot = -Mth.cos(time * idleSpeed - 0.22f) * idleDegree / 1.12f;

        this.tube4.xRot = -Mth.sin(time * idleSpeed + 0.17f) * idleDegree / 1.17f;
        this.tube4.zRot = -Mth.cos(time * idleSpeed) * idleDegree / 1.22f;
    }
}