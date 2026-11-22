package net.star.copperoverthrow.item.custom;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;

public class DoublePickaxesItem extends DiggerItem {

    public DoublePickaxesItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if ((state.is(Tags.Blocks.STONES)
                || (state.is(Tags.Blocks.COBBLESTONES)
                || (state.is(Tags.Blocks.END_STONES)
                || (state.is(Tags.Blocks.NETHERRACKS)))))
                && stack.get(ModDataComponents.IS_USED) != null) {
            return super.getDestroySpeed(stack, state) * 16;
        }
        return super.getDestroySpeed(stack, state) / 2;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);


            if (!isSelected) {
                stack.set(ModDataComponents.IS_USED, null);
            }

            if (isSelected && offhandStack.is(ItemStack.EMPTY.getItem())) {
                player.getInventory().setItem(Inventory.SLOT_OFFHAND, new ItemStack(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get()));
                stack.set(ModDataComponents.IS_USED, 1);
            }

            if (mainHandStack.is(ModItems.COPPER_DOUBLE_PICKAXES.get())) {

            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.get(ModDataComponents.IS_USED) == null && super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }
}