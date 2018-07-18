package epicsquid.mysticallib.gui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.event.RegisterGuiFactoriesEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

  private static Map<String, Integer> guiNames = new HashMap<String, Integer>();
  private static Map<Integer, IGuiFactory> guis = new HashMap<Integer, IGuiFactory>();
  private static int id = 0;

  public GuiHandler() {
    MinecraftForge.EVENT_BUS.post(new RegisterGuiFactoriesEvent());
  }

  public static boolean hasGui(@Nonnull String name) {
    return guiNames.containsKey(name);
  }

  public static int getGuiID(@Nonnull String name) {
    return guiNames.get(name);
  }

  public static void registerGui(@Nonnull IGuiFactory factory) {
    guiNames.put(factory.getName(), id);
    guis.put(id, factory);
    id++;
  }

  @Override
  public Object getServerGuiElement(int ID, @Nonnull EntityPlayer player, @Nonnull World world, int x, int y, int z) {
    if (guis.containsKey(ID)) {
      return guis.get(ID).constructContainer(player, world, x, y, z);
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, @Nonnull EntityPlayer player, @Nonnull World world, int x, int y, int z) {
    if (guis.containsKey(ID)) {
      return guis.get(ID).constructGui(player, world, x, y, z);
    }
    return null;
  }

}
