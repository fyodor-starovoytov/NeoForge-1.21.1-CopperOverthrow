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

    @Override
    public void render(LogStripperBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.getItem(0);
        int count = stack.getCount();

        float baseScale = 0.60f;
        float maxScale = 1.0f;
        float scale = (float) (baseScale + (maxScale - baseScale) * (Math.pow(count, 0.4) / Math.pow(64, 0.4)));

        float anchorX = 0.5f;
        float anchorY = 0.5f;
        float anchorZ = 0.5f;

        poseStack.pushPose();

        poseStack.translate(anchorX, anchorY + (0.25 * scale), anchorZ);
        poseStack.scale(scale, scale, scale);

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos){
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }
}
