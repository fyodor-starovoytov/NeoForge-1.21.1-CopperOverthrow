package net.star.copperoverthrow.effect;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.custom.CopperChiselItem;
import org.checkerframework.checker.units.qual.C;

import java.beans.Visibility;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, CopperOverthrow.MOD_ID);


    public static final Holder<MobEffect> CONDUCTIVITY_EFFECT = MOB_EFFECTS.register("conductivity",
            ()-> new ConductivityEffect(MobEffectCategory.NEUTRAL));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
