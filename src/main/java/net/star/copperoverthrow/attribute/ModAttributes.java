package net.star.copperoverthrow.attribute;

import com.ibm.icu.text.DateFormat;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.star.copperoverthrow.CopperOverthrow;

public class ModAttributes{

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, CopperOverthrow.MOD_ID);

    public static final RegistryObject<Attribute> HEAD_IMPACT_RESISTANCE = ATTRIBUTES.register(
            "head_impact_resistance",
            () -> new RangedAttribute(
                    "copperoverthrow.attribute.head_impact_resistance",
                    0.0,
                    0.0,
                    1.0
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> NIGHT_VISION = ATTRIBUTES.register(
            "night_vision",
            () -> new RangedAttribute(
                    "copperoverthrow.attribute.night_vision",
                    0.0,
                    0.0,
                    1.0
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> BREAK_SPEED = ATTRIBUTES.register(
            "break_speed",
            () -> new RangedAttribute(
                    "copperoverthrow.attribute.break_speed",
                    0.0,
                    0.0,
                    1.0
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> SAFE_FALL_DISTANCE = ATTRIBUTES.register(
            "safe_fall_distance",
            () -> new RangedAttribute(
                    "copperoverthrow.attribute.break_speed",
                    0.0,
                    0.0,
                    99
            ).setSyncable(true)
    );

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
