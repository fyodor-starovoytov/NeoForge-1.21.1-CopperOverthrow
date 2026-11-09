package net.star.copperoverthrow.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.util.ModBlockModifiers;

@Mod.EventBusSubscriber(modid = CopperOverthrow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEvents {

    @SubscribeEvent
    public static void onToolModify(BlockEvent.BlockToolModificationEvent event) {
        BlockState state = event.getState();

        if (event.getToolAction() == ToolActions.AXE_SCRAPE) {
            Block previous = ModBlockModifiers.PREVIOUS_BY_BLOCK.get(state.getBlock());
            if (previous != null) {
                event.setFinalState(previous.withPropertiesOf(state));
            }
        }

        if (event.getToolAction() == ToolActions.AXE_WAX_OFF) {
            Block unwaxed = ModBlockModifiers.UNWAXABLES.get(state.getBlock());
            if (unwaxed != null) {
                event.setFinalState(unwaxed.withPropertiesOf(state));
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        if (stack.is(Items.HONEYCOMB)) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            Block waxed = ModBlockModifiers.WAXABLES.get(state.getBlock());

            if (waxed != null) {
                Player player = event.getEntity();

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                }

                player.swing(event.getHand());
                level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3003, pos, 0); // Spawns the green wax particles

                if (!level.isClientSide) {
                    level.setBlock(pos, waxed.withPropertiesOf(state), 11);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
}