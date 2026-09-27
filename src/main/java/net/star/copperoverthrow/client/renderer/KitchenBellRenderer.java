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
import net.star.copperoverthrow.block.entity.custom.KitchenBellBlockEntity;
import net.star.copperoverthrow.client.KitchenBellModel;

@OnlyIn(Dist.CLIENT)
public class KitchenBellRenderer implements BlockEntityRenderer<KitchenBellBlockEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/kitchen_bell_hat_entity.png");
    private static final ResourceLocation EXPOSED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/exposed_tamtambodytexture.png");
    private static final ResourceLocation WEATHERED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/weathered_tamtambodytexture.png");
    private static final ResourceLocation OXIDIZED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/oxidized_tamtambodytexture.png");


    private final ModelPart root;

    public KitchenBellRenderer(BlockEntityRendererProvider.Context context) {
        this.root = context.bakeLayer(KitchenBellModel.LAYER_LOCATION);
    }

    public void render(KitchenBellBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.translate(0.5D, 0.115D, 0.5D);
        poseStack.scale(0.8125F, -1.0F, -0.8125F);

        if (blockEntity.swinging) {
            float time = (float) blockEntity.ticks + partialTick;

            BlockPos pos = blockEntity.getBlockPos();
            float xSign = ((pos.getX() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;
            float zSign = ((pos.getY() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;

            float swingAngleX = (Mth.sin(time) / (4.0F + time)) * xSign;
            float swingAngleZ = (Mth.sin((float) (time + (Math.PI / 2))) / (4.0F + time)) * zSign;

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