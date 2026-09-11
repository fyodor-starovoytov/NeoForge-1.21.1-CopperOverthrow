package net.star.copperoverthrow.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;

import java.util.function.Supplier;

public class ModSounds {

        public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
                DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CopperOverthrow.MOD_ID);

        public static final Supplier<SoundEvent> RAIN_DRUM_RAINING = registerSoundEvent("raindrumb1");
        public static final Supplier<SoundEvent> RAIN_DRUM_PLAYING = registerSoundEvent("rain_drum_playing");

        private static Supplier<SoundEvent> registerSoundEvent(String name) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CopperOverthrow.MOD_ID, name);
            return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
        }

        public static void register(IEventBus eventBus) {
            SOUND_EVENTS.register(eventBus);
        }


}
