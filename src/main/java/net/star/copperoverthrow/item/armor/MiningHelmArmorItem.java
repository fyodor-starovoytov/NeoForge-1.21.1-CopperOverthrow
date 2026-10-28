package net.star.copperoverthrow.item.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.custom.HologramBlock;
import org.jetbrains.annotations.Nullable;

public class MiningHelmArmorItem extends CrashHelmetArmorItem {
    private static final ResourceLocation TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "mining_helm");
    public MiningHelmArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }
    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TEXTURE;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && player.getEquipmentSlotForItem(stack).equals(EquipmentSlot.HEAD)){

            if(!level.isClientSide && level.getBlockState(getBlockLookedAt(player, 10f)).isAir()){
                level.setBlockAndUpdate(getBlockLookedAt(player, 10f), ModBlocks.TEMPORAL_LIGHT_BLOCK.get().defaultBlockState().setValue(LightBlock.LEVEL, 11));
            }
            if(!level.isClientSide && level.getBlockState(getBlockLookedAt(player, 8f)).isAir()){
                level.setBlockAndUpdate(getBlockLookedAt(player, 8f), ModBlocks.TEMPORAL_LIGHT_BLOCK.get().defaultBlockState().setValue(LightBlock.LEVEL, 9));
            }
            if(!level.isClientSide && level.getBlockState(getBlockLookedAt(player, 5f)).isAir()){
                level.setBlockAndUpdate(getBlockLookedAt(player, 5f), ModBlocks.TEMPORAL_LIGHT_BLOCK.get().defaultBlockState().setValue(LightBlock.LEVEL, 8));
            }
            if(!level.isClientSide && level.getBlockState(getBlockLookedAt(player, 3f)).isAir()){
                level.setBlockAndUpdate(getBlockLookedAt(player, 3f), ModBlocks.TEMPORAL_LIGHT_BLOCK.get().defaultBlockState().setValue(LightBlock.LEVEL, 6));
            }
            if(!level.isClientSide && level.getBlockState(getBlockLookedAt(player, 1f)).isAir()){
                level.setBlockAndUpdate(getBlockLookedAt(player, 1f), ModBlocks.TEMPORAL_LIGHT_BLOCK.get().defaultBlockState().setValue(LightBlock.LEVEL, 3));
            }
        }
    }


    private static BlockPos getBlockLookedAt(Player player, float factor){
        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(2f),
                (player.getEyePosition(2f).add(player.getViewVector(2f).scale(factor))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        BlockPos secondaryPos = traceResult.getBlockPos();

        return secondaryPos;
    }

}
