package net.star.copperoverthrow.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class LogStripperBlockEntityRenderer implements BlockEntityRenderer<LogStripperBlockEntity> {
    public LogStripperBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }

    private static final float INITIAL_RENDER_X = 0.5F;
    private static final float INITIAL_RENDER_Y = 0.75F;
    private static final float INITIAL_RENDER_Z = 0.5F;
    
    @Override
    public void render(LogStripperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.getItem(0);

        if (blockEntity.getItem(0).getCount() <= 16) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X, INITIAL_RENDER_Y, INITIAL_RENDER_Z);
            poseStack.scale(1f, 1f, 1f);

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

        poseStack.popPose();
        }
        if (blockEntity.getItem(0).getCount() <= 32 && 16 < blockEntity.getItem(0).getCount()) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X, INITIAL_RENDER_Y, INITIAL_RENDER_Z);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.46, 0.70, 0.56);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();
        }
        if (blockEntity.getItem(0).getCount() <= 48 && 32 < blockEntity.getItem(0).getCount()) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X, INITIAL_RENDER_Y, INITIAL_RENDER_Z);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.46, 0.70, 0.56);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.56, 0.65, 0.46);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();
        }
        if (48 < blockEntity.getItem(0).getCount()) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X, INITIAL_RENDER_Y, INITIAL_RENDER_Z);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.46, 0.70, 0.56);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.56, 0.65, 0.46);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.42, 0.725, 0.44);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.58, 0.675, 0.58);
            poseStack.scale(1f, 1f, 1f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

            poseStack.popPose();
        }

    }

    private int getLightLevel(Level level, BlockPos pos){
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }

    public static float randomPositionGenerator(int MIN, int MAX) {
        int intGenerator = (int) (Math.random() * (MAX - MIN + 1)) + MIN;
        return (float) intGenerator /100;
    }
}
