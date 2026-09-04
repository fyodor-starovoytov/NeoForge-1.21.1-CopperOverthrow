package net.star.copperoverthrow.event;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.enchantment.ModEnchantments;
import net.star.copperoverthrow.item.custom.CopperTrowelItem;
import net.star.copperoverthrow.item.custom.HammerItem;

import javax.swing.event.TreeExpansionEvent;
import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {

if (Screen.hasShiftDown()){return;}

        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {

            int area = 1;
            int deep = 0;

            Holder<Enchantment> arealHolder = event.getLevel().registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(ModEnchantments.AREAL);

            Holder<Enchantment> tunnelingHolder = event.getLevel().registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(ModEnchantments.TUNNELING);

            if (mainHandItem.getEnchantmentLevel(arealHolder) != 0){
                area = 2;
            }
            if (mainHandItem.getEnchantmentLevel(tunnelingHolder) != 0){
                deep = mainHandItem.getEnchantmentLevel(tunnelingHolder);
            }

            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(area, deep, initialBlockPos, serverPlayer)) {

                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos)) || hasOreTag(event, pos)) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    private static boolean hasOreTag(BlockEvent.BreakEvent event, BlockPos pos){
        return event.getLevel().getBlockState(pos).is(Tags.Blocks.ORES);

    }
/*
    @SubscribeEvent
    public static void onTrowelUsage(BlockEvent.EntityPlaceEvent event) {

        Entity entity = event.getEntity();

        if (entity instanceof Player player) {

            ItemStack mainHandItem = player.getMainHandItem();

            if(mainHandItem.getItem() instanceof CopperTrowelItem hammer && player instanceof ServerPlayer serverPlayer) {

                BlockPos initialBlockPos = event.getPos();
                BlockState state = CopperTrowelItem.getRandomBlock()
                if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                    return;
                }

                for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(area, deep, initialBlockPos, serverPlayer)) {

                    if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos)) || hasOreTag(event, pos)) {
                        continue;
                    }

                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                }
            }
        }
    }

 */
}