package net.star.copperoverthrow.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.star.copperoverthrow.CopperOverthrow;


public class RedWindChimeModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "red_wind_chime"), "main");
    private final ModelPart root;
    private final ModelPart tubes;
    private final ModelPart tube2;
    private final ModelPart tube6;
    private final ModelPart tube7;
    private final ModelPart tube4;
    private final ModelPart cup;
    private final ModelPart hitbox;

    public RedWindChimeModel(ModelPart root) {
        this.root = root.getChild("root");
        this.tubes = this.root.getChild("tubes");
        this.tube2 = this.tubes.getChild("tube2");
        this.tube6 = this.tubes.getChild("tube6");
        this.tube7 = this.tubes.getChild("tube7");
        this.tube4 = this.tubes.getChild("tube4");
        this.cup = this.root.getChild("cup");
        this.hitbox = this.root.getChild("hitbox");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition tubes = root.addOrReplaceChild("tubes", CubeListBuilder.create(), PartPose.offset(0.0F, 32.0F, 0.0F));

        PartDefinition tube2 = tubes.addOrReplaceChild("tube2", CubeListBuilder.create().texOffs(24, 23).addBox(-0.5F, 3.0F, -0.55F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.15F, -24.0F, 1.2F));

        PartDefinition cube_r1 = tube2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 2.0F, 0.3F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r2 = tube2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.3F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tube6 = tubes.addOrReplaceChild("tube6", CubeListBuilder.create().texOffs(4, 26).addBox(-0.55F, 2.0F, -0.55F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -24.0F, -0.8F));

        PartDefinition cube_r3 = tube6.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 14).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.3F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r4 = tube6.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(28, 12).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 2.0F, 0.3F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tube7 = tubes.addOrReplaceChild("tube7", CubeListBuilder.create().texOffs(0, 26).addBox(-0.5F, 3.0F, -0.55F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.85F, -24.0F, -0.8F));

        PartDefinition cube_r5 = tube7.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(18, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 2.0F, 0.3F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r6 = tube7.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.3F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tube4 = tubes.addOrReplaceChild("tube4", CubeListBuilder.create().texOffs(24, 12).addBox(-0.5F, 4.0F, -0.55F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.85F, -24.0F, 1.2F));

        PartDefinition cube_r7 = tube4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 2.0F, 0.3F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r8 = tube4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(8, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.3F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cup = root.addOrReplaceChild("cup", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 3.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r9 = cup.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(22, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r10 = cup.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(20, 26).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 2.0F, 0.35F, 0.0F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);

        float idleSpeed = 0.05F;
        float idleDegree = 0.05F;

        this.root.zRot = Mth.sin(ageInTicks * idleSpeed) * idleDegree;
        this.root.yRot = Mth.sin(ageInTicks * idleSpeed) * idleDegree;
        this.cup.yRot = -Mth.cos(ageInTicks * idleSpeed) * idleDegree;
        this.root.y += Mth.cos(ageInTicks * idleSpeed * 2.0F) * 0.2F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
