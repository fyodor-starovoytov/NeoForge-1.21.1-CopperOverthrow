package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.block.entity.custom.CopperBeeosphereBlockEntity;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.custom.BeeCatcherItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CopperBeeosphereBlock extends BeehiveBlock {
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public CopperBeeosphereBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CopperBeeosphereBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CopperBeeosphereBlockEntity copperBeeosphereBlockEntity) {
            if (stack.getItem() instanceof BeeCatcherItem beeCatcherItem) {

                if (!beeCatcherItem.hasNoBees(stack)) {
                    List<CustomData> bees = beeCatcherItem.getBees(stack);
                    List<CustomData> remaining = new ArrayList<>(bees);


                    if (!(bees.isEmpty()) && !level.isClientSide) {

                        while (!remaining.isEmpty()) {
                            CompoundTag tag = remaining.removeLast().copyTag();

                            Bee bee = EntityType.BEE.create(
                                    (ServerLevel) level,
                                    b -> b.load(tag),
                                    pos,
                                    MobSpawnType.BUCKET,
                                    true,
                                    false
                            );

                            if (bee != null) {
                                copperBeeosphereBlockEntity.addOccupant(bee);
                            }

                            stack.set(ModDataComponents.CAUGHT_BEES, null);
                        }
                }

                else {

                }
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}
