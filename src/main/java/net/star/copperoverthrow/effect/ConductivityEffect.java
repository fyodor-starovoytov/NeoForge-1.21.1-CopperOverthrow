package net.star.copperoverthrow.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.star.copperoverthrow.block.ModBlocks;

public class ConductivityEffect extends MobEffect{

    public static int CHANCE = 10000;

    public ConductivityEffect(MobEffectCategory category) {
        super(category, 0);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        int y = -2;
        int x = -1;
        int z = -2;

        Level level = livingEntity.level();
        BlockPos pos = livingEntity.getOnPos();

        for (z = -2; z < 3; z++) {
            for (x = -2; x < 3; x++) {
                for (y = -1; y < 5; y++) {
                    BlockState state = level.getBlockState(pos.relative(Direction.Axis.Y, y).relative(Direction.Axis.X, x).relative(Direction.Axis.Z, z));

                    if (state.getBlock().equals(Blocks.AIR) || state.getBlock().equals(ModBlocks.CONDUCT_BLOCK)) {
                        level.setBlockAndUpdate(pos.relative(Direction.Axis.Y, y).relative(Direction.Axis.X, x).relative(Direction.Axis.Z, z), ModBlocks.CONDUCT_BLOCK.get().defaultBlockState());
                    }
                }
            }
        }

        if (level.isThundering() && !level.isClientSide && pos.getY() == level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) - 1) {

            if (CHANCE_FOR_LIGHTNING_HIT() < 90){
                hitLightning(level, pos);
            }

        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private void hitLightning(Level level, BlockPos pos) {
        ServerLevel serverLevel = (ServerLevel)level;
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
        if (lightningBolt != null){
            lightningBolt.moveTo(Vec3.atBottomCenterOf(pos));
            serverLevel.addFreshEntity(lightningBolt);
        }
    }

    public static int CHANCE_FOR_LIGHTNING_HIT() {
        int random = (int) (Math.random() * (CHANCE - 1 + 1)) + 1;
        return random;
    }
}
