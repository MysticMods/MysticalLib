package epicsquid.mysticallib.gui;

import java.util.HashMap;
import java.util.Map;

import epicsquid.mysticallib.event.RegisterGuiFactoriesEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
  public static Map<String, Integer> guiNames = new HashMap<String, Integer>();
  public static Map<Integer, IGuiFactory> guis = new HashMap<Integer, IGuiFactory>();
  static int id = 0;

  public GuiHandler() {
    MinecraftForge.EVENT_BUS.post(new RegisterGuiFactoriesEvent());
  }

  public static boolean hasGui(String name) {
    return guiNames.containsKey(name);
  }

  public static int getGuiID(String name) {
    return guiNames.get(name);
  }

  public static void registerGui(IGuiFactory factory) {
    guiNames.put(factory.getName(), id);
    guis.put(id, factory);
    id++;
  }

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (guis.containsKey(ID)) {
      return guis.get(ID).constructContainer(player, world, x, y, z);
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (guis.containsKey(ID)) {
      return guis.get(ID).constructGui(player, world, x, y, z);
    }
    return null;
  }

}
