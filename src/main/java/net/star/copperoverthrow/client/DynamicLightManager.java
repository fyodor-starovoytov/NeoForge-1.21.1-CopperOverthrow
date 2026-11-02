package net.star.copperoverthrow.client;

import net.minecraft.core.BlockPos;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicLightManager {
    private static final Map<BlockPos, Integer> ACTIVE_LIGHTS = new ConcurrentHashMap<>();

    public static void setLight(BlockPos pos, int level) {
        if (level > 0) {
            ACTIVE_LIGHTS.put(pos, level);
            System.out.println("COPPEROVERTHROW DEBUG: Added light");
        } else {
            ACTIVE_LIGHTS.remove(pos);
            System.out.println("COPPEROVERTHROW DEBUG: Removed light");
        }
    }

    public static int getCustomLightLevel(BlockPos pos) {
        return ACTIVE_LIGHTS.getOrDefault(pos, 0);
    }

    public static boolean hasCustomLightAt(BlockPos pos) {
        return ACTIVE_LIGHTS.containsKey(pos);
    }
}
