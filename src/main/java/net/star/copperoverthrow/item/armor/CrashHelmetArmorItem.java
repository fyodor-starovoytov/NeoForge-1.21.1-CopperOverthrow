package net.star.copperoverthrow.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.attribute.ModAttributes;

import javax.annotation.Nullable;

public class CrashHelmetArmorItem extends AbstractExtendedArmorItem {
    private static final String TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "crash_helmet");
    public CrashHelmetArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public boolean isFullSetActive(LivingEntity living) {
        return false;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.LEGS) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(slot));

            builder.put(
                    ModAttributes.HEAD_IMPACT_RESISTANCE.get(),
                    new AttributeModifier(
                            "Head impact resistance",
                            ServerConfig.CRASH_HELMET_HEAD_IMPACT_RESISTANCE.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }
}
