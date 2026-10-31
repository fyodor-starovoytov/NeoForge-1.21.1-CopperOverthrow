package net.star.copperoverthrow.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.star.copperoverthrow.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;


public class FireflyEntity extends Animal implements FlyingAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    private float idleAnimationTimeout = 0;

    public FireflyEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.GLOW_BERRIES);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 4));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, stack -> stack.is(Items.GLOW_BERRIES), false));

        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(4, new FireflyWanderGoal());
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }

        };
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.FLYING_SPEED, 2.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.05F)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.FIREFLY.get().create(level);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    private void jumpInLiquidInternal() {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.01, 0.0));
    }

    @Override
    public void jumpInFluid(net.neoforged.neoforge.fluids.FluidType type) {
        this.jumpInLiquidInternal();
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.5F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.2F));
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 45.8f;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }


    class FireflyPanicGoal extends PanicGoal {
        public FireflyPanicGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        @Override
        public boolean isRunning() {
            return super.isRunning();
        }
    }

    class FireflyWanderGoal extends Goal {
        FireflyWanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return FireflyEntity.this.navigation.isDone() && FireflyEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return FireflyEntity.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                FireflyEntity.this.navigation.moveTo(FireflyEntity.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        }

        @javax.annotation.Nullable
        private Vec3 findPos() {
            Vec3 vec3 = FireflyEntity.this.getViewVector(0.0F);

            Vec3 vec32 = HoverRandomPos.getPos(FireflyEntity.this, 20, 7, vec3.x, vec3.z, (float) (Math.PI / 2), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(FireflyEntity.this, 20, 4, -2, vec3.x, vec3.z, (float) (Math.PI / 2));
        }
    }
}
