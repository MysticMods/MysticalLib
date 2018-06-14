package epicsquid.mysticallib;

import epicsquid.mysticallib.fx.FXHandler;
import epicsquid.mysticallib.proxy.CommonProxy;
import epicsquid.mysticallib.recipe.RecipeRegistry;
import epicsquid.mysticallib.tile.CableManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MysticalLib.MODID, version = MysticalLib.VERSION, name = MysticalLib.MODNAME, dependencies = MysticalLib.DEPENDENCIES)
public class MysticalLib {
  public static final String MODID = "mysticallib";
  public static final String VERSION = "@VERSION@";
  public static final String MODNAME = "MysticalLib";
  public static final String DEPENDENCIES = "after:*";

  @SidedProxy(clientSide = "epicsquid.mysticallib.proxy.ClientProxy", serverSide = "epicsquid.mysticallib.proxy.CommonProxy") public static CommonProxy proxy;

  @Instance public static MysticalLib INSTANCE;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new LibRegistry());
    MinecraftForge.EVENT_BUS.register(new LibEvents());
    MinecraftForge.EVENT_BUS.register(new CableManager());
    MinecraftForge.EVENT_BUS.register(new FXHandler());
    MinecraftForge.EVENT_BUS.register(new RecipeRegistry());
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
