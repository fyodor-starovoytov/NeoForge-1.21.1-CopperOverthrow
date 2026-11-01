package net.star.copperoverthrow.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.star.copperoverthrow.CopperOverthrow;

@OnlyIn(Dist.CLIENT)
public class FireflyLightLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M>  {

    private final RenderType TEXTURE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID,"textures/entity/firefly/firefly_emissive.png"));

    public FireflyLightLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
                       float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.level().isDay()) {
            super.render(poseStack, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public RenderType renderType() {
        return TEXTURE;
    }
}
