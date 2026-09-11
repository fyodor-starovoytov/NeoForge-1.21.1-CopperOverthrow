package net.star.copperoverthrow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.star.copperoverthrow.sound.ModSounds;

public class RainDrumBlockTall extends ThreeBlockTallDecoration {
    private final float PITCH;
    private final float VOLUME;
    private final double X1_Z1;
    private final double X2_Z2;
    private final VoxelShape SHAPE;
    private final float SOUND_PROBABILITY = 0.1f;
    private final float THUNDER_SOUND_PROBABILITY_ADDITION = 0.05f;
    private final float THUNDER_VOLUME_ADDITION = 0.5f;
    private final int MAX_DRIPSTONE_HEIGHT_CHECK = 11;


    public RainDrumBlockTall(Properties properties, float pitch, float volume, double x1_z1, double x2_z2) {
        super(properties);
        this.PITCH = pitch;
        this.VOLUME = volume;
        this.X1_Z1 = x1_z1;
        this.X2_Z2 = x2_z2;
        this.SHAPE = Block.box(X1_Z1, 0, X1_Z1, X2_Z2, 16, X2_Z2);
    }



    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isRainingAt(pos.above())) {

            float finalSoundProbality = SOUND_PROBABILITY;
            float finalVolume = VOLUME;
            if (level.isThundering()){
                finalSoundProbality = SOUND_PROBABILITY + THUNDER_SOUND_PROBABILITY_ADDITION;
                finalVolume = VOLUME + THUNDER_VOLUME_ADDITION;
            }

            if (random.nextFloat() < finalSoundProbality) {
                float finalPitch = Math.min(10 , Math.max (0.85f , random.nextFloat() * randomFloat() + PITCH));
                        level.playLocalSound(pos,
                                ModSounds.RAIN_DRUM_RAINING.get(),
                                SoundSource.RECORDS,
                                finalVolume,
                                finalPitch,
                                false
                        );
            }
        }

        checkAndPlayDripstoneSound(level, pos, random);
    }

    private void checkAndPlayDripstoneSound(Level level, BlockPos pos, RandomSource random) {


        for (int i = 1; i <= MAX_DRIPSTONE_HEIGHT_CHECK; i++) {
            BlockPos checkPos = pos.above(i);
            BlockState checkState = level.getBlockState(checkPos);

            if (checkState.is(Blocks.POINTED_DRIPSTONE)) {
                if (checkState.getValue(PointedDripstoneBlock.TIP_DIRECTION) == Direction.DOWN) {

                    Fluid fluid = getDripstoneFluid(level, pos);
                    boolean isLava = fluid instanceof LavaFluid;

                    if (fluid != Fluids.EMPTY) {
                        if (random.nextFloat() < 0.05f) {
                            float finalPitch = Math.min(2.0f, Math.max(0.85f, PITCH + (random.nextFloat() - 0.5f) * 0.2f));
                            level.playLocalSound(
                                    pos,
                                    ModSounds.RAIN_DRUM_PLAYING.get(),
                                    SoundSource.RECORDS,
                                    VOLUME,
                                    finalPitch,
                                    false
                            );

                            if (isLava) {
                                level.playLocalSound(
                                        pos,
                                        SoundEvents.LAVA_EXTINGUISH,
                                        SoundSource.RECORDS,
                                        0.42f,
                                        finalPitch,
                                        false
                                );
                            }
                        }
                    }
                }
                break;
            } else if (!checkState.isAir()) {
                break;
            }
        }
    }

    private Fluid getDripstoneFluid(Level level, BlockPos drumPos) {
        for (int i = 1; i <= MAX_DRIPSTONE_HEIGHT_CHECK; i++) {
            BlockPos checkPos = drumPos.above(i);
            BlockState checkState = level.getBlockState(checkPos);

            if (checkState.is(Blocks.POINTED_DRIPSTONE)) {
                if (checkState.getValue(PointedDripstoneBlock.TIP_DIRECTION) == Direction.DOWN) {
                    BlockPos rootPos = checkPos;
                    while (level.getBlockState(rootPos).is(Blocks.POINTED_DRIPSTONE)) {
                        rootPos = rootPos.above();
                    }

                    BlockPos liquidPos = rootPos.above(1);

                    Fluid fluid = level.getFluidState(liquidPos).getType();
                    if (fluid != Fluids.EMPTY) {
                        return fluid;
                    }
                }
                break;
            } else if (!checkState.isAir()) {
                break;
            }
        }
        return Fluids.EMPTY;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (!level.isClientSide){
            level.playSound( null,
                    pos,
                    ModSounds.RAIN_DRUM_PLAYING.get(),
                    SoundSource.RECORDS,
                    VOLUME,
                    PITCH
            );
        }
        return InteractionResult.SUCCESS;
    }

    private float randomFloat(){
        if (Math.random() < 0.51){
            return -0.5f;
        }
        return 0.5f;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!level.isClientSide){
            level.playSound( null,
                    pos,
                    ModSounds.RAIN_DRUM_PLAYING.get(),
                    SoundSource.RECORDS,
                    VOLUME,
                    PITCH
            );
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide){
            level.playSound( null,
                    pos,
                    ModSounds.RAIN_DRUM_PLAYING.get(),
                    SoundSource.RECORDS,
                    VOLUME,
                    PITCH
            );
        }
    }
}
