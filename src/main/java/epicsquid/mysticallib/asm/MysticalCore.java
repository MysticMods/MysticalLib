package epicsquid.mysticallib.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MysticalCore extends DummyModContainer {
  public MysticalCore() {
    super(new ModMetadata());
    ModMetadata meta = getMetadata();
    meta.modId = "mysticalcore";
    meta.name = "MysticalCore";
    meta.version = "1.0";
    meta.credits = "";
    meta.authorList = Arrays.asList("Elucent");
    meta.description = "Tiny coremod used to bring back IItemRenderers.";
    meta.screenshots = new String[0];
    meta.logoFile = "";
  }

  @Override
  public boolean registerBus(EventBus bus, LoadController controller) {
    bus.register(this);
    return true;
  }

  @Subscribe
  public void modConstruction(FMLConstructionEvent evt) {

  }

  @Subscribe
  public void preInit(FMLPreInitializationEvent evt) {

  }

  @Subscribe
  public void init(FMLInitializationEvent evt) {

  }

  @Subscribe
  public void postInit(FMLPostInitializationEvent evt) {

  }
}
