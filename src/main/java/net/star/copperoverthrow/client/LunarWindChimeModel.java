package net.star.copperoverthrow.client;// Made with Blockbench 5.2.2
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

public class LunarWindChimeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "lunar_wind_chime"), "main");
	private final ModelPart root;
	private final ModelPart top_coin;
	private final ModelPart cup;
	private final ModelPart tubes;
	private final ModelPart tube0;
	private final ModelPart tube1;
	private final ModelPart tube2;
	private final ModelPart tube3;
	private final ModelPart tube4;

	public LunarWindChimeModel(ModelPart root) {
		this.root = root.getChild("root");
		this.top_coin = this.root.getChild("top_coin");
		this.cup = this.root.getChild("cup");
		this.tubes = this.root.getChild("tubes");
		this.tube0 = this.tubes.getChild("tube0");
		this.tube1 = this.tubes.getChild("tube1");
		this.tube2 = this.tubes.getChild("tube2");
		this.tube3 = this.tubes.getChild("tube3");
		this.tube4 = this.tubes.getChild("tube4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition top_coin = root.addOrReplaceChild("top_coin", CubeListBuilder.create().texOffs(20, 11).addBox(-0.5F, 3.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(20, 16).addBox(-0.5F, 7.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(20, 21).addBox(-0.5F, 4.0F, 1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-0.5F, 5.0F, -3.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-0.5F, 4.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 24).addBox(-0.5F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 26).addBox(-0.5F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 26).addBox(-0.5F, 8.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = top_coin.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, -3.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = top_coin.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 4).addBox(0.0F, -3.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition cup = root.addOrReplaceChild("cup", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 3.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(1, 9).addBox(-1.0F, 4.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(11, 9).addBox(-1.0F, 4.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition cube_r3 = cup.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 4).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r4 = cup.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition tubes = root.addOrReplaceChild("tubes", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition tube0 = tubes.addOrReplaceChild("tube0", CubeListBuilder.create().texOffs(8, 13).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -4.0F));

		PartDefinition cube_r5 = tube0.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(26, 21).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r6 = tube0.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(20, 26).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition tube1 = tubes.addOrReplaceChild("tube1", CubeListBuilder.create().texOffs(12, 13).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition cube_r7 = tube1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 26).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r8 = tube1.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(22, 26).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition tube2 = tubes.addOrReplaceChild("tube2", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r9 = tube2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(6, 27).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r10 = tube2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(26, 25).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition tube3 = tubes.addOrReplaceChild("tube3", CubeListBuilder.create().texOffs(16, 13).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition cube_r11 = tube3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(10, 27).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r12 = tube3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(8, 27).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		PartDefinition tube4 = tubes.addOrReplaceChild("tube4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 4.0F));

		PartDefinition cube_r13 = tube4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(12, 28).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r14 = tube4.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(28, 11).addBox(0.0F, -2.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}