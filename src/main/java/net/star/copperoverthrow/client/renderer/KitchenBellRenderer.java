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
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.entity.custom.KitchenBellBlockEntity;
import net.star.copperoverthrow.client.KitchenBellModel;

@OnlyIn(Dist.CLIENT)
public class KitchenBellRenderer implements BlockEntityRenderer<KitchenBellBlockEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/kitchen_bell_hat_entity.png");
    private static final ResourceLocation EXPOSED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/exposed_kitchen_bell_hat_entity.png");
    private static final ResourceLocation WEATHERED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/weathered_kitchen_bell_hat_entity.png");
    private static final ResourceLocation OXIDIZED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/oxidized_kitchen_bell_hat_entity.png");


    private final ModelPart root;
    private int ShakeWeakness = 8;


    public KitchenBellRenderer(BlockEntityRendererProvider.Context context) {
        this.root = context.bakeLayer(KitchenBellModel.LAYER_LOCATION);
    }

    public void render(KitchenBellBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (isBlock(blockEntity, ModBlocks.EXPOSED_KITCHEN_BELL.get(), ModBlocks.WAXED_EXPOSED_KITCHEN_BELL.get())) {
            ShakeWeakness = 15;
        } else if (isBlock(blockEntity, ModBlocks.WEATHERED_KITCHEN_BELL.get(), ModBlocks.WAXED_WEATHERED_KITCHEN_BELL.get())) {
            ShakeWeakness = 28;
        } else if (isBlock(blockEntity, ModBlocks.OXIDIZED_KITCHEN_BELL.get(), ModBlocks.WAXED_OXIDIZED_KITCHEN_BELL.get())) {
            ShakeWeakness = 50;
        } else {
            ShakeWeakness = 7;
        }

        poseStack.pushPose();

        poseStack.translate(0.484375D, 0.4275D, 0.484375D);
        poseStack.scale(1F, -1.0F, -1F);

        if (blockEntity.swinging) {
            float time = (float) blockEntity.ticks + partialTick;

            BlockPos pos = blockEntity.getBlockPos();
            float xSign = ((pos.getX() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;
            float zSign = ((pos.getY() ^ pos.getZ()) % 2 == 0) ? 1.0F : -1.0F;

            float swingAngleX = (Mth.sin(time) / (ShakeWeakness + time)) * xSign;
            float swingAngleZ = (Mth.sin((float) (time + (Math.PI / 2))) / (ShakeWeakness + time)) * zSign;

            this.root.xRot = swingAngleX;
            this.root.zRot = swingAngleZ;
        } else {
            this.root.xRot = 0.0F;
            this.root.zRot = 0.0F;
        }

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureForBell(blockEntity)));
        this.root.render(poseStack, consumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }

    private ResourceLocation getTextureForBell(KitchenBellBlockEntity blockEntity) {
        if (isBlock(blockEntity, ModBlocks.EXPOSED_KITCHEN_BELL.get(), ModBlocks.WAXED_EXPOSED_KITCHEN_BELL.get())) {
            return EXPOSED_TEXTURE;
        }
        if (isBlock(blockEntity, ModBlocks.WEATHERED_KITCHEN_BELL.get(), ModBlocks.WAXED_WEATHERED_KITCHEN_BELL.get())) {
            return WEATHERED_TEXTURE;
        }
        if (isBlock(blockEntity, ModBlocks.OXIDIZED_KITCHEN_BELL.get(), ModBlocks.WAXED_OXIDIZED_KITCHEN_BELL.get())) {
            return OXIDIZED_TEXTURE;
        }
        return TEXTURE;
    }

    private boolean isBlock(KitchenBellBlockEntity blockEntity, Block unwaxed, Block waxed) {
        Block block = blockEntity.getBlockState().getBlock();
        return block == unwaxed || block == waxed;
    }
}