package net.star.copperoverthrow;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        public static final ModConfigSpec.DoubleValue LOG_STRIPPER_TOOL_DAMAGE = BUILDER
                .comment("Durability of used Tool lost per ingredient on a log stripping station")
                .translation("copperoverthrow.config.log_stripper_tool_damage")
                .defineInRange("logStripperToolDamage", 1.0, 0.0, 5.0);

        public static final ModConfigSpec.DoubleValue STEPPER_BOOTS_STEP_HEIGHT = BUILDER
                .comment("Stepper Boots' added Step Height amount. 0.6 is vanilla Step Height (0.6 + x)")
                .translation("copperoverthrow.config.stepper_boots_step_height")
                .defineInRange("stepperBootsStepHeight", 0.5, 0.0, 3.0);

        public static final ModConfigSpec.DoubleValue STEPPER_BOOTS_SAFE_FALL_DISTANCE = BUILDER
                .comment("Stepper Boots' added Safe Distance amount. 3.0 is vanilla Safe Fall (3.0 + x)")
                .translation("copperoverthrow.config.stepper_boots_safe_fall_distance")
                .defineInRange("stepperBootsSafeFallDistance", 3.0, 0.0, 999.0);

        public static final ModConfigSpec.DoubleValue STEPPER_LEGGINGS_SPEED_BOOST = BUILDER
                .comment("Stepper Leggings' added Movement Speed (0.020 equals 20%)")
                .translation("copperoverthrow.config.stepper_leggings_speed")
                .defineInRange("stepperLeggingsSpeedBoost", 0.020, 0.000, 1.000);

        public static final ModConfigSpec.DoubleValue STEPPER_LEGGINGS_WATER_MOBILITY = BUILDER
                .comment("Stepper Leggings' added Water Mobility Efficiency (0.5 equals 50% of water resistance penalty mitigated")
                .translation("copperoverthrow.config.stepper_leggings_water")
                .defineInRange("stepperLeggingsWaterMobility", 0.5, 0.0, 1.0);
        public static final ModConfigSpec SPEC = BUILDER.build();
}
