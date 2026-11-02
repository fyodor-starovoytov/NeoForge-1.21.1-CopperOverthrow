package net.star.copperoverthrow.item.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.star.copperoverthrow.client.DynamicLightManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

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

            if (level.isClientSide) {
                BlockPos pos = getBlockLookedAt(player);

                if (pos != null) {
                    DynamicLightManager.setLight(pos, 15);
                }
            }
        }
    }

    private static BlockPos getBlockLookedAt(Player player){
        double reach = player.getAttributeBaseValue(Attributes.BLOCK_INTERACTION_RANGE);

        Vec3 startPos = player.getEyePosition(1.0f);
        Vec3 viewVector = player.getViewVector(1.0f);
        Vec3 endPos = startPos.add(viewVector.scale(reach + 1));

        Level level = player.level();
        BlockHitResult traceResult = level.clip(new ClipContext(
                startPos,
                endPos,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        return traceResult.getType() == HitResult.Type.BLOCK ?
                traceResult.getBlockPos().relative(traceResult.getDirection())
                : null;
    }
}

