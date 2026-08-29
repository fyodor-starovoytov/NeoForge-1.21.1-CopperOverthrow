package net.star.copperoverthrow.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.star.copperoverthrow.util.ModTags;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, Properties properties) {
        super(tier, ModTags.Blocks.MINEABLE_WITH_HAMMER, properties);
    }

    public static List<BlockPos> getBlocksToBeDestroyed(int range, int deep, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if(traceResult.getDirection() == Direction.UP) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = -deep; z <= 0; z++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + z, initalBlockPos.getZ() + y));

                    }
                }
            }
        }

        if(traceResult.getDirection() == Direction.DOWN) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = 0; z <= deep; z++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + z, initalBlockPos.getZ() + y));

                    }
                }
            }
        }

        if(traceResult.getDirection() == Direction.SOUTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = -deep; z <= 0; z++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ() + z));
                    }
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = 0; z <= deep; z++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ() + z));
                    }
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = -deep; z <= 0; z++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + z, initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                    }
                }
            }
        }

        if(traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    for (int z = 0; z <= deep; z++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + z, initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                    }
                }
            }
        }

        return positions;
    }
}