package net.star.copperoverthrow.item;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;

public class ModAttributes extends Attributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, CopperOverthrow.MOD_ID);

    public static final Holder<Attribute> HEAD_IMPACT_RESISTANCE = ATTRIBUTES.register(
            "head_impact_resistance",
            () -> new PercentageAttribute(
                    "copperoverthrow.attribute.head_impact_resistance",
                    0.0,
                    0.0,
                    1.0
            ).setSyncable(true)
    );

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
