package net.star.copperoverthrow.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.star.copperoverthrow.CopperOverthrow;
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
}