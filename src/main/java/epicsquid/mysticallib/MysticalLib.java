package epicsquid.mysticallib;

import epicsquid.mysticallib.event.OBJModelRegistrar;
import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.setup.IProxy;
import epicsquid.mysticallib.setup.ModSetup;
import epicsquid.mysticallib.setup.ServerProxy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mysticallib")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MysticalLib {

  public static final String MODID = "mysticallib";
  public static final Logger LOG = LogManager.getLogger();

  // Sided setup
  public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  // Side agnostic setup
  public static ModSetup setup = new ModSetup();

  public MysticalLib() {
    // Register the setup method for mod loading
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    bus.addListener(this::setup);

    DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
      bus.addListener(OBJModelRegistrar::modelBake);
      bus.addListener(OBJModelRegistrar::textureStitchPre);
    });
  }

  private void setup(final FMLCommonSetupEvent event) {
    setup.init();
    proxy.init();
  }


}
