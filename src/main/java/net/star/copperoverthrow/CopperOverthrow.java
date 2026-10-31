package net.star.copperoverthrow;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.effect.ModEffects;
import net.star.copperoverthrow.entity.ModEntities;
import net.star.copperoverthrow.item.ModCreativeModeTabs;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.sound.ModSounds;
import org.slf4j.Logger;

@Mod(CopperOverthrow.MOD_ID)
public class CopperOverthrow {

    public static final String MOD_ID = "copperoverthrow";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CopperOverthrow(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModDataComponents.register(modEventBus);

        ModAttributes.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }


    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.COPPER_HAMMER);

        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.TAMTAM);
        }

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
