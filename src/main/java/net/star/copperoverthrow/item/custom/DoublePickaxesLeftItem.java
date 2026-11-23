package net.star.copperoverthrow.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.star.copperoverthrow.item.ModItems;

public class DoublePickaxesLeftItem extends DiggerItem {

    public DoublePickaxesLeftItem(float attackDamageModifier, float attackSpeedModifier, Tier tier, Properties properties) {
        super(attackDamageModifier, attackSpeedModifier, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offhandStack = player.getOffhandItem();

        boolean isInOffhand = (offhandStack == stack);
        CompoundTag mainTag = mainHandStack.getTag();
        boolean mainHandValid = mainHandStack.is(ModItems.COPPER_DOUBLE_PICKAXES.get())
                && mainTag != null
                && mainTag.getBoolean(DoublePickaxesItem.TAG_IS_USED);

        if (!isInOffhand || !mainHandValid) {
            stack.setCount(0);
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}