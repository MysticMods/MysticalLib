package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.modifiers.PlayerModifierRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModSetup {
  public static void init(FMLCommonSetupEvent event) {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    MinecraftForge.EVENT_BUS.addListener(PlayerModifierRegistry.getInstance()::onEntityConstructed);
  }
}
