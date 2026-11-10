package net.star.copperoverthrow.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.star.copperoverthrow.util.ModTags;

import java.util.ArrayList;
import java.util.List;

public class CrowbarItem extends DiggerItem {
    public CrowbarItem(Tier tier, Properties properties) {
        super(tier, ModTags.Blocks.MINEABLE_WITH_CROWBAR, properties);
    }
}