package net.star.copperoverthrow.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.entity.custom.FireflyEntity;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CopperOverthrow.MOD_ID);

    public static final Supplier<EntityType<FireflyEntity>> FIREFLY =
            ENTITY_TYPES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 0.4f).build("firefly"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
