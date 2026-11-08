package net.star.copperoverthrow.event;

import com.google.common.collect.Multimap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.block.custom.MultiBlockTallDecoration;
import net.star.copperoverthrow.block.custom.TamTamBlock;
import net.star.copperoverthrow.enchantment.ModEnchantments;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.item.custom.HammerItem;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = CopperOverthrow.MOD_ID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = ConcurrentHashMap.newKeySet();

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();

        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.COPPER_EXOSKELETON.get())) {

            float originalSpeed = event.getOriginalSpeed();
            float bonus = ServerConfig.EXOSKELETON_BLOCK_BREAK_SPEED.get().floatValue();

            event.setNewSpeed(originalSpeed + bonus);
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ModItems.COPPER_STEPPER_BOOTS.get())) {

            float currentFallDistance = event.getDistance();
            float safeDistanceBonus = ServerConfig.STEPPER_BOOTS_SAFE_FALL_DISTANCE.get().floatValue();

            float newFallDistance = Math.max(0.0F, currentFallDistance - safeDistanceBonus);

            event.setDistance(newFallDistance);
        }
    }

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

                if (mainHandItem.getEnchantmentLevel(arealHolder.get()) != 0) {
                    area = 2;
                }
                if (mainHandItem.getEnchantmentLevel(tunnelingHolder.get()) != 0) {
                    deep = mainHandItem.getEnchantmentLevel(tunnelingHolder.get());
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
                    //ffas
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
    public static void onHeadHit(LivingHurtEvent event) {
        LivingEntity victim = event.getEntity();
        ItemStack headStack = victim.getItemBySlot(EquipmentSlot.HEAD);
        Multimap<Attribute, AttributeModifier> modifiers = headStack.getAttributeModifiers(EquipmentSlot.HEAD);

        if (modifiers.containsKey(ModAttributes.HEAD_IMPACT_RESISTANCE.get())) {
            for (AttributeModifier modifier : modifiers.get(ModAttributes.HEAD_IMPACT_RESISTANCE.get())) {

                float originalDamage = event.getAmount();
                double deductible = modifier.getAmount();

                boolean isFallingBlockDamage = event.getSource().is(DamageTypes.FALLING_ANVIL)
                        || event.getSource().is(DamageTypes.FALLING_STALACTITE)
                        || event.getSource().is(DamageTypes.FALLING_BLOCK);

                boolean isElytraCrash = event.getSource().is(DamageTypes.FLY_INTO_WALL);

                if (isFallingBlockDamage || isElytraCrash) {
                    event.setAmount((float) (originalDamage * (1.0F - deductible)));

                }
            }
        }
    }
}