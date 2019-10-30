package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.event.RegisterFXEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModSetup {

  public ModSetup() {
  }

  public void init() {
    FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterFXEvent());
  }
}
