package net.star.copperoverthrow.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Map;

public class CopperChiselItem extends Item {

    private static final Map<Block, Block> CHISEL_MAP =
             Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS
            );

    public static final int ANIMATION_DURATION = 10;
    private static final int USE_DURATION = 10;

    public CopperChiselItem(Properties properties) {
        super(properties);
    }

    /* @Override
    public InteractionResult useOn(UseOnContext context) {

        //Getting the level of the interaction happened, is it in which world or is it Server or Client
        //Level basically means world, just renamed
        //Level defines if it's client or server side operation
        Level level = context.getLevel();

        //Getting the clicked block when using the item
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (CHISEL_MAP.containsKey(clickedBlock)){
             if (!level.isClientSide){
                // SERVER ONLY
                    level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP.get(clickedBlock).defaultBlockState());

                    context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level)
                    //Because we know that this operation is only possible
                    //when it's on server, we can just cast the level onto the ServerLevel
                     , context.getPlayer(),
                            item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                    level.playSound(null, context.getClickedPos(), SoundEvents.COPPER_HIT, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }
    */

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();

        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();


        Player player = context.getPlayer();
        if (player != null && clickedBlock instanceof Block) {
            player.startUsingItem(context.getHand());
        }

        return InteractionResult.CONSUME;
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 200;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration >= 0 && livingEntity instanceof Player player) {

            HitResult hitresult = this.calculateHitResult(player);

            if (hitresult instanceof BlockHitResult blockhitresult && hitresult.getType() == HitResult.Type.BLOCK) {

                int i = this.getUseDuration(stack, livingEntity) - remainingUseDuration + 1;

                //Defines which tick the action is performed
                boolean flag = i % 4 == 0;

                if (flag) {
                    BlockPos blockpos = blockhitresult.getBlockPos();
                    BlockState blockstate = level.getBlockState(blockpos);
                    HumanoidArm humanoidarm = livingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND
                            ? player.getMainArm()
                            : player.getMainArm().getOpposite();

                    SoundEvent soundevent;
                    if (CHISEL_MAP.containsKey(blockstate.getBlock())) {
                        soundevent = SoundEvents.COPPER_BREAK;
                    } else {
                        soundevent = SoundEvents.COPPER_BULB_PLACE;
                    }

                    level.playSound(player, blockpos, soundevent, SoundSource.BLOCKS);
                    if (!level.isClientSide() && CHISEL_MAP.containsKey(blockstate.getBlock())) {

                        boolean flag1 = level.setBlockAndUpdate(blockpos, CHISEL_MAP.get(blockstate.getBlock()).defaultBlockState());

                        if (flag1) {
                            EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                                    ? EquipmentSlot.OFFHAND
                                    : EquipmentSlot.MAINHAND;
                            stack.hurtAndBreak(1, livingEntity, equipmentslot);

                            InteractionResult.sidedSuccess(false);
                        }
                    }
                }

                return;
            }

            livingEntity.releaseUsingItem();
        } else {
            livingEntity.releaseUsingItem();
        }
    }

    private HitResult calculateHitResult(Player player) {
        return ProjectileUtil.getHitResultOnViewVector(
                player, p_281111_ -> !p_281111_.isSpectator() && p_281111_.isPickable(), player.blockInteractionRange()
        );
    }


}
