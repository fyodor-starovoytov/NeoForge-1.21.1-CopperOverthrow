package net.star.copperoverthrow.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.star.copperoverthrow.CopperOverthrow;

import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.model.GlowingGogglesModel;
import net.star.copperoverthrow.item.armor.client.provider.ArmorModelProvider;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

import javax.annotation.Nullable;

public class GlowingGogglesArmorItem extends AbstractExtendedArmorItem {
    private static final String TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "copper_glowing_goggles");
    public GlowingGogglesArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }
    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE;
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(slot));

            builder.put(
                    ModAttributes.NIGHT_VISION.get(),
                    new AttributeModifier(
                            "Night vision modifier",
                            1.0,
                            AttributeModifier.Operation.ADDITION
                    )
            );

            return builder.build();
        }

        return super.getDefaultAttributeModifiers(slot);
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

    @Override
    protected boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(GlowingGogglesModel::createBodyLayer, GlowingGogglesModel::new);
    }
}

