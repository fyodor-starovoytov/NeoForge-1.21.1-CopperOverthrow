package net.star.copperoverthrow.client;
// Made with Blockbench 5.2.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.star.copperoverthrow.CopperOverthrow;

public class BambooWindChimeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CopperOverthrow.MOD_ID, "bamboo_wind_chime"), "main");
	private final ModelPart root;
	private final ModelPart cup;
	private final ModelPart tubes;
	private final ModelPart tube0;
	private final ModelPart tube1;
	private final ModelPart tube2;
	private final ModelPart tube3;
	private final ModelPart tube4;
	private final ModelPart tube6;
	private final ModelPart tube7;
	private final ModelPart tube8;

	public wooden_wind_chime(ModelPart root) {
		this.root = root.getChild("root");
		this.cup = this.root.getChild("cup");
		this.tubes = this.root.getChild("tubes");
		this.tube0 = this.tubes.getChild("tube0");
		this.tube1 = this.tubes.getChild("tube1");
		this.tube2 = this.tubes.getChild("tube2");
		this.tube3 = this.tubes.getChild("tube3");
		this.tube4 = this.tubes.getChild("tube4");
		this.tube6 = this.tubes.getChild("tube6");
		this.tube7 = this.tubes.getChild("tube7");
		this.tube8 = this.tubes.getChild("tube8");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition cup = root.addOrReplaceChild("cup", CubeListBuilder.create().texOffs(0, 7).addBox(3.0F, 5.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 18).addBox(-4.0F, 5.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, 5.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = cup.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 28).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, 5.5F, 0.35F, -3.1416F, -0.7418F, -2.3998F));

		PartDefinition cube_r2 = cup.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 27).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.35F, 5.0F, 0.35F, 0.0F, -0.7854F, 0.7418F));

		PartDefinition cube_r3 = cup.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 28).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.35F, 5.0F, 0.35F, -3.1416F, -0.7418F, 2.3562F));

		PartDefinition cube_r4 = cup.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(2, 27).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9F, 5.5F, 0.35F, 0.0F, -0.7854F, -0.7854F));

		PartDefinition cube_r5 = cup.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(28, 0).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 5.5F, -3.15F, -2.2061F, -0.4981F, 2.5667F));

		PartDefinition cube_r6 = cup.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(8, 12).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 5.5F, -3.15F, -0.9553F, -0.5236F, 0.6155F));

		PartDefinition cube_r7 = cup.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(6, 27).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 5.0F, 3.35F, 2.2061F, -0.4981F, -2.5667F));

		PartDefinition cube_r8 = cup.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 5.0F, 3.35F, 0.9553F, -0.5236F, -0.6155F));

		PartDefinition cube_r9 = cup.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 28).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r10 = cup.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(28, 23).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r11 = cup.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(22, 12).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -5.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r12 = cup.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(22, 7).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition tubes = root.addOrReplaceChild("tubes", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition tube0 = tubes.addOrReplaceChild("tube0", CubeListBuilder.create().texOffs(10, 7).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 2.0F));

		PartDefinition cube_r13 = tube0.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(14, 29).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r14 = tube0.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(12, 29).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube1 = tubes.addOrReplaceChild("tube1", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, 4.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 1.5F));

		PartDefinition cube_r15 = tube1.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(22, 23).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r16 = tube1.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(20, 23).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube2 = tubes.addOrReplaceChild("tube2", CubeListBuilder.create().texOffs(4, 12).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 2.0F));

		PartDefinition cube_r17 = tube2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(28, 27).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r18 = tube2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(26, 28).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube3 = tubes.addOrReplaceChild("tube3", CubeListBuilder.create().texOffs(14, 7).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -11.0F, 0.0F));

		PartDefinition cube_r19 = tube3.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(18, 29).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r20 = tube3.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(16, 29).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube4 = tubes.addOrReplaceChild("tube4", CubeListBuilder.create().texOffs(18, 7).addBox(-0.5F, 4.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -11.0F, 0.0F));

		PartDefinition cube_r21 = tube4.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(24, 23).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r22 = tube4.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube6 = tubes.addOrReplaceChild("tube6", CubeListBuilder.create().texOffs(8, 18).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, -1.5F));

		PartDefinition cube_r23 = tube6.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(30, 3).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r24 = tube6.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube7 = tubes.addOrReplaceChild("tube7", CubeListBuilder.create().texOffs(12, 18).addBox(-0.5F, 4.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, -2.0F));

		PartDefinition cube_r25 = tube7.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(26, 23).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r26 = tube7.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(26, 0).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tube8 = tubes.addOrReplaceChild("tube8", CubeListBuilder.create().texOffs(16, 18).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, -2.0F));

		PartDefinition cube_r27 = tube8.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(10, 29).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.35F, 2.0F, 0.35F, 0.0F, -2.3998F, 0.0F));

		PartDefinition cube_r28 = tube8.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(8, 29).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.35F, 2.0F, 0.35F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}