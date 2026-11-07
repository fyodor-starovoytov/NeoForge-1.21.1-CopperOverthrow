package net.star.copperoverthrow;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.star.copperoverthrow.attribute.ModAttributes;
import net.star.copperoverthrow.block.ModBlocks;
import net.star.copperoverthrow.block.entity.ModBlockEntities;
import net.star.copperoverthrow.component.ModDataComponents;
import net.star.copperoverthrow.effect.ModEffects;
import net.star.copperoverthrow.item.ModCreativeModeTabs;
import net.star.copperoverthrow.item.ModItems;
import net.star.copperoverthrow.sound.ModSounds;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.transformer.Config;

@Mod(CopperOverthrow.MOD_ID)
public class CopperOverthrow {

    public static final String MOD_ID = "copperoverthrow";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CopperOverthrow(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        modEventBus.register(ModSounds.SOUND_EVENTS);
        ModDataComponents.register(modEventBus);

        ModAttributes.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }


    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
