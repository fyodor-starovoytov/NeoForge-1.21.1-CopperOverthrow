package net.star.copperoverthrow.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractNocturnalAnimal extends Animal {
    protected AbstractNocturnalAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean checkNocturnalSpawnRules(EntityType<? extends Animal> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean isBaseValid = Animal.checkAnimalSpawnRules(entityType, level, spawnType, pos, random);

        boolean flag = MobSpawnType.ignoresLightRequirements(spawnType) || isEmptyMoon(level);

        return isBaseValid && flag;
    }

    protected static boolean isEmptyMoon(LevelAccessor level) {
        if (level instanceof Level actualLevel) {
            boolean isEmptyMoon = actualLevel.getMoonPhase() == 4;
            boolean isNight = actualLevel.isNight();

            return isEmptyMoon && isNight;
        }
        return false;
    }
}
