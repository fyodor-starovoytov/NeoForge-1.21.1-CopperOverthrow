package net.star.copperoverthrow.item.armor.client.provider;

import net.star.copperoverthrow.item.armor.client.model.ArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * model provider that uses a single model and uses part visibility to ensure only the necessary part of the model is visible
 */
public class SimpleModelProvider implements ArmorModelProvider {
    private final ArmorModel model;

    public SimpleModelProvider(Supplier<LayerDefinition> definitionSupplier, Function<ModelPart, ArmorModel> model) {
        this.model = model.apply(definitionSupplier.get().bakeRoot());
    }

    @Override
    public ArmorModel getModel(LivingEntity living, ItemStack stack, EquipmentSlot slot) {
        return model;
    }
}
