package net.star.copperoverthrow.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.star.copperoverthrow.client.DynamicLightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelLightEngine.class)
public class LevelLightEngineMixin {

    @Inject(method = "getRawBrightness", at = @At("RETURN"), cancellable = true)
    private void onGetRawBrightness(BlockPos pos, int amount, CallbackInfoReturnable<Integer> cir) {
        if (DynamicLightManager.hasCustomLightAt(pos)) {
            int customLight = DynamicLightManager.getCustomLightLevel(pos);
            System.out.println("COPPEROVERTHROW MIXING: READ LIGHT");
            cir.setReturnValue(Math.max(customLight, cir.getReturnValue() != null ? cir.getReturnValue() : 0));
        }
    }
}