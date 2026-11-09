package net.star.copperoverthrow.block.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.block.entity.custom.LogStripperBlockEntity;
import net.star.copperoverthrow.util.ModBlockModifiers;
import net.star.copperoverthrow.util.ModTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class LogStripperBlock extends BaseEntityBlock implements WeatheringCopper{
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    private final WeatherState weatherState;

    public LogStripperBlock(Properties properties, WeatherState weatherState) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    public VoxelShape getShape( BlockState state,  BlockGetter level,  BlockPos pos,  CollisionContext context) {
        return SHAPE;
    }


    @Override
    public BlockEntity newBlockEntity( BlockPos pos,  BlockState state) {
        return new LogStripperBlockEntity(pos, state);
    }

    @Override
    public  RenderShape getRenderShape( BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state,  Level level,  BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()){
            if (level.getBlockEntity(pos) instanceof LogStripperBlockEntity logStripperBlockEntity){
                Containers.dropContents(level, pos, logStripperBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof LogStripperBlockEntity logStripperBlockEntity)) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(hand);
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
            if (slot != null) {
                EquipmentSlot finalSlot = slot;
                axeStack.hurtAndBreak(Math.max(1, toDamage), player, (p) -> p.broadcastBreakEvent(finalSlot));
            }

            ItemStack resultStack = getStrippedBlock(inputStack);
            logStripperBlockEntity.setItem(0, resultStack);
            if (resultStack.getItem() instanceof BlockItem blockItem) {
                BlockState particleState = blockItem.getBlock().defaultBlockState();
                level.levelEvent(2001, pos, Block.getId(particleState));
            }

            level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

        // SAW SAWING
        if (!inputStack.isEmpty() && isBlockPlank(inputStack) && !sawStack.isEmpty()) {
            int toDamage = (int) (inputStack.getCount() * ServerConfig.LOG_STRIPPER_TOOL_DAMAGE.get());
            if (slot != null) {
                EquipmentSlot finalSlot = slot;
                sawStack.hurtAndBreak(Math.max(1, toDamage), player, (p) -> p.broadcastBreakEvent(finalSlot));
            }

            ItemStack resultStack = getFromPlankBlock(inputStack);
            int totalCount = resultStack.getCount();
            int maxStack = resultStack.getMaxStackSize();

            if (totalCount > maxStack) {
                ItemStack setStack = resultStack.copy();
                setStack.setCount(maxStack);
                logStripperBlockEntity.setItem(0, setStack);

                if (!level.isClientSide) {
                    int remaining = totalCount - maxStack;

                    while (remaining > 0) {
                        int dropCount = Math.min(remaining, maxStack);
                        ItemStack dropStack = resultStack.copy();
                        dropStack.setCount(dropCount);
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
                level.levelEvent(2001, pos, Block.getId(particleState));
            }

            return InteractionResult.SUCCESS;
        }

        // INSERT ITEM INTO EMPTY STRIPPER
        if (logStripperBlockEntity.isEmpty() && !stack.isEmpty() && stack.getItem() instanceof BlockItem) {
            logStripperBlockEntity.setItem(0, stack.copy());
            stack.setCount(0);

            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 2.0f);

            return InteractionResult.SUCCESS;
        }

        // REMOVE ITEM FROM STRIPPER
        if (player.getInventory().getFreeSlot() != -1) {
            if (!logStripperBlockEntity.isEmpty()) {
                ItemStack stackInside = logStripperBlockEntity.getItem(0);

                if (!player.getInventory().add(stackInside)) {
                    player.drop(stackInside, false);
                }

                logStripperBlockEntity.clearContent();

                level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
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
            ResourceLocation blockId = new ResourceLocation(plankId.getNamespace(), resultPath);
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
            BlockState strippedState = state.getToolModifiedState(null, ToolActions.AXE_STRIP, true);

            if (strippedState != null) {
                return new ItemStack(strippedState.getBlock().asItem(), count);
            }

            ResourceLocation plankId = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (plankId != null) {
                String path = plankId.getPath();

                if (path.contains("_log") && !path.startsWith("stripped_")) {
                    ResourceLocation blockId = new ResourceLocation(
                            plankId.getNamespace(),
                            "stripped_" + path
                    );

                    if (ForgeRegistries.ITEMS.containsKey(blockId)) {
                        Item resultBlock = ForgeRegistries.ITEMS.getValue(blockId);
                        if (resultBlock != null && resultBlock != Items.AIR) {
                            return new ItemStack(resultBlock, count);
                        }
                    }
                }
            }
        }
        return stack;
    }

    public boolean isBlockStrippable(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            BlockState state = blockItem.getBlock().defaultBlockState();

            BlockState strippedState = state.getToolModifiedState(null, ToolActions.AXE_STRIP, true);

            if (strippedState != null) {
                return true;
            }

            ResourceLocation plankId = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (plankId != null) {
                String path = plankId.getPath();
                if (path.contains("_log")) {
                    ResourceLocation blockId = new ResourceLocation(
                            plankId.getNamespace(),
                            "stripped_" + path
                    );
                    return ForgeRegistries.ITEMS.containsKey(blockId);
                }
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
    public void randomTick( BlockState state,  ServerLevel level,  BlockPos pos,  RandomSource random) {
        this.randomTick(state, level, pos, random);
    }

    @Override
    public  WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.copperoverthrow.when_placed.tooltip"));
            tooltip.add(Component.translatable("tooltip.copperoverthrow.log_stripper_block.tooltip"));
        }
        else {
            tooltip.add(Component.translatable("tooltip.copperoverthrow.press_shift.tooltip"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public Optional<BlockState> getNext(BlockState state) {
        return java.util.Optional.ofNullable(ModBlockModifiers.OXIDIZABLES.get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }
}

