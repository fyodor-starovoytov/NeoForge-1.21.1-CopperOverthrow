package net.star.copperoverthrow.item.armor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.component.ModDataComponents;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MiningHelmArmorItem extends CrashHelmetArmorItem {
    private static final ResourceLocation TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "mining_helm");
    public MiningHelmArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }
    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TEXTURE;
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && player.getEquipmentSlotForItem(stack).equals(EquipmentSlot.HEAD)) {

            if (!level.isClientSide) {
                BlockPos startPos = BlockPos.containing(player.getEyePosition());
                BlockPos targetPos = getBlockLookedAt(player);

                List<BlockPos> prevPositions = stack.getOrDefault(ModDataComponents.TEMPORAL_BLOCKPOS_LIST, List.of());

                List<BlockPos> newPositions = new ArrayList<>();
                if (targetPos != null) {
                    newPositions = getVector(startPos, targetPos);
                }

                if (!newPositions.equals(prevPositions)) {

                    Set<BlockPos> oldSet = new HashSet<>(prevPositions);
                    Set<BlockPos> newSet = new HashSet<>(newPositions);

                    Set<BlockPos> toRemove = new HashSet<>(oldSet);
                    toRemove.removeAll(newSet);

                    Set<BlockPos> toAdd = new HashSet<>(newSet);
                    toAdd.removeAll(oldSet);

                    for (BlockPos pos : toRemove) {
                        if (level.getBlockState(pos).is(ModBlocks.TEMPORAL_LIGHT_BLOCK.get())) {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }

                    for (BlockPos pos : toAdd) {
                        if (level.getBlockState(pos).isAir()) {
                            level.setBlockAndUpdate(pos, ModBlocks.TEMPORAL_LIGHT_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(LightBlock.LEVEL, 14));
                        }
                    }

                    if (newPositions.isEmpty()) {
                        stack.remove(ModDataComponents.TEMPORAL_BLOCKPOS_LIST);
                    } else {
                        stack.set(ModDataComponents.TEMPORAL_BLOCKPOS_LIST, newPositions);
                    }
                }
            }
        }
    }

    public static List<BlockPos> getVector(BlockPos initial, BlockPos secondary) {
        List<BlockPos> vectorBlocks = new ArrayList<>();

        if (initial.distSqr(secondary) > ServerConfig.MEASURE_MAX_DISTANCE.get() * ServerConfig.MEASURE_MAX_DISTANCE.get()) {
            return vectorBlocks;
        }

        int x1 = initial.getX();
        int y1 = initial.getY();
        int z1 = initial.getZ();

        int x2 = secondary.getX();
        int y2 = secondary.getY();
        int z2 = secondary.getZ();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dz = Math.abs(z2 - z1);

        int xs = (x2 > x1) ? 1 : -1;
        int ys = (y2 > y1) ? 1 : -1;
        int zs = (z2 > z1) ? 1 : -1;

        if (dx >= dy && dx >= dz) {
            int p1 = 2 * dy - dx;
            int p2 = 2 * dz - dx;

            while (x1 != x2) {
                vectorBlocks.add(new BlockPos(x1, y1, z1));
                if (p1 >= 0) {
                    y1 += ys;
                    p1 -= 2 * dx;
                }
                if (p2 >= 0) {
                    z1 += zs;
                    p2 -= 2 * dx;
                }
                p1 += 2 * dy;
                p2 += 2 * dz;
                x1 += xs;
            }
        } else if (dy >= dx && dy >= dz) {
            int p1 = 2 * dx - dy;
            int p2 = 2 * dz - dy;

            while (y1 != y2) {
                vectorBlocks.add(new BlockPos(x1, y1, z1));
                if (p1 >= 0) {
                    x1 += xs;
                    p1 -= 2 * dy;
                }
                if (p2 >= 0) {
                    z1 += zs;
                    p2 -= 2 * dy;
                }
                p1 += 2 * dx;
                p2 += 2 * dz;
                y1 += ys;
            }
        } else {
            int p1 = 2 * dy - dz;
            int p2 = 2 * dx - dz;

            while (z1 != z2) {
                vectorBlocks.add(new BlockPos(x1, y1, z1));
                if (p1 >= 0) {
                    y1 += ys;
                    p1 -= 2 * dz;
                }
                if (p2 >= 0) {
                    x1 += xs;
                    p2 -= 2 * dz;
                }
                p1 += 2 * dy;
                p2 += 2 * dx;
                z1 += zs;
            }
        }
        vectorBlocks.add(new BlockPos(x2, y2, z2));

        return vectorBlocks;
    }

    private static BlockPos getBlockLookedAt(Player player){
        double reach = player.getAttributeBaseValue(Attributes.BLOCK_INTERACTION_RANGE) + 3;

        Vec3 startPos = player.getEyePosition(1.0f);
        Vec3 viewVector = player.getViewVector(1.0f);
        Vec3 endPos = startPos.add(viewVector.scale(reach));

        Level level = player.level();
        BlockHitResult traceResult = level.clip(new ClipContext(
                startPos,
                endPos,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        return traceResult.getType() == HitResult.Type.BLOCK ?
                traceResult.getBlockPos().relative(traceResult.getDirection())
                : BlockPos.containing(endPos);
    }
}

