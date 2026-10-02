package net.star.copperoverthrow.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.custom.LogStripperBlock;

import java.util.ArrayList;
import java.util.List;

public class HandsawItem extends DiggerItem {
    public static final int ANIMATION_DURATION = 5;

    public HandsawItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_AXE, properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return ServerConfig.HANDSAW_USE_TIME.get();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockState clickedBlock = level.getBlockState(context.getClickedPos());
        Player player = context.getPlayer();

        if (player != null && clickedBlock.getBlock() instanceof Block && !(clickedBlock.getBlock() instanceof LogStripperBlock) && clickedBlock.is(BlockTags.LOGS)) {
            player.startUsingItem(context.getHand());
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration >= 0 && livingEntity instanceof Player player) {

            HitResult hitresult = this.calculateHitResult(player);

            if (hitresult instanceof BlockHitResult blockhitresult && hitresult.getType() == HitResult.Type.BLOCK && level.getBlockState(blockhitresult.getBlockPos()).is(BlockTags.LOGS)) {

                BlockPos pos = blockhitresult.getBlockPos();
                BlockState state = level.getBlockState(pos);

                int i = this.getUseDuration(stack, livingEntity) - remainingUseDuration + 1;

                //Defines which tick the action is performed
                boolean flag = i % ANIMATION_DURATION == 0;

                if (flag) {
                    level.playLocalSound(player, SoundEvents.FROG_EAT, SoundSource.PLAYERS, 1, 1);
                }
                return;
            }
            livingEntity.releaseUsingItem();
        } else {
            livingEntity.releaseUsingItem();
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof Player player) {
            HitResult hitresult = this.calculateHitResult(player);

            if (hitresult instanceof BlockHitResult blockHitResult && hitresult.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = blockHitResult.getBlockPos();
                List<BlockPos> BlocksToBeDestroyed = new ArrayList<>(getBlocksToBeDestroyed(pos, level));

                for (BlockPos destroyPos : BlocksToBeDestroyed) {
                    level.destroyBlock(destroyPos, true, player);
                }

                EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                        ? EquipmentSlot.OFFHAND
                        : EquipmentSlot.MAINHAND;
                stack.hurtAndBreak(BlocksToBeDestroyed.size(), livingEntity, equipmentslot);
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    private HitResult calculateHitResult(Player player) {
        return ProjectileUtil.getHitResultOnViewVector(
                player, p_281111_ -> !p_281111_.isSpectator() && p_281111_.isPickable(), player.blockInteractionRange()
        );
    }

    public static List<BlockPos> getBlocksToBeDestroyed(BlockPos initalBlockPos, Level level) {
        List<BlockPos> toBeChecked = new ArrayList<>();
        List<BlockPos> toBeDestroyed = new ArrayList<>();
        int r = 1;

        Block targetBlock = level.getBlockState(initalBlockPos).getBlock();

        toBeChecked.addFirst(initalBlockPos.immutable());
        toBeDestroyed.addFirst(initalBlockPos.immutable());

        while (!toBeChecked.isEmpty() && toBeDestroyed.size() < ServerConfig.HANDSAW_MAX_DESTROYED_BLOCKS.get()){
            BlockPos comparable = toBeChecked.removeLast();
            for (BlockPos pos : BlockPos.betweenClosed(comparable.offset(-r, 0, -r), comparable.offset(r, r, r))) {
                BlockState state = level.getBlockState(pos);

                if (state.is(targetBlock) && !toBeDestroyed.contains(pos.immutable())) {
                    toBeChecked.add(pos.immutable());
                    toBeDestroyed.add(pos.immutable());
                }
            }

        }
        return toBeDestroyed;
    }
}
