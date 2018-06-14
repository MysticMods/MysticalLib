package epicsquid.mysticallib.proxy;


import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.event.RegisterFXEvent;
import epicsquid.mysticallib.event.RegisterWorldGenEvent;
import epicsquid.mysticallib.gui.GuiHandler;
import epicsquid.mysticallib.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
  public void preInit(FMLPreInitializationEvent event) {
    LibRegistry.initAll();
    PacketHandler.registerMessages();
    MinecraftForge.EVENT_BUS.post(new RegisterFXEvent());
  }

  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.post(new RegisterWorldGenEvent());
  }

  public void postInit(FMLPostInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(MysticalLib.INSTANCE, new GuiHandler());
  }
}
