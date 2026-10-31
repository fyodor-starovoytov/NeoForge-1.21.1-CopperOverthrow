package net.star.copperoverthrow.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.block.custom.MultiBlockTallDecoration;
import net.star.copperoverthrow.block.custom.TamTamBlock;
import net.star.copperoverthrow.enchantment.ModEnchantments;
import net.star.copperoverthrow.entity.ModEntities;
import net.star.copperoverthrow.entity.custom.FireflyEntity;
import net.star.copperoverthrow.item.custom.HammerItem;
import net.star.copperoverthrow.sound.ModSounds;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = ConcurrentHashMap.newKeySet();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player.level().isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof HammerItem hammer) {
            BlockPos initialBlockPos = event.getPos();

            if (!HARVESTED_BLOCKS.add(initialBlockPos)) {
                return;
            }

            try {
                int area = 1;
                int deep = 0;

                Holder<Enchantment> arealHolder = event.getLevel().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ModEnchantments.AREAL);

                Holder<Enchantment> tunnelingHolder = event.getLevel().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ModEnchantments.TUNNELING);

                if (mainHandItem.getEnchantmentLevel(arealHolder) != 0) {
                    area = 2;
                }
                if (mainHandItem.getEnchantmentLevel(tunnelingHolder) != 0) {
                    deep = mainHandItem.getEnchantmentLevel(tunnelingHolder);
                }

                for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(area, deep, initialBlockPos, serverPlayer)) {
                    if (pos.equals(initialBlockPos) || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos)) || hasOreTag(event, pos)) {
                        continue;
                    }

                    if (HARVESTED_BLOCKS.add(pos)) {
                        try {
                            serverPlayer.gameMode.destroyBlock(pos);
                        } finally {
                            HARVESTED_BLOCKS.remove(pos);
                        }
                    }
                }
            } finally {
                HARVESTED_BLOCKS.remove(initialBlockPos);
            }
        }
    }

    private static boolean hasOreTag(BlockEvent.BreakEvent event, BlockPos pos) {
        return event.getLevel().getBlockState(pos).is(Tags.Blocks.ORES);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
            Level level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);

            if (state.getBlock() instanceof MultiBlockTallDecoration drum) {
                ItemStack stack = event.getItemStack();

                if (stack.is(Items.HONEYCOMB)) {
                    Waxable waxable = state.getBlockHolder().getData(NeoForgeDataMaps.WAXABLES);

                    if (waxable != null) {
                        Player player = event.getEntity();

                        if (!level.isClientSide) {
                            Block waxedBlock = waxable.waxed();

                            if (player instanceof ServerPlayer serverPlayer) {
                                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                            }

                            if (!player.isCreative()) {
                                stack.shrink(1);
                            }

                            level.setBlock(pos, waxedBlock.defaultBlockState().setValue(MultiBlockTallDecoration.PART, state.getValue(MultiBlockTallDecoration.PART)), 3);
                            drum.propagateBlockChange(level, pos, state, waxedBlock);

                            level.levelEvent(null, 3003, pos, 0);
                        }

                        player.swing(event.getHand());

                        event.setCanceled(true);
                        event.setCancellationResult(ItemInteractionResult.sidedSuccess(level.isClientSide).result());
                    }
                }
            }

        if (state.getBlock() instanceof TamTamBlock plate) {
            ItemStack stack = event.getItemStack();

            if (stack.is(Items.HONEYCOMB)) {
                Waxable waxable = state.getBlockHolder().getData(NeoForgeDataMaps.WAXABLES);

                if (waxable != null) {
                    Player player = event.getEntity();

                    if (!level.isClientSide) {
                        Block waxedBlock = waxable.waxed();

                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                        }

                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }

                        level.setBlock(pos, waxedBlock.defaultBlockState().setValue(TamTamBlock.PART, state.getValue(TamTamBlock.PART)), 3);
                        plate.propagateBlockChange(level, pos, state, waxedBlock);

                        level.levelEvent(null, 3003, pos, 0);
                    }

                    player.swing(event.getHand());

                    event.setCanceled(true);
                    event.setCancellationResult(ItemInteractionResult.sidedSuccess(level.isClientSide).result());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHeadHit(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity victim) {

            ItemStack headStack = victim.getItemBySlot(EquipmentSlot.HEAD);
            for (var entry : headStack.getAttributeModifiers().modifiers()) {
                if (entry.modifier().is(ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "head_impact_resistance"))) {

                    float originalDamage = event.getOriginalDamage();
                    Vec3 sourcePos = event.getSource().getSourcePosition();
                    double deductible = entry.modifier().amount();

                    boolean isFallingBlockDamage = event.getSource().is(DamageTypes.FALLING_ANVIL)
                            || event.getSource().is(DamageTypes.FALLING_STALACTITE)
                            || event.getSource().is(DamageTypes.FALLING_BLOCK);

                    boolean isElytraCrash = event.getSource().is(DamageTypes.FLY_INTO_WALL);

                    boolean isMaceSmashFromAbove = sourcePos != null
                            && event.getSource().getWeaponItem() != null
                            && event.getSource().getWeaponItem().is(Items.MACE)
                            && sourcePos.y() >= victim.getY() + 1.11;

                    if (isFallingBlockDamage || isMaceSmashFromAbove || isElytraCrash) {
                        event.setNewDamage((float) (originalDamage * (1.0F - deductible)));
                        if (isMaceSmashFromAbove){
                            Level level = victim.level();
                            if (!level.isClientSide){
                                level.playSound(null, victim.getOnPos(), ModSounds.CRASH_HELMET_BONK.get(), SoundSource.PLAYERS);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FIREFLY.get(), FireflyEntity.createAttributes().build());
    }
}