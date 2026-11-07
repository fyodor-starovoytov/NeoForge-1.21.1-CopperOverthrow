package net.star.copperoverthrow;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
        private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        public static final ForgeConfigSpec.IntValue HANDSAW_USE_TIME = BUILDER
            .comment("The Handsaw use duration in ticks")
            .translation("copperoverthrow.config.handsaw_use")
            .defineInRange("HandSawUse", 80, 1, 999);

        public static final ForgeConfigSpec.IntValue HANDSAW_MAX_DESTROYED_BLOCKS = BUILDER
                .comment("Maximum of possible blocks broken at a time, having larger value may affect the frame rate")
                .translation("copperoverthrow.config.handsaw_max")
                .defineInRange("HandSawMax", 128, 1, 999);
        public static final ForgeConfigSpec.IntValue MEASURE_MAX_DISTANCE = BUILDER
            .comment("Maximum distance a measure creates hologram blocks")
            .translation("copperoverthrow.config.measure_max_distance")
            .defineInRange("MeasureMaxDistance", 100, 1, 999);


    public static final ForgeConfigSpec.DoubleValue CRASH_HELMET_HEAD_IMPACT_RESISTANCE = BUILDER
                .comment("Crash Helmets' Head Impact Resistance Percentage")
                .translation("copperoverthrow.config.crash_helmet_head_impact_resistance")
                .defineInRange("CrashHelmetHeadImpactResistance", 1.0, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue EXOSKELETON_BLOCK_BREAK_SPEED = BUILDER
            .comment("Copper Exoskeleton's Added Block Break Speed")
            .translation("copperoverthrow.config.exoskeleton_breaking_speed")
            .defineInRange("ExoskeletonBreakingSpeed", 0.3, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue EXOSKELETON_REACH = BUILDER
            .comment("Copper Exoskeleton's added block reach amount")
            .translation("copperoverthrow.config.exoskeleton_reach")
            .defineInRange("ExoskeletonReach", 3, 0, 10);


    public static final ForgeConfigSpec.DoubleValue STEPPER_BOOTS_STEP_HEIGHT = BUILDER
                .comment("Stepper Boots' added Step Height amount. 0.6 is vanilla Step Height (0.6 + x)")
                .translation("copperoverthrow.config.stepper_boots_step_height")
                .defineInRange("StepperBootsStepHeight", 0.5, 0.0, 3.0);

        public static final ForgeConfigSpec.DoubleValue STEPPER_BOOTS_SAFE_FALL_DISTANCE = BUILDER
                .comment("Stepper Boots' added Safe Distance amount. 3.0 is vanilla Safe Fall (3.0 + x)")
                .translation("copperoverthrow.config.stepper_boots_safe_fall_distance")
                .defineInRange("stepperBootsSafeFallDistance", 3.0, 0.0, 999.0);

        public static final ForgeConfigSpec.DoubleValue STEPPER_LEGGINGS_SPEED_BOOST = BUILDER
                .comment("Stepper Leggings' added Movement Speed (0.020 equals 20%)")
                .translation("copperoverthrow.config.stepper_leggings_speed")
                .defineInRange("stepperLeggingsSpeedBoost", 0.020, 0.000, 1.000);

        public static final ForgeConfigSpec.DoubleValue STEPPER_LEGGINGS_WATER_MOBILITY = BUILDER
                .comment("Stepper Leggings' added Water Mobility Efficiency (0.5 equals 50% of water resistance penalty mitigated")
                .translation("copperoverthrow.config.stepper_leggings_water")
                .defineInRange("stepperLeggingsWaterMobility", 0.5, 0.0, 1.0);

         public static final ForgeConfigSpec.DoubleValue LOG_STRIPPER_TOOL_DAMAGE = BUILDER
            .comment("Durability of used Tool lost per ingredient on a log stripping station")
            .translation("copperoverthrow.config.log_stripper_tool_damage")
            .defineInRange("logStripperToolDamage", 0.5, 0.0, 5.0);

        public static final ForgeConfigSpec.DoubleValue TAMTAM_BASIC_VOLUME_RADIUS = BUILDER
                .comment("Tam-Tam's Volume radius (16 blocks * x volume")
                .translation("copperoverthrow.config.tam_tam_volume")
                .defineInRange("tamtamVolumeRadius", 8.0, 0.0, 32.0);
        public static final ForgeConfigSpec.IntValue TAMTAM_FULL_MOON_VOLUME_RADIUS = BUILDER
                .comment("Tam-Tam's Volume radius (16 blocks * x volume")
                .translation("copperoverthrow.config.tam_tam_full_moon_volume")
                .defineInRange("tamtamMoonVolumeRadius", 2, 0, 32);
        public static final ForgeConfigSpec.DoubleValue TAMTAM_FULL_MOON_VOLUME_CHANCE = BUILDER
                .comment("Tam-Tam's Volume radius (16 blocks * x volume")
                .translation("copperoverthrow.config.tam_tam_full_moon_chance")
                .defineInRange("tamtamMoonVolumeChance", 0.02, 0.0, 1.0);

        public static final ForgeConfigSpec.DoubleValue DESK_BELL_VOLUME_RAIDUS = BUILDER
            .comment("Desk Bell's Volume radius (16 blocks * x volume")
            .translation("copperoverthrow.config.kitchen_bell_volume")
            .defineInRange("kitchenbellVolumeRadius", 1.0, 0.0, 32.0);

        public static final ForgeConfigSpec.BooleanValue JAMMING = BUILDER
                .comment("Whether Desk Bell has a small chance to get jammed. Default: TRUE")
                .translation("copperoverthrow.config.jamming")
                .define("jamming", true);

        public static final ForgeConfigSpec.IntValue WIND_CHIME_MAX_ROTATION = BUILDER
            .comment("Wind Chimes' maximum rotation in degrees on hit")
            .translation("copperoverthrow.config.wind_chime_max_rotation")
            .defineInRange("windChimeMaxRotation", 60, 1, 360);
        public static final ForgeConfigSpec.DoubleValue WIND_CHIME_IDLE_SOUND_CHANCE = BUILDER
                .comment("Wind Chimes' probability to play idle sound")
                .translation("copperoverthrow.config.wind_chime_idle_sound_chance")
                .defineInRange("windChimeIdleSoundChance", 0.003, 0.0, 1.0);

        public static final ForgeConfigSpec.IntValue CHIME_CHECK_RANGE = BUILDER
            .comment("Wind Chimes' check range to see if the chime is open enough to play idle sounds, needs to be walk through to be updated")
            .translation("copperoverthrow.config.wind_chime_idle_range")
            .defineInRange("windChimeIdleRange", 12, 0, 20);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
