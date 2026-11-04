package net.star.copperoverthrow.event;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.star.copperoverthrow.component.ModDataComponents;

public class CustomSpyglassArmposeExtension implements IClientItemExtensions {

    public static final CustomSpyglassArmposeExtension INSTANCE = new CustomSpyglassArmposeExtension();

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (stack.get(ModDataComponents.IS_USED) != null && entity.getUsedItemHand() == hand) {
            return HumanoidModel.ArmPose.SPYGLASS;
        }
        return HumanoidModel.ArmPose.ITEM;
    }

}
