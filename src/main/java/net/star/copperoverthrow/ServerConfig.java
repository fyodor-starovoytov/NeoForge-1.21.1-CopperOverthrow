package net.star.copperoverthrow;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue RAIN_DRUM_DETECTION_RADIUS = BUILDER
            .comment("The radius in blocks within which rain drums detect rain or water drops.")
            .translation("copperoverthrow.config.rain_drum_detection_radius")
            .defineInRange("rainDrumDetectionRadius", 1, 1, 16);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
