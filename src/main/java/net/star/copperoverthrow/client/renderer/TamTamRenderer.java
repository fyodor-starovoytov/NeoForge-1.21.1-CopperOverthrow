package net.star.copperoverthrow.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.custom.TamTamBlock;
import net.star.copperoverthrow.block.entity.custom.TamTamBlockEntity;
import net.star.copperoverthrow.client.TamTamModel;

@OnlyIn(Dist.CLIENT)
public class TamTamRenderer implements BlockEntityRenderer<TamTamBlockEntity> {
    public static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/tamtambodytexture.png");

    private final ModelPart root;
    private final ModelPart tamtamBody;

    public TamTamRenderer(BlockEntityRendererProvider.Context context) {
        this.root = context.bakeLayer(TamTamModel.LAYER_LOCATION);
        this.tamtamBody = this.root.getChild("root").getChild("tamtam_body");
    }

    @Override
    public void render(TamTamBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Boolean isUpper = blockEntity.getBlockState().getValue(TamTamBlock.PART) == TamTamBlock.WideThinDoubleBlock.MAIN_TOP;

        if (isUpper) {
            poseStack.pushPose();

            if (blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST) {
                poseStack.translate(0.5D, 0.5D, 1.0D);
                poseStack.scale(1.0F, -1.0F, -1.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
            if (blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING) == Direction.WEST) {
                poseStack.translate(0.5D, 0.5D, 0.0D);
                poseStack.scale(1.0F, -1.0F, -1.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
            if (blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING) == Direction.NORTH) {
                poseStack.translate(1.0D, 0.5D, 0.5D);
                poseStack.scale(1.0F, -1.0F, -1.0F);
            }
            if (blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING) == Direction.SOUTH) {
                poseStack.translate(0.0D, 0.5D, 0.5D);
                poseStack.scale(1.0F, -1.0F, -1.0F);
            }

            if (blockEntity.swinging) {
                float time = (float) blockEntity.ticks + partialTick;
                float swingAngle = (3 * Mth.sin(time / (1.5f * (float) Math.PI))) / (7 + time / 4.0F);

                if (blockEntity.clickDirection == Direction.NORTH) {
                    this.tamtamBody.xRot = -swingAngle;
                    this.tamtamBody.zRot = 0.0F;
                } else if (blockEntity.clickDirection == Direction.SOUTH) {
                    this.tamtamBody.xRot = swingAngle;
                    this.tamtamBody.zRot = 0.0F;
                } else if (blockEntity.clickDirection == Direction.EAST) {
                    this.tamtamBody.zRot = 0.0F;
                    this.tamtamBody.xRot = -swingAngle;
                } else if (blockEntity.clickDirection == Direction.WEST) {
                    this.tamtamBody.zRot = 0.0F;
                    this.tamtamBody.xRot = swingAngle;
                }
            } else {
                this.tamtamBody.xRot = 0.0F;
                this.tamtamBody.zRot = 0.0F;
            }

            // Render root (which renders tamtamBody as a child with all correct parent offsets applied)
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            this.root.render(poseStack, consumer, packedLight, packedOverlay, -1);

            poseStack.popPose();
        }
    }
}