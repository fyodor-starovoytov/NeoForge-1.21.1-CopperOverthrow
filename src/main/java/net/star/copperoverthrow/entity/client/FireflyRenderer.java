package net.star.copperoverthrow.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.entity.custom.FireflyEntity;

import java.util.Map;

public class FireflyRenderer extends MobRenderer<FireflyEntity, FireflyModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/entity/firefly/firefly.png");

    public FireflyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireflyModel(pContext.bakeLayer(ModModelLayers.FIREFLY)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(FireflyEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(FireflyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
