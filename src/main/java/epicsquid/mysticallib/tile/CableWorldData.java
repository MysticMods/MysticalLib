package epicsquid.mysticallib.tile;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import epicsquid.mysticallib.MysticalLib;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class CableWorldData extends WorldSavedData {
  public static Map<Integer, CableNetwork> networks = new HashMap<Integer, CableNetwork>();

  public CableWorldData(String name) {
    super(name);
  }

  public CableWorldData() {
    super(MysticalLib.MODID);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    NBTTagList takenIDs = new NBTTagList();
    for (Entry<Integer, CableNetwork> e : networks.entrySet()) {
      takenIDs.appendTag(new NBTTagInt(e.getKey()));
      tag.setTag("network" + e.getKey(), e.getValue().writeToNBT());
    }
    tag.setTag("takenIDs", takenIDs);
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    NBTTagList takenIDs = tag.getTagList("takenIDs", Constants.NBT.TAG_INT);
    for (int i = 0; i < takenIDs.tagCount(); i++) {
      int id = takenIDs.getIntAt(i);
      NBTTagCompound t = tag.getCompoundTag("network" + id);
      CableNetwork n = null;
      if (t.hasKey("type")) {
        String type = t.getString("type");
        Class<? extends CableNetwork> cl = CableManager.networkRegistry.get(type);
        try {
          n = cl.getConstructor(CableWorldData.class, int.class).newInstance(this, id);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      } else {
        n = new CableNetwork(this, id);
      }
      n.dirty = true;
      n.readFromNBT(t);
    }
  }

  public static CableWorldData get(World w) {
    MapStorage s = w.getMapStorage();
    CableWorldData d = (CableWorldData) s.getOrLoadData(CableWorldData.class, MysticalLib.MODID);

    if (d == null) {
      d = new CableWorldData();
      s.setData(MysticalLib.MODID, d);
    }

    return d;
  }

}
