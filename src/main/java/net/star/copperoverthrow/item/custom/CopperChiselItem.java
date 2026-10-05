package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class CopperChiselItem extends Item {

    public CopperChiselItem(Properties properties) {super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();


        Player player = context.getPlayer();
        if (player != null && clickedBlock instanceof Block) {

                    BlockPos blockpos = context.getClickedPos();
                    BlockState blockstate = level.getBlockState(blockpos);
                    ItemStack stack = context.getItemInHand();

                    SoundEvent soundevent;
                    if (doesResultExist(level, blockstate)) {
                        soundevent = SoundEvents.COPPER_BREAK;
                    } else {
                        soundevent = SoundEvents.COPPER_BULB_PLACE;
                    }
                    level.playSound(player, blockpos, soundevent, SoundSource.BLOCKS);

                    if ((!level.isClientSide()) && doesResultExist(level, blockstate)) {

                        while (true) {
                            Block block = getBlockToPlace(level, blockstate);

                            if (blockstate.getBlock() instanceof StairBlock){
                                if (NoAvailableStairBlock(level, blockstate)){
                                    break;
                                }

                                if (!(block instanceof StairBlock)) {
                                    getBlockToPlace(level, blockstate);
                                    continue;
                                }
                            }

                            if (blockstate.getBlock() instanceof WallBlock){
                                if (NoAvailableWallBlock(level, blockstate)){
                                    break;
                                }

                                if (!(block instanceof WallBlock)) {
                                    getBlockToPlace(level, blockstate);
                                    continue;
                                }
                            }

                            if (blockstate.getBlock() instanceof SlabBlock){
                                if (NoAvailableSlabBlock(level, blockstate)){
                                    break;
                                }

                                if (!(block instanceof SlabBlock)) {
                                    getBlockToPlace(level, blockstate);
                                    continue;
                                }
                            }

                            if (!(blockstate.getBlock() instanceof SlabBlock || blockstate.getBlock() instanceof WallBlock || blockstate.getBlock() instanceof StairBlock)){
                                if (NoAvailableFullBlock(level, blockstate)){
                                    break;
                                }

                                if (block instanceof StairBlock || block instanceof SlabBlock || block instanceof WallBlock) {
                                    getBlockToPlace(level, blockstate);
                                    continue;
                                }
                            }

                            if (setBlock(level, blockpos, block, blockstate)) {
                                EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                                        ? EquipmentSlot.OFFHAND
                                        : EquipmentSlot.MAINHAND;
                                stack.hurtAndBreak(1, player, equipmentslot);

                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
            }
        return InteractionResult.CONSUME;
    }

    private boolean setBlock(Level level, BlockPos blockpos, Block block, BlockState blockstate){
        level.setBlockAndUpdate(blockpos, block.withPropertiesOf(blockstate));
        return true;
    }

    private SingleRecipeInput getSingleRecipeInput(Block block) {
      return new SingleRecipeInput (new ItemStack (block.asItem()));
    }

    private HitResult calculateHitResult(Player player) {
        return ProjectileUtil.getHitResultOnViewVector(
                player, p_281111_ -> !p_281111_.isSpectator() && p_281111_.isPickable(), player.blockInteractionRange()
        );
    }

    private Block getBlockToPlace(Level level, BlockState blockstate){

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

    private Boolean NoAvailableSlabBlock(Level level, BlockState blockstate) {

        List<RecipeHolder<StonecutterRecipe>> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int blocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).value().getResultItem(level.registryAccess()).getItem())).getBlock();

            if (block instanceof SlabBlock) {
                blocks++;
            }
        }
        return blocks == 0;
    }

    private Boolean NoAvailableStairBlock(Level level, BlockState blockstate) {

        List<RecipeHolder<StonecutterRecipe>> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int blocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).value().getResultItem(level.registryAccess()).getItem())).getBlock();

            if (block instanceof StairBlock) {
                blocks++;
            }
        }
        return blocks == 0;
    }

    private Boolean NoAvailableWallBlock(Level level, BlockState blockstate) {

        List<RecipeHolder<StonecutterRecipe>> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int blocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).value().getResultItem(level.registryAccess()).getItem())).getBlock();

            if (block instanceof StairBlock) {
                blocks++;
            }
        }
        return blocks == 0;
    }


    private Boolean doesResultExist (Level level, BlockState blockstate){
        List<RecipeHolder<StonecutterRecipe>> recipeList =
                (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING,  getSingleRecipeInput(blockstate.getBlock()), level));

        //Returns false or true
        return !recipeList.isEmpty();
    }

    public static int randomIntGenerator(int MIN, int MAX) {
        return (int) (Math.random() * (MAX - MIN + 1)) + MIN;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_chisel_item.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
