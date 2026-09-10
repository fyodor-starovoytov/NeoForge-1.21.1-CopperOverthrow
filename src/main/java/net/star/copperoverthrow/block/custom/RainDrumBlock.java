package net.star.copperoverthrow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.sound.ModSounds;

public class RainDrumBlock extends Block {
    private final float pitch;
    private final float volume;
    private final float soungProbability = 0.10f;

    public RainDrumBlock(Properties properties, float pitch, float volume) {
        super(properties);
        this.pitch = pitch;
        this.volume = volume;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isRainingAt(pos.above())) {
            if (random.nextFloat() < soungProbability) {
                float floatPitch = Math.min(10 , Math.max (0.85f , random.nextFloat() * randomFloat() + pitch));

                level.playLocalSound(
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        ModSounds.RAIN_DRUM_B1.get(),
                        SoundSource.RECORDS,
                        volume,
                        floatPitch,
                        false
                );
            }
        }
    }

    //ДОБАВЬ ВЗАИМОДЕЙСТВИЕ С КАПЕЛЬНИКОМ

    private float randomFloat(){
        if (Math.random() < 0.51){
            return -0.5f;
        }
        return 0.5f;
    }

}
