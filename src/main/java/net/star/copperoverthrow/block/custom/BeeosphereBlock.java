package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.star.copperoverthrow.block.entity.custom.BeeosphereBlockEntity;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BeeosphereBlock extends BaseEntityBlock {

    public static final MapCodec<BeeosphereBlock> CODEC = simpleCodec(BeeosphereBlock::new);


    public BeeosphereBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BeeosphereBlockEntity(pos, state);
    }


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof BeeosphereBlockEntity beeosphereBlockEntity){


            EquipmentSlot slot = null;
            ItemStack beeCatcherStack = ItemStack.EMPTY;

            if (player.getMainHandItem().is(ModTags.Items.C_TOOLS_CATCHERS)) {
                slot = EquipmentSlot.MAINHAND;
                beeCatcherStack = player.getMainHandItem();
            } else if (player.getOffhandItem().is(ModTags.Items.C_TOOLS_CATCHERS)) {
                slot = EquipmentSlot.OFFHAND;
                beeCatcherStack = player.getOffhandItem();
            }

            if (beeosphereBlockEntity.getAmountOfFreeSlots() != 0 && !beeCatcherStack.isEmpty()){
                List<CustomData> bees = beeCatcherStack.getOrDefault(ModDataComponents.CAUGHT_BEES, List.of());
                List<CustomData> remaining = new ArrayList<>(bees);

                if (!bees.isEmpty()) {
                    while (!remaining.isEmpty()) {
                        CompoundTag tag = remaining.removeLast().copyTag();

                        beeosphereBlockEntity.saveData(CustomData.of(tag));
                        }}
                }
            beeCatcherStack.set(ModDataComponents.CAUGHT_BEES, null);
            }
        return ItemInteractionResult.SUCCESS;
    }
}
