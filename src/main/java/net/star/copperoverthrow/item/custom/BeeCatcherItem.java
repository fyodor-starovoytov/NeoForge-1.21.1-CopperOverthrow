package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.star.copperoverthrow.component.ModDataComponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.entity.Entity.RemovalReason.DISCARDED;

public class BeeCatcherItem extends Item {

    public BeeCatcherItem(Properties properties) {
        super(properties);

    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity instanceof Bee bee && hasFreeSlot(stack)) {
            BlockPos pos = entity.blockPosition();
            boolean isClient = entity.level().isClientSide();

            if (!isClient) {

            CompoundTag tag = new CompoundTag();
            bee.saveWithoutId(tag);
            tag.remove("Pos");
            tag.remove("Motion");
            tag.remove("Rotation");

            List<CustomData> bees = new ArrayList<>(getBees(stack));
            bees.add(CustomData.of(tag));

            //DOESNT SAVE THE DATA IN CREATIVE
            stack.set(ModDataComponents.CAUGHT_BEES, bees);
            entity.level().playSound(null, pos, SoundEvents.BEEHIVE_ENTER, SoundSource.NEUTRAL);
            entity.discard();

            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();

        List<CustomData> bees = getBees(stack);
        List<CustomData> remaining = new ArrayList<>(bees);

        if (!(bees.isEmpty()) && !level.isClientSide) {

            while (!remaining.isEmpty()) {
                CompoundTag tag = remaining.removeLast().copyTag();

            Bee bee = EntityType.BEE.create(
                    (ServerLevel) level,
                    b -> b.load(tag),
                    context.getClickedPos(),
                    MobSpawnType.BUCKET,
                    true,
                    false
            );
            if (bee != null) {
                BlockPos spawnPos = context.getClickedPos().relative(context.getClickedFace());
                bee.moveTo(
                        spawnPos.getX() + Math.random(),
                        spawnPos.getY(),
                        spawnPos.getZ() + Math.random(),
                        bee.getYRot(),
                        bee.getXRot()
                );
                level.addFreshEntity(bee);
            }}
                stack.set(ModDataComponents.CAUGHT_BEES, null);
                level.playSound(null, context.getClickedPos(), SoundEvents.BEEHIVE_ENTER, SoundSource.NEUTRAL);
                return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_bee.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.bee_catcher_item.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.bee_catcher_free_item.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private List<CustomData> getBees(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CAUGHT_BEES, List.of());
    }

    private boolean hasFreeSlot(ItemStack stack) {
        return getBees(stack).size() < 3;
    }

}
