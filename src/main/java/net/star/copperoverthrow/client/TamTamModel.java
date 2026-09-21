package net.star.copperoverthrow.client;// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.star.copperoverthrow.CopperOverthrow;

public class TamTamModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "tamtammodel"), "main");
	private final ModelPart root;
	private final ModelPart tamtam_body;

	public TamTamModel(ModelPart root) {
		this.root = root.getChild("root");
		this.tamtam_body = this.root.getChild("tamtam_body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tamtam_body = root.addOrReplaceChild("tamtam_body", CubeListBuilder.create().texOffs(46, 18).addBox(-5.0F, 28.0F, 0.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 20).addBox(-5.0F, 3.0F, 0.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 44).addBox(-8.0F, 4.0F, 0.0F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, 5.0F, 0.0F, 22.0F, 22.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 54).addBox(5.0F, 0.0F, 0.5F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(27, 54).addBox(-8.0F, 0.0F, 0.5F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-8.0F, 27.0F, 0.0F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 50).addBox(6.0F, 12.0F, -2.0F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(46, 12).addBox(-4.0F, 22.0F, -2.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(30, 46).addBox(-4.0F, 9.0F, -2.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(12, 50).addBox(-7.0F, 12.0F, -2.0F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(42, 23).addBox(-6.0F, 10.0F, -2.0F, 12.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 46).addBox(-6.0F, 25.0F, -1.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-9.0F, 7.0F, -1.0F, 18.0F, 18.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(46, 0).addBox(-6.0F, 6.0F, -1.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -32.0F, -0.5F));

		PartDefinition cube_r1 = tamtam_body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 4).addBox(-6.0F, -8.0F, -1.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 16.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = tamtam_body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(46, 8).addBox(-6.0F, -8.0F, -1.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.0F, 16.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r3 = tamtam_body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(42, 42).addBox(-9.0F, -1.0F, 0.0F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 15.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r4 = tamtam_body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(42, 40).addBox(-9.0F, -1.0F, 0.0F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 15.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r5 = tamtam_body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(46, 52).addBox(-4.0F, -2.0F, 0.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 15.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r6 = tamtam_body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 52).addBox(-4.0F, -2.0F, 0.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 15.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {

	}
}