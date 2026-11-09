package net.star.copperoverthrow.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

public class CopperChiselItem extends AbstractRepairableCopperItem {

    public CopperChiselItem(Properties properties) {super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();

        Player player = context.getPlayer();
        if (player != null) {

                    BlockPos blockpos = context.getClickedPos();
                    BlockState blockstate = level.getBlockState(blockpos);
                    ItemStack stack = context.getItemInHand();

                    SoundEvent soundevent;
                    if (doesResultExist(level, blockstate)) {
                        soundevent = SoundEvents.COPPER_BREAK;
                    } else {
                        soundevent = SoundEvents.COPPER_PLACE;
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
                                stack.hurtAndBreak(1, player, (entity) ->
                                {entity.broadcastBreakEvent(equipmentslot);
                                });

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

    private SimpleContainer getSingleRecipeInput(Block block) {
        return new SimpleContainer(new ItemStack(block.asItem()));
    }

    private HitResult calculateHitResult(Player player) {
        return ProjectileUtil.getHitResultOnViewVector(
                player, p_281111_ -> !p_281111_.isSpectator() && p_281111_.isPickable(), player.getBlockReach()
        );
    }

    private Block getBlockToPlace(Level level, BlockState blockstate){

    List<StonecutterRecipe> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING,  getSingleRecipeInput(blockstate.getBlock()), level));

    int recipeListLength = recipeList.size();

    return (((BlockItem)(recipeList.get(randomIntGenerator(0, recipeListLength-1)).getResultItem(level.registryAccess()).getItem())).getBlock());
    }

    private Boolean NoAvailableFullBlock(Level level, BlockState blockstate) {

        List<StonecutterRecipe> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int fullBlocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).getResultItem(level.registryAccess()).getItem())).getBlock();

            if (!(block instanceof SlabBlock || block instanceof StairBlock || block instanceof WallBlock)) {
                fullBlocks++;
            }
        }
        return fullBlocks == 0;
    }

    private Boolean NoAvailableSlabBlock(Level level, BlockState blockstate) {

        List<StonecutterRecipe> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int blocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).getResultItem(level.registryAccess()).getItem())).getBlock();

            if (block instanceof SlabBlock) {
                blocks++;
            }
        }
        return blocks == 0;
    }

    private Boolean NoAvailableStairBlock(Level level, BlockState blockstate) {

        List<StonecutterRecipe> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int blocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).getResultItem(level.registryAccess()).getItem())).getBlock();

            if (block instanceof StairBlock) {
                blocks++;
            }
        }
        return blocks == 0;
    }

    private Boolean NoAvailableWallBlock(Level level, BlockState blockstate) {

        List<StonecutterRecipe> recipeList = (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, getSingleRecipeInput(blockstate.getBlock()), level));

        int recipeListLength = recipeList.size();

        int blocks = 0;

        for (int i = 0; i < recipeListLength; i++) {

            Block block = ((BlockItem) (recipeList.get(i).getResultItem(level.registryAccess()).getItem())).getBlock();

            if (block instanceof StairBlock) {
                blocks++;
            }
        }
        return blocks == 0;
    }


    private Boolean doesResultExist (Level level, BlockState blockstate){
        List<StonecutterRecipe> recipeList =
                (level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING,  getSingleRecipeInput(blockstate.getBlock()), level));

        return !recipeList.isEmpty();
    }

    public static int randomIntGenerator(int MIN, int MAX) {
        return (int) (Math.random() * (MAX - MIN + 1)) + MIN;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_used_on_block.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.copper_chisel_item.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

}
