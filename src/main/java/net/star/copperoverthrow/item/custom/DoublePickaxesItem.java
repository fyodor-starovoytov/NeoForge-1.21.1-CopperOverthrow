package net.star.copperoverthrow.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.star.copperoverthrow.item.ModItems;

public class DoublePickaxesItem extends DiggerItem {
    public static final String TAG_IS_USED = "is_used";

    public DoublePickaxesItem(float attackDamageModifier, float attackSpeedModifier, Tier tier, Properties properties) {
        super(attackDamageModifier, attackSpeedModifier, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        CompoundTag tag = stack.getTag();
        boolean isUsed = tag != null && tag.getBoolean(TAG_IS_USED);

        if ((state.is(Tags.Blocks.STONE)
                || state.is(Tags.Blocks.COBBLESTONE)
                || state.is(Tags.Blocks.END_STONES)
                || state.is(Tags.Blocks.NETHERRACK))
                && isUsed) {
            return super.getDestroySpeed(stack, state) * 16;
        }
        return super.getDestroySpeed(stack, state) / 2;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offhandStack = player.getOffhandItem();
        boolean isHeldInMainHand = isSelected && mainHandStack == stack;

        if (!isHeldInMainHand) {
            if (stack.hasTag() && stack.getTag().contains(TAG_IS_USED)) {
                stack.removeTagKey(TAG_IS_USED);
            }
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean(TAG_IS_USED) && offhandStack.is(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get())) {
            tag.putBoolean(TAG_IS_USED, true);
        }

        if (offhandStack.isEmpty()) {
            player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.COPPER_DOUBLE_PICKAXES_LEFT.get()));
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        boolean wasUsed = oldStack.hasTag() && oldStack.getTag().getBoolean(TAG_IS_USED);
        return !wasUsed && super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }
}