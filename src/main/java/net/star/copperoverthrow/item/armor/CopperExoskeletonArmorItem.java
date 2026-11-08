package net.star.copperoverthrow.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.item.armor.client.model.CopperExoskeletonModel;
import net.star.copperoverthrow.item.armor.client.model.CrashHelmetModel;
import net.star.copperoverthrow.item.armor.client.provider.ArmorModelProvider;
import net.star.copperoverthrow.item.armor.client.provider.SimpleModelProvider;

import javax.annotation.Nullable;

public class CopperExoskeletonArmorItem extends AbstractExtendedArmorItem {
    private static final String TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "copper_exoskeleton");
    public CopperExoskeletonArmorItem(ArmorMaterial material, Type type, Properties properties) {
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
        if (slot == EquipmentSlot.CHEST) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            builder.putAll(super.getDefaultAttributeModifiers(slot));

            builder.put(
                    ForgeMod.BLOCK_REACH.get(),
                    new AttributeModifier(
                            "Exoskeleton reach",
                            ServerConfig.EXOSKELETON_REACH.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            builder.put(
                    ModAttributes.BREAK_SPEED.get(),
                    new AttributeModifier(
                            "Exoskeleton breaking speed",
                            ServerConfig.EXOSKELETON_BLOCK_BREAK_SPEED.get(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    @Override
    protected boolean withCustomModel() {
        return true;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(CopperExoskeletonModel::createBodyLayer, CopperExoskeletonModel::new);
    }
}
