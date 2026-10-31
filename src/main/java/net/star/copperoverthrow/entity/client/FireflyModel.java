package net.star.copperoverthrow.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.entity.custom.FireflyEntity;


public class FireflyModel extends HierarchicalModel<FireflyEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "firefly"), "main");
    private final ModelPart root;

    public FireflyModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -2.0F, -5.0F, 7.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_antenna = root.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(38, 0).addBox(1.5F, -2.0F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition right_antenna = root.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(38, 0).addBox(-1.5F, -2.0F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition left_wing = root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(-5, 18).mirror().addBox(0.0F, 0.0F, -1.5F, 9.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -2.0F, -1.5F));

        PartDefinition right_wing = root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(-5, 18).addBox(-9.0F, 0.0F, -1.5F, 9.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -2.0F, -1.5F));

        PartDefinition front_legs = root.addOrReplaceChild("front_legs", CubeListBuilder.create().texOffs(28, 1).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 3.0F, -2.0F));

        PartDefinition middle_legs = root.addOrReplaceChild("middle_legs", CubeListBuilder.create().texOffs(27, 3).addBox(-4.0F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 3.0F, 0.0F));

        PartDefinition back_legs = root.addOrReplaceChild("back_legs", CubeListBuilder.create().texOffs(27, 5).addBox(-4.0F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 3.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(FireflyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(FireflyAnimations.flying_forward, limbSwing, limbSwingAmount, 3f, 2.5f);
        this.animate(entity.idleAnimationState, FireflyAnimations.idle, ageInTicks, 1f);
    }
}

