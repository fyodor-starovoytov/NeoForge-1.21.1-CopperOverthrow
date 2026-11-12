package net.star.copperoverthrow.item.armor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.attribute.ModAttributes;
import org.jetbrains.annotations.Nullable;

public class GlowingGogglesArmorItem extends AbstractExtendedArmorItem {
    private static final ResourceLocation TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "copper_glowing_goggles");
    public GlowingGogglesArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }
    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return TEXTURE;
    }


    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {

        ItemAttributeModifiers base = super.getDefaultAttributeModifiers();

        return base
                .withModifierAdded(
                        ModAttributes.NIGHT_VISION,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "night_vision"),
                                1,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.HEAD
                );
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) {
            return;
        }

        if (entity instanceof LivingEntity livingEntity ) {
            ItemStack headItem = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
            if (ItemStack.matches(headItem, stack)) {

                int currentLight = getLight(livingEntity);

                if (currentLight >= 13) {
                    MobEffectInstance blindness = new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false, false);
                    livingEntity.addEffect(blindness);
                } else {
                    MobEffectInstance nightVision = new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, false);
                    livingEntity.addEffect(nightVision);
                }
            }
        }
    }

    public int getLight(Entity entity) {
        Level level = entity.level();
        BlockPos pos = entity.blockPosition();

        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int rawSkyLight = level.getBrightness(LightLayer.SKY, pos);

        int skySubtract = level.getSkyDarken();

        if (level.dimensionType().hasSkyLight()) {
            skySubtract = Math.max(skySubtract, level.getSkyDarken());
        }

        int actualSkyLight = Math.max(0, rawSkyLight - skySubtract);

        return Math.max(blockLight, actualSkyLight);
    }
}

