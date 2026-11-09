package net.star.copperoverthrow.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.star.copperoverthrow.util.ModBlockModifiers;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Optional;

public class CopperLanternBlock extends LanternBlock implements WeatheringCopper {

    private final WeatherState weatherState;

    public CopperLanternBlock(Properties properties, WeatherState weatherState) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.randomTick(state, level, pos, random);
    }

    @Override
    public @NotNull WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public Optional<BlockState> getNext(BlockState state) {
        return java.util.Optional.ofNullable(ModBlockModifiers.OXIDIZABLES.get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }
}