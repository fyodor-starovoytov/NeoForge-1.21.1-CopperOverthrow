package net.star.copperoverthrow.item.armor.client.model;// Made with Blockbench 5.2.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.star.copperoverthrow.CopperOverthrow;

public class GlowingGogglesModel extends ArmorModel {
	public GlowingGogglesModel(ModelPart root) {
		super(root);
	}

	private static final ResourceLocation EMISSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "textures/models/armor/glowing_goggles_emissive.png");
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(2, 3).addBox(-4.5F, -4.5F, -4.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(44, 53).addBox(1.5F, -3.0F, -2.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(45, 53).addBox(-1.5F, -3.0F, -2.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(33, 48).addBox(-0.5F, -4.0F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(34, 44).addBox(-0.5F, -3.0F, -2.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(34, 48).addBox(-0.5F, -1.0F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, -3.0F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(34, 44).addBox(-0.5F, -3.0F, -2.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 53).addBox(1.5F, -3.0F, -2.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(45, 53).addBox(-1.5F, -3.0F, -2.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(33, 48).addBox(-0.5F, -4.0F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(34, 48).addBox(-0.5F, -1.0F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, -3.0F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_boot = partdefinition.addOrReplaceChild("right_boot", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_boot = partdefinition.addOrReplaceChild("left_boot", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}