package net.star.copperoverthrow.block.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HologramBlock extends Block {
    public static final BooleanProperty PLACED_BY_RULER = BooleanProperty.create("placed_by_ruler");

    public HologramBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PLACED_BY_RULER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PLACED_BY_RULER);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.@NotNull Builder builder) {
        if (state.getValue(PLACED_BY_RULER)) {
            return Collections.emptyList();
        }
        return super.getDrops(state, builder);
    }
}


