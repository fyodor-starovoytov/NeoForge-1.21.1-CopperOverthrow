package net.star.copperoverthrow;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = CopperOverthrow.MOD_ID)
public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue RAIN_DRUMS_NOTE_PARTICLES = BUILDER
            .comment("Whether feature Rain Drums emit particles when playing a note. Default: TRUE")
            .translation("copperoverthrow.config.rain_drum_enable_notes")
            .define("noteParticlesBoolean", true);

    public static final ModConfigSpec.BooleanValue RAIN_DRUMS_SPARK_PARTICLES = BUILDER
            .comment("Whether feature Rain Drums sparkle during full moon. Default: TRUE")
            .translation("copperoverthrow.config.rain_drum_enable_sparks")
            .define("sparkleParticlesBoolean", true);

    public static final ModConfigSpec.DoubleValue WIND_CHIME_IDLE_SOUND_CHANCE = BUILDER
            .comment("Wind Chimes' probability to play idle sound")
            .translation("copperoverthrow.config.wind_chime_idle_sound_chance")
            .defineInRange("windChimeIdleSoundChance", 0.03, 0.0, 1.0);


    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean rainDrumsNoteParticles;
    public static boolean rainDrumsSparkingParticles;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        rainDrumsNoteParticles = RAIN_DRUMS_NOTE_PARTICLES.get();
        rainDrumsSparkingParticles = RAIN_DRUMS_SPARK_PARTICLES.get();
    }
}