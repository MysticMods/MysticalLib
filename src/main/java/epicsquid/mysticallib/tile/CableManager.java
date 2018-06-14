package epicsquid.mysticallib.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class CableManager {
  public static Map<String, Class<? extends CableNetwork>> networkRegistry = new HashMap<String, Class<? extends CableNetwork>>();
  private static int ID = 0;
  private boolean inited = false;

  @SubscribeEvent
  public void onUnload(WorldEvent.Unload event) {
    inited = false;
  }

  @SubscribeEvent
  public void onWorldTick(WorldTickEvent event) {
    if (event.phase == TickEvent.Phase.END && !inited && !event.world.isRemote) {
      inited = true;
      CableWorldData.get(event.world);
      for (CableNetwork n : CableWorldData.get(event.world).networks.values()) {
        for (BlockPos p : n.cables) {
          TileEntity t = event.world.getTileEntity(p);
          if (t instanceof TileCable && ((TileCable) t).type.equalsIgnoreCase(n.type)) {
            ((TileCable) t).network = n;
            t.markDirty();
          }
        }
        n.dirty = true;
      }
    } else if (event.phase == TickEvent.Phase.END && !event.world.isRemote) {
      CableWorldData d = CableWorldData.get(event.world);
      for (int i = 0; i < d.networks.size(); i++) {
        Entry<Integer, CableNetwork> e = ((Entry<Integer, CableNetwork>) d.networks.entrySet().toArray()[i]);
        if (!e.getValue().valid) {
          d.networks.remove(e.getKey());
          i = Math.max(0, i - 1);
          d.markDirty();
        } else {
          if (e.getValue().dirty) {
            e.getValue().updateConnections(event.world);
          }
          if (e.getValue().needsDistributionTick()) {
            e.getValue().distribute();
          }
        }
      }
    }
  }

  public static int getNextID(CableWorldData d) {
    int id = ID++;
    while (d.networks.containsKey(id)) {
      id = ID++;
    }
    return id;
  }
}
