package net.star.copperoverthrow.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.ServerConfig;
import net.star.copperoverthrow.item.ModAttributes;
import org.jetbrains.annotations.Nullable;

public class CrashHelmetArmorItem extends AbstractExtendedArmorItem {
    private static final ResourceLocation TEXTURE = makeCustomTextureLocation(CopperOverthrow.MOD_ID, "crash_helmet");
    public CrashHelmetArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public boolean isFullSetActive(LivingEntity living) {
        return false;
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
                ModAttributes.HEAD_IMPACT_RESISTANCE,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, "crash_helmet_head_impact_resistance"),
                        ServerConfig.CRASH_HELMET_HEAD_IMPACT_RESISTANCE.get(),
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HEAD
        );
    }
}
