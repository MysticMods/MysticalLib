package epicsquid.mysticallib;

import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.setup.IProxy;
import epicsquid.mysticallib.setup.ModSetup;
import epicsquid.mysticallib.setup.ServerProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("mysticallib")
public class MysticalLib {

  public static final String MODID = "mysticallib";

  // Sided setup
  public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  // Side agnostic setup
  public static ModSetup setup = new ModSetup();

  public MysticalLib() {
    // Register the setup method for mod loading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
  }

  private void setup(final FMLCommonSetupEvent event) {
    setup.init();
    proxy.init();
  }


}
