package net.star.copperoverthrow.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.star.copperoverthrow.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;


public class FireflyEntity extends AbstractNocturnalAnimal implements FlyingAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    private float idleAnimationTimeout = 0;

    public FireflyEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.GLOW_BERRIES);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FireflyPanicGoal(this, 1.5));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, stack -> stack.is(Items.GLOW_BERRIES), false));
        this.goalSelector.addGoal(4, new FlirtGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));

        this.goalSelector.addGoal(6, new FloatGoal(this));
        this.goalSelector.addGoal(7, new FireflyWanderGoal());
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
                .add(Attributes.ATTACK_DAMAGE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 0.03)
                .add(Attributes.FLYING_SPEED, 0.15F)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    public float getFlyingSpeed() {
        return (float) this.getAttributeValue(Attributes.FLYING_SPEED);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.FIREFLY.get().create(level);
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

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        Item item = itemstack.getItem();
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.igniteForSeconds(8.0F);
                }
            }
        }

        super.aiStep();
    }

    protected boolean isSunSensitive() {
        return true;
    }

    class FlirtGoal extends FollowParentGoal {

        @javax.annotation.Nullable
        private Animal partner;
        private final Animal animal;
        private final double speedModifier;
        private int timeToRecalcPath;

        public FlirtGoal(Animal animal, double speedModifier) {
            super(animal, speedModifier);
            this.animal = animal;
            this.speedModifier = speedModifier;
        }

        @Override
        public boolean canUse() {
            if (this.animal.getAge() < 0) {
                return false;
            } else {
                List<? extends Animal> list = this.animal
                        .level()
                        .getEntitiesOfClass((Class<? extends Animal>)this.animal.getClass(), this.animal.getBoundingBox().inflate(8.0, 4.0, 8.0));
                Animal nearestPartner = null;
                double nearestDistSqr = Double.MAX_VALUE;

                for (Animal potentialPartner : list) {

                    if (potentialPartner == this.animal) {
                        continue;
                    }

                    if (potentialPartner.getAge() >= 0 && potentialPartner.isAlive()) {
                        double distSqr = this.animal.distanceToSqr(potentialPartner);
                        if (distSqr < nearestDistSqr) {
                            nearestDistSqr = distSqr;
                            nearestPartner = potentialPartner;
                        }
                    }
                }

                if (nearestPartner == null) {
                    return false;
                }

                if (isPanicking()){
                    return false;
                }

                this.partner = nearestPartner;
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            if (this.animal.getAge() < 0) {
                return false;
            }
            if (this.partner == null || !this.partner.isAlive()) {
                return false;
            }

            if (isPanicking()){
                return false;
            }

            double distSqr = this.animal.distanceToSqr(this.partner);
            return distSqr <= 256.0;

        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
        }

        @Override
        public void stop() {
            this.partner = null;
        }

        @Override
        public void tick() {
            if (this.partner == null) return;

            this.animal.getLookControl().setLookAt(this.partner, 5.0F, (float) this.animal.getMaxHeadXRot());

            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(5);

                double distSqr = this.animal.distanceToSqr(this.partner);

                if (distSqr > 10.0) {
                    this.animal.getNavigation().moveTo(this.partner, this.speedModifier);
                } else {

                    double angle = (this.animal.tickCount * 0.2) + (this.animal.getId() % 12);
                    double offsetX = Math.cos(angle) * 3.5;
                    double offsetZ = Math.sin(angle) * -3.5;

                    this.animal.getNavigation().moveTo(
                            this.partner.getX() + offsetX,
                            this.partner.getY(),
                            this.partner.getZ() + offsetZ,
                            this.speedModifier
                    );
                }
            }
        }
    }

    class FireflyPanicGoal extends PanicGoal {
        public FireflyPanicGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        @Override
        public boolean isInterruptable() {
            return false;
        }

        @Override
        protected boolean findRandomPosition() {
            Vec3 vec3 = FireflyEntity.this.getViewVector(0.0F);

            Vec3 vec32 = HoverRandomPos.getPos(this.mob, 20, 7, vec3.x, vec3.z, (float) (Math.PI / 2), 3, 1);

            if (vec32 == null) {
                vec32 = AirAndWaterRandomPos.getPos(this.mob, 20, 4, -2, vec3.x, vec3.z, (float) (Math.PI / 2));
            }

            if (vec32 == null) {
                return false;
            }

            this.posX = vec32.x;
            this.posY = vec32.y;
            this.posZ = vec32.z;

            return true;
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
