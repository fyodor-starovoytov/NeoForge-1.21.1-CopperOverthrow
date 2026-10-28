package net.star.copperoverthrow.block.custom;

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
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.block.entity.custom.CopperBeeosphereBlockEntity;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.item.custom.BeeCatcherItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CopperBeeosphereBlock extends BeehiveBlock {
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public CopperBeeosphereBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new CopperBeeosphereBlockEntity(pos, state);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
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
