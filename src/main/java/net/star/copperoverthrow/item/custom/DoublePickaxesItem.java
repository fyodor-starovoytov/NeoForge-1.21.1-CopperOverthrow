package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.Tags;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.util.ModTags;

import java.util.List;
import java.util.Stack;

public class DoublePickaxesItem extends DiggerItem {
    public DoublePickaxesItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Tags.Blocks.STONES) && stack.get(ModDataComponents.IS_USED) != null) {
            return super.getDestroySpeed(stack, state) + 55.0f;
        }
        return super.getDestroySpeed(stack, state) + -1.3f;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            ItemStack offhandStack = player.getItemInHand(InteractionHand.OFF_HAND);

            if (isSelected && offhandStack.is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT)){
                stack.set(ModDataComponents.IS_USED, 1);
            }

            if (!isSelected || !offhandStack.is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT)){
                stack.set(ModDataComponents.IS_USED, null);
            }

            if (isSelected && offhandStack.isEmpty()) {
                player.getInventory().setItem(Inventory.SLOT_OFFHAND, new ItemStack(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get()));
            }

            if (!isSelected && offhandStack.is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT)) {
                player.getInventory().setItem(Inventory.SLOT_OFFHAND, ItemStack.EMPTY);
            }
        }


    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.crowbar_item.tooltip"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}