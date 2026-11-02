package net.star.copperoverthrow.item.armor.client.model;// Made with Blockbench 5.2.2


import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CopperExoskeletonModel extends ArmorModel {

	public CopperExoskeletonModel(ModelPart root) {
        super(root);
	}
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(38, 29).addBox(-4.0F, 1.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(1, 15).addBox(-3.0F, -2.0F, -3.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(25, 22).addBox(-3.0F, 7.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(25, 22).addBox(-3.0F, 7.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 29).addBox(-2.0F, -3.0F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(14, 31).addBox(1.0F, 1.0F, 2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 42).addBox(-3.0F, 1.0F, 2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 40).addBox(-3.0F, 1.0F, -4.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(28, 0).addBox(2.0F, 1.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(1.0F, -3.0F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-2.0F, -2.0F, -3.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 15).addBox(-2.0F, 7.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_boot = partdefinition.addOrReplaceChild("right_boot", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_boot = partdefinition.addOrReplaceChild("left_boot", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

}