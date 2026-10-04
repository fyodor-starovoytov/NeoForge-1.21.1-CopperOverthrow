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

public class LogStripperBlockEntityRenderer implements BlockEntityRenderer<LogStripperBlockEntity> {
    public LogStripperBlockEntityRenderer(BlockEntityRendererProvider.Context context){
    }

    private static final float INITIAL_RENDER_X = 0.5F;
    private static final float INITIAL_RENDER_Y = 0.65F;
    private static final float INITIAL_RENDER_Z = 0.5F;
    private static final float UP_SHIFT = 0.30F;
    private static final float SCALING = 0.60F;
    
    @Override
    public void render(LogStripperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.getItem(0);

        if (blockEntity.getItem(0).getCount() <= 16) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X, INITIAL_RENDER_Y, INITIAL_RENDER_Z);
            poseStack.scale(SCALING, SCALING, SCALING);

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

        poseStack.popPose();
        }



        if (blockEntity.getItem(0).getCount() <= 32 && 16 < blockEntity.getItem(0).getCount()) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X+0.205, INITIAL_RENDER_Y, INITIAL_RENDER_Z-0.04);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X-0.205, INITIAL_RENDER_Y, INITIAL_RENDER_Z+0.06);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();
        }




        if (blockEntity.getItem(0).getCount() <= 48 && 32 < blockEntity.getItem(0).getCount()) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X+0.190, INITIAL_RENDER_Y, INITIAL_RENDER_Z-0.02);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X-0.190, INITIAL_RENDER_Y, INITIAL_RENDER_Z+0.16);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X-0.170, INITIAL_RENDER_Y, INITIAL_RENDER_Z-0.19);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();
        }




        if (48 < blockEntity.getItem(0).getCount()) {
            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X+0.195, INITIAL_RENDER_Y, INITIAL_RENDER_Z-0.17);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X-0.190, INITIAL_RENDER_Y, INITIAL_RENDER_Z+0.16);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X-0.170, INITIAL_RENDER_Y, INITIAL_RENDER_Z-0.19);
            poseStack.scale(SCALING, SCALING, SCALING);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                    blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(INITIAL_RENDER_X+0.185, INITIAL_RENDER_Y, INITIAL_RENDER_Z+0.18);
            poseStack.scale(SCALING, SCALING, SCALING);

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
