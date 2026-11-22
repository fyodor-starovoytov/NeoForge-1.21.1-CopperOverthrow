package net.star.copperoverthrow.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.ModItems;

public class DoublePickaxesLeftItem extends DiggerItem {


    public DoublePickaxesLeftItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity instanceof Player player) {
                boolean isOffhand = (slotId == Inventory.SLOT_OFFHAND);
                ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (!mainHandStack.is(ModItems.COPPER_DOUBLE_PICKAXES.get()) || player.getMainHandItem().get(ModDataComponents.IS_USED) == null) {
                    stack.setCount(0);
                }

                if (!isOffhand) {
                    stack.setCount(0);
                }
            }
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}