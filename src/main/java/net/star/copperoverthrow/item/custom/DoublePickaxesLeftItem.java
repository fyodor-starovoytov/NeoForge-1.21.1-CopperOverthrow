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
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offhandStack = player.getOffhandItem();

        boolean isInOffhand = (offhandStack == stack);
        boolean mainHandValid = mainHandStack.is(ModItems.COPPER_DOUBLE_PICKAXES.get())
                && mainHandStack.get(ModDataComponents.IS_USED) != null;

        if (!isInOffhand || !mainHandValid) {
            stack.setCount(0);
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}