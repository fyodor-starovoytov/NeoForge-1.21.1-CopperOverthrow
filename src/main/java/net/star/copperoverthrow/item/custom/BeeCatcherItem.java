package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.star.copperoverthrow.block.custom.CopperBeeosphereBlock;
import net.star.copperoverthrow.component.ModDataComponents;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

                List<CompoundTag> bees = new ArrayList<>(getBees(stack));
                bees.add(tag);
                ListTag listTag = new ListTag();
                for (CompoundTag beeData : bees) {
                    listTag.add(beeData);
                }
                stack.getOrCreateTag().put("caught_bees", listTag);

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

        List<CompoundTag> bees = new ArrayList<>(getBees(stack));
        List<CompoundTag> remaining = new ArrayList<>(bees);

        if (level.getBlockState(context.getClickedPos()).getBlock() instanceof CopperBeeosphereBlock){
            return InteractionResult.SUCCESS;
        }

        if (!(bees.isEmpty()) && !level.isClientSide) {

            while (!remaining.isEmpty()) {
                CompoundTag tag = remaining.remove(remaining.size() - 1).copy();

            Bee bee = EntityType.BEE.spawn(
                    (ServerLevel) level,
                    null,
                    b -> {
                        tag.remove("UUID");
                        b.load(tag);
                    },
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
            }}
                stack.removeTagKey("caught_bees");
                level.playSound(null, context.getClickedPos(), SoundEvents.BEEHIVE_ENTER, SoundSource.NEUTRAL);
                return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
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
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    public static List<CompoundTag> getBees(ItemStack stack) {
        List<CompoundTag> beeList = new ArrayList<>();

        if (stack.hasTag() && stack.getTag().contains("caught_bees", Tag.TAG_LIST)) {
            ListTag listTag = stack.getTag().getList("caught_bees", Tag.TAG_COMPOUND);

            for (int i = 0; i < listTag.size(); i++) {
                beeList.add(listTag.getCompound(i));
            }
        }

        return beeList;
    }

    public boolean hasFreeSlot(ItemStack stack) {
        return getBees(stack).size() < 3;
    }

    public boolean hasNoBees(ItemStack stack) {
        return getBees(stack).isEmpty();
    }
}
