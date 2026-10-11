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

    public WindChimeRenderer(BlockEntityRendererProvider.Context context) {
        this.root = context.bakeLayer(RedWindChimeModel.LAYER_LOCATION);
    }

    public void render(RedWindChimeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        poseStack.pushPose();

        poseStack.translate(0.484375D, 1.5D, 0.484375D);
        poseStack.scale(1F, -1.0F, -1F);

        if (blockEntity.swinging) {
            float time = (float) blockEntity.ticks + partialTick;

            BlockPos pos = blockEntity.getBlockPos();
            float xSign = ((pos.getX() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;
            float zSign = ((pos.getY() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;

            float swingAngleX = (Mth.sin(time) / (time)) * xSign;
            float swingAngleZ = (Mth.sin((float) (time + (Math.PI / 2))) / (time)) * zSign;

            this.root.xRot = swingAngleX;
            this.root.zRot = swingAngleZ;
        } else {
            this.root.xRot = 0.0F;
            this.root.zRot = 0.0F;
        }

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.root.render(poseStack, consumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }
}