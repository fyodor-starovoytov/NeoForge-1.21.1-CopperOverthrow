package net.star.copperoverthrow;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CopperOverthrow.MOD_ID, value = Dist.CLIENT)
public class ClientConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue RAIN_DRUMS_NOTE_PARTICLES = BUILDER
            .comment("Whether feature Rain Drums emit particles when playing a note. Default: TRUE")
            .translation("copperoverthrow.config.rain_drum_enable_notes")
            .define("noteParticlesBoolean", true);

    public static final ForgeConfigSpec.BooleanValue RAIN_DRUMS_SPARK_PARTICLES = BUILDER
            .comment("Whether feature Rain Drums sparkle during full moon. Default: TRUE")
            .translation("copperoverthrow.config.rain_drum_enable_sparks")
            .define("sparkleParticlesBoolean", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean rainDrumsNoteParticles;
    public static boolean rainDrumsSparkingParticles;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC || event instanceof ModConfigEvent.Unloading) return;
        rainDrumsNoteParticles = RAIN_DRUMS_NOTE_PARTICLES.get();
        rainDrumsSparkingParticles = RAIN_DRUMS_SPARK_PARTICLES.get();
    }
}