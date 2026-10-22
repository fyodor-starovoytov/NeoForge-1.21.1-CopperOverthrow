package net.star.copperoverthrow.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;

public abstract class AbstractRepairableCopperItem extends Item {
    public AbstractRepairableCopperItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Tags.Items.INGOTS_COPPER) || super.isValidRepairItem(stack, repairCandidate);
    }
}
