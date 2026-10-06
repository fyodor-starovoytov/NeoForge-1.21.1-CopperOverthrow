package net.star.copperoverthrow.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;
import net.star.copperoverthrow.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class LogStripperBlock extends BaseEntityBlock implements WeatheringCopper{
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public static final MapCodec<LogStripperBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    WeatheringCopper.WeatherState.CODEC.fieldOf("weather_state").forGetter(LogStripperBlock::getAge)
            ).apply(instance, LogStripperBlock::new)
    );

    private final WeatherState weatherState;

    public LogStripperBlock(Properties properties, WeatherState weatherState) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogStripperBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()){
            if (level.getBlockEntity(pos) instanceof LogStripperBlockEntity logStripperBlockEntity){
                Containers.dropContents(level, pos, logStripperBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof LogStripperBlockEntity logStripperBlockEntity)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ItemStack heldMain = player.getMainHandItem();
        ItemStack heldOff = player.getOffhandItem();

        EquipmentSlot slot = null;
        ItemStack axeStack = ItemStack.EMPTY;
        ItemStack sawStack = ItemStack.EMPTY;

        if (heldMain.is(ItemTags.AXES)) {
            slot = EquipmentSlot.MAINHAND;
            axeStack = heldMain;
        } else if (heldOff.is(ItemTags.AXES)) {
            slot = EquipmentSlot.OFFHAND;
            axeStack = heldOff;
        }
        if (heldMain.is(ModTags.Items.C_TOOLS_SAWS)) {
            slot = EquipmentSlot.MAINHAND;
            sawStack = heldMain;
        } else if (heldOff.is(ModTags.Items.C_TOOLS_SAWS)) {
            slot = EquipmentSlot.OFFHAND;
            sawStack = heldOff;
        }

        ItemStack inputStack = logStripperBlockEntity.getItem(0);

        // AXE STRIPPING
        if (!inputStack.isEmpty() && isBlockStrippable(inputStack) && !axeStack.isEmpty()) {
            int toDamage = (int) (inputStack.getCount() * ServerConfig.LOG_STRIPPER_TOOL_DAMAGE.get());
            axeStack.hurtAndBreak(Math.max(1, toDamage), player, slot);

            ItemStack resultStack = getStrippedBlock(inputStack);
            logStripperBlockEntity.setItem(0, resultStack);
            if (resultStack.getItem() instanceof BlockItem blockItem) {
                BlockState particleState = blockItem.getBlock().defaultBlockState();
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(particleState));
            }

            level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            return ItemInteractionResult.SUCCESS;
        }

        // SAW SAWING
        if (!inputStack.isEmpty() && isBlockPlank(inputStack) && !sawStack.isEmpty()) {
            int toDamage = (int) (inputStack.getCount() * ServerConfig.LOG_STRIPPER_TOOL_DAMAGE.get());
            sawStack.hurtAndBreak(Math.max(1, toDamage), player, slot);

            ItemStack resultStack = getFromPlankBlock(inputStack);
            int totalCount = resultStack.getCount();
            int maxStack = resultStack.getMaxStackSize(); // 64

            if (totalCount > maxStack) {
                logStripperBlockEntity.setItem(0, resultStack.copyWithCount(maxStack));

                if (!level.isClientSide()) {
                    int remaining = totalCount - maxStack;

                    while (remaining > 0) {
                        int dropCount = Math.min(remaining, maxStack);
                        ItemStack dropStack = resultStack.copyWithCount(dropCount);
                        Block.popResource(level, pos.above(), dropStack);
                        remaining -= dropCount;

                        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                }
            } else {
                logStripperBlockEntity.setItem(0, resultStack);
            }

            if (resultStack.getItem() instanceof BlockItem blockItem) {
                BlockState particleState = blockItem.getBlock().defaultBlockState();
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(particleState));
            }

            return ItemInteractionResult.SUCCESS;
        }

        // INSERT ITEM INTO EMPTY STRIPPER
        if (logStripperBlockEntity.isEmpty() && !stack.isEmpty() && stack.getItem() instanceof BlockItem) {
            logStripperBlockEntity.setItem(0, stack.copy());
            stack.setCount(0);

            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 2.0f);

            return ItemInteractionResult.SUCCESS;
        }

        // REMOVE ITEM FROM STRIPPER
        if ((player.getInventory().getFreeSlot() != -1)) {
            if (!logStripperBlockEntity.isEmpty()) {
                ItemStack stackInside = logStripperBlockEntity.getItem(0);

                if (!player.getInventory().add(stackInside)) {
                    player.drop(stackInside, false);
                }

                logStripperBlockEntity.clearContent();

                level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public ItemStack getFromPlankBlock(ItemStack stack) {
        int count = stack.getCount();
        ResourceLocation plankId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String path = plankId.getPath();
        String resultPath = null;
        if (path.contains("_log") || path.contains("_stem")) {
            resultPath = path.replace("stripped_", "").replace("_log", "_planks").replace("_stem", "_planks");
            count = count * 4;
        } else if (path.contains("_wood")) {
            resultPath = path.replace("stripped_", "").replace("_wood", "_planks");
            count = count * 4;
        } else if (path.contains("_planks")) {
            resultPath = path.replace("_planks", "_stairs");
        } else if (path.contains("_stairs")) {
            resultPath = path.replace("_stairs", "_slab");
            count = count * 2;
        }

        if (resultPath != null) {
            ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(plankId.getNamespace(), resultPath);
            Item resultBlock = BuiltInRegistries.ITEM.get(blockId);

            if (resultBlock != Items.AIR) {
                return new ItemStack(resultBlock, count);
            }
        }

        return stack;
    }

    public ItemStack getStrippedBlock(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            int count = stack.getCount();

            BlockState state = blockItem.getBlock().defaultBlockState();
            Holder<Block> blockHolder = state.getBlockHolder();
            Strippable strippableData = blockHolder.getData(NeoForgeDataMaps.STRIPPABLES);

            if (strippableData != null) {
                return new ItemStack(strippableData.strippedBlock().asItem(), count);
            }

            ResourceLocation plankId = BuiltInRegistries.ITEM.getKey(stack.getItem());
            String path = plankId.getPath();

            if (path.contains("_log") && !path.startsWith("stripped_")) {
                ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(
                        plankId.getNamespace(),
                        "stripped_" + path
                );
                Item resultBlock = BuiltInRegistries.ITEM.get(blockId);

                if (resultBlock != Items.AIR) {
                    return new ItemStack(resultBlock, count);
                }
            }
        }

        return stack;
    }

    public boolean isBlockStrippable(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            BlockState state = blockItem.getBlock().defaultBlockState();
            Holder<Block> blockHolder = state.getBlockHolder();
            Strippable strippableData = blockHolder.getData(NeoForgeDataMaps.STRIPPABLES);

            if (strippableData != null) {
                return true;
            }

            ResourceLocation plankId = BuiltInRegistries.ITEM.getKey(stack.getItem());
            String path = plankId.getPath();
            if (path.contains("_log")) {
                ResourceLocation blockId = ResourceLocation.fromNamespaceAndPath(
                        plankId.getNamespace(),
                        "stripped_" + path
                );
                return BuiltInRegistries.ITEM.get(blockId) != Items.AIR;
            }
        }

        return false;
    }

    public boolean isBlockPlank(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            BlockState state = blockItem.getBlock().defaultBlockState();
            return state.is(BlockTags.PLANKS) || state.is(BlockTags.WOODEN_STAIRS) || state.is(BlockTags.LOGS);
        }
        return false;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.changeOverTime(state, level, pos, random);
    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.when_placed.tooltip"));
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.log_stripper_block.tooltip"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

