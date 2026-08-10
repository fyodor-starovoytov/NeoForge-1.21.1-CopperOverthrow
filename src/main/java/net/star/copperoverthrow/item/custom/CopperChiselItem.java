package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
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
                boolean flag = i % 5 == 0;

                if (flag) {
                    BlockPos blockpos = blockhitresult.getBlockPos();
                    BlockState blockstate = level.getBlockState(blockpos);

                    SoundEvent soundevent;
                    if (doesResultExist(level, blockstate)) {
                        soundevent = SoundEvents.COPPER_BREAK;
                    } else {
                        soundevent = SoundEvents.COPPER_BULB_PLACE;
                    }
                    level.playSound(player, blockpos, soundevent, SoundSource.BLOCKS);

                        if ((!level.isClientSide()) && doesResultExist(level, blockstate)) {

                            while (true) {
                                Block block = getBlock(level, blockstate);

                                if (NoAvailableFullBlock(level, blockstate)){
                                    break;
                                }

                                if (block instanceof StairBlock || block instanceof SlabBlock || block instanceof WallBlock) {
                                    getBlock(level, blockstate);
                                    continue;
                                }

                                boolean flag1 = level.setBlockAndUpdate(blockpos, block.defaultBlockState());

                                if (flag1) {
                                    System.out.print(BuiltInRegistries.BLOCK);
                                    EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                                            ? EquipmentSlot.OFFHAND
                                            : EquipmentSlot.MAINHAND;
                                    stack.hurtAndBreak(1, livingEntity, equipmentslot);

                                    break;
                                }
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

    private SingleRecipeInput getSingleRecipeInput(Block block) {
      return new SingleRecipeInput (new ItemStack (block.asItem()));
    }

    private HitResult calculateHitResult(Player player) {
        return ProjectileUtil.getHitResultOnViewVector(
                player, p_281111_ -> !p_281111_.isSpectator() && p_281111_.isPickable(), player.blockInteractionRange()
        );
    }

    private Block getBlock(Level level, BlockState blockstate){

    List<RecipeHolder<StonecutterRecipe>> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING,  getSingleRecipeInput(blockstate.getBlock()), level));

    int recipeListLength = recipeList.size();

    return (((BlockItem)(recipeList.get(randomIntGenerator(0, recipeListLength-1)).value().getResultItem(level.registryAccess()).getItem())).getBlock());
    }

    private Boolean NoAvailableFullBlock(Level level, BlockState blockstate) {

        List<RecipeHolder<StonecutterRecipe>> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int fullBlocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).value().getResultItem(level.registryAccess()).getItem())).getBlock();

            if (!(block instanceof SlabBlock || block instanceof StairBlock || block instanceof WallBlock)) {
                fullBlocks++;
            }
        }
        return fullBlocks == 0;
    }

    private Boolean doesResultExist (Level level, BlockState blockstate){
        List<RecipeHolder<StonecutterRecipe>> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING,  getSingleRecipeInput(blockstate.getBlock()), level));

        //Returns false or true
        return !recipeList.isEmpty();
    }

    public static int randomIntGenerator(int MIN, int MAX) {
        return (int) (Math.random() * (MAX - MIN + 1)) + MIN;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_chisel_item.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
