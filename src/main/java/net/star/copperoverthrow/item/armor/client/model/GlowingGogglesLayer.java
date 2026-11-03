package net.star.copperoverthrow.item.armor.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.star.copperoverthrow.CopperOverthrow;

@OnlyIn(Dist.CLIENT)
public class GlowingGogglesLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private static final RenderType EMISSIVE_TEXTURE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID,"textures/models/armor/glowing_goggles_emissive.png"));

    public GlowingGogglesLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    @Override
    public RenderType renderType() {
        return EMISSIVE_TEXTURE;
    }
}
