package net.star.copperoverthrow.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Objects;

public class CopperTrowelItem extends Item {

    public CopperTrowelItem(Properties properties) {super(properties);
    }

     @Override
    public InteractionResult useOn(UseOnContext context) {



        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

             if (!level.isClientSide && player != null){
                 if (anyHotbarBlocks(context)) {

                     Direction direction = getBlockSide(player).getDirection();

                     if (!(level.getBlockState(pos).getBlock() instanceof TallGrassBlock)) {

                         if (direction.equals(Direction.DOWN)) {
                             pos = pos.below();
                         }

                         if (direction.equals(Direction.UP)) {
                             pos = pos.above();
                         }

                         if (direction.equals(Direction.NORTH)) {
                             pos = pos.north();
                         }

                         if (direction.equals(Direction.SOUTH)) {
                             pos = pos.south();
                         }

                         if (direction.equals(Direction.EAST)) {
                             pos = pos.east();
                         }

                         if (direction.equals(Direction.WEST)) {
                             pos = pos.west();
                         }
                     }

                     boolean hasLivingEntity = !level.getEntitiesOfClass(
                             LivingEntity.class,
                             new AABB(pos)).isEmpty();

                     ItemStack item = getRandomItem(context);

                     if (isBlockEmpty(level, pos) && !hasLivingEntity){

                         Block block = ((BlockItem) item.getItem()).getBlock();
                         BlockState stateForPlacement = block.getStateForPlacement(new BlockPlaceContext(level, player, player.getUsedItemHand(), item, getBlockSide(player)));

                         level.setBlockAndUpdate(pos, Objects.requireNonNullElseGet(stateForPlacement, block::defaultBlockState));

                         context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level)
                                 , context.getPlayer(),
                                 tool -> context.getPlayer().onEquippedItemBroken(tool, EquipmentSlot.MAINHAND));

                         item.shrink(1);

                         level.playSound(null, context.getClickedPos(), SoundEvents.COPPER_HIT, SoundSource.BLOCKS);
                     }}
             }
        return InteractionResult.SUCCESS;
    }

    private static boolean anyHotbarBlocks(UseOnContext context){
        for (int i = 0; i < 9; i++) {
           if (context.getPlayer().getSlot(i).get().getItem() instanceof BlockItem) {
               return true;
           }
        }
    return false;
    }

    private static ItemStack getRandomItem(UseOnContext context){
        while (true) {
            ItemStack item = context.getPlayer().getSlot(randomIntGenerator(0, 8)).get();

            if (isItemBlockItem(item.getItem())) {
                return item;
            }
        }
    }

    private static boolean isItemBlockItem(Item item){

        return item instanceof BlockItem;

    }

    private static BlockHitResult getBlockSide(Player player) {
        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        return traceResult;
    }

    public static int randomIntGenerator(int MIN, int MAX) {
        return (int) (Math.random() * (MAX - MIN + 1)) + MIN;
    }

    public static boolean isBlockEmpty(Level level, BlockPos pos){
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof AirBlock || state.getBlock() instanceof TallGrassBlock  || state.getBlock() instanceof DoublePlantBlock;
    }

}
