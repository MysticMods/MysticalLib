package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.event.RegisterFXEvent;
import epicsquid.mysticallib.modifiers.PlayerModifierRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModSetup {

  public ModSetup() {
  }

  public void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    bus.post(new RegisterFXEvent());
    MinecraftForge.EVENT_BUS.addListener(PlayerModifierRegistry.getInstance()::onEntityConstructed);
  }
}
