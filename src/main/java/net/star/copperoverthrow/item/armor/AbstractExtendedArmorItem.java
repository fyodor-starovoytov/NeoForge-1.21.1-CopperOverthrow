package net.star.copperoverthrow.item.armor;

import com.google.common.collect.Multimap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * basic armor item.
 */
public abstract class AbstractExtendedArmorItem extends ArmorItem {

    public AbstractExtendedArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    public boolean isFullSetActive(LivingEntity living) {
        return isFullSetActive(living, this.getMaterial());
    }

    public static boolean isFullSetActive(LivingEntity living, Holder<ArmorMaterial> materials) {
        if (living == null) {
            return false;
        }
        ArmorItem head = living.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        Item chestPlate = living.getItemBySlot(EquipmentSlot.CHEST).getItem();
        ArmorItem chest;
        if (chestPlate instanceof ElytraItem || chestPlate instanceof AirItem) {
            return false;
        } else {
            chest = (ArmorItem) living.getItemBySlot(EquipmentSlot.CHEST).getItem();
        }
        ArmorItem legs = living.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        ArmorItem feet = living.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem armorItem ? armorItem : null;
        return (head != null && legs != null && feet != null) && (head.getMaterial() == materials && chest.getMaterial() == materials && legs.getMaterial() == materials && feet.getMaterial() == materials);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {return null;}

    //region display / model

    /**
     * creates a custom texture for your armor in
     * <br>{@code <nameSpace>:textures/models/armor/custom/<id>.png}
     */

    public static ResourceLocation makeCustomTextureLocation(String nameSpace, String id) {
        return ResourceLocation.fromNamespaceAndPath(nameSpace, "textures/models/armor/" + id + ".png");
    }

    //endregion

    /**
     * @param registry the Register to add to
     * @param baseName the base name of the armor
     * @param creator a lambda function to create an instance of the armor, mostly a method reference to the constructor
     * @return a Map mapping the ArmorType to the RegObj for the slot
     */
    public static <T extends AbstractExtendedArmorItem> Map<Type, DeferredItem<T>> createRegistry(DeferredRegister.Items registry, String baseName, Function<Type, T> creator) {
        return Util.make(new EnumMap<>(Type.class), map -> {
            for (Type type : Type.values()) {
                if (type != Type.BODY) map.put(type, registry.register(baseName + "_" + type.getName(), () -> creator.apply(type)));
            }
        });
    }
}