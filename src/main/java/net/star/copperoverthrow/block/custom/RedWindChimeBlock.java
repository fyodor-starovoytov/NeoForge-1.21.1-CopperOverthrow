package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.block.entity.custom.KitchenBellBlockEntity;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;
import net.star.copperoverthrow.block.entity.custom.RedWindChimeBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class RedWindChimeBlock extends AbstractWindChimeBlock {

    public RedWindChimeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.WIND_CHIME_BE.get(), RedWindChimeBlockEntity::tick);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide()) return;

        BlockPos targetPos = state.getValue(UPPER) ? pos : pos.above();

        if (level.getBlockEntity(targetPos) instanceof RedWindChimeBlockEntity blockEntity) {
            if (!blockEntity.swinging) {
                double dx = entity.getX() - entity.xo;
                double dz = entity.getZ() - entity.zo;
                boolean entityIsMoving = (dx * dx + dz * dz) > 0.001D;

                if (entityIsMoving) {
                    blockEntity.startSwing(dx, dz);
                }
            }
        }
    }

    @Override
    protected void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (level.isClientSide()) return;

        BlockPos targetPos = state.getValue(UPPER) ? pos : pos.above();

        if (level.getBlockEntity(targetPos) instanceof RedWindChimeBlockEntity blockEntity) {
            if (!blockEntity.swinging) {
                    blockEntity.startSwing(explosion.radius()/10, explosion.radius()/10);
            }
        }
    }

    //
//    @Override
//    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//        AABB checkArea = new AABB(pos).inflate(1.0D, 1.0D, 1.0D);
//        BlockEntity be = level.getBlockEntity(pos);
//
//        if (be instanceof RedWindChimeBlockEntity blockEntity) {
//        if (!level.getEntities(null, checkArea).isEmpty()) {
//            for (Entity entity : level.getEntities(null, checkArea)) {
//
//                    System.out.println("Entity seen");
//                    double dx = entity.getX() - entity.xo;
//                    double dz = entity.getZ() - entity.zo;
//                    boolean entityIsMoving = (dx * dx + dz * dz) > 0.1D;
//
//                    if (entityIsMoving && !blockEntity.swinging) {
//                        System.out.println(dx + dz);
//                        blockEntity.startSwing(dx, dz);
//                        break;
//                    }
//                }
//            }
//        }
//    }
}