package epicsquid.mysticallib.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;

public class Util {
  public static Random rand = new Random();

  public static <T extends TileEntity> List<T> getTileEntitiesWithin(World world, Class<? extends T> teClass, int x1, int y1, int z1, int x2, int y2, int z2) {
    List<T> tiles = new ArrayList<T>();
    for (int i = x1; i <= x2; i++) {
      for (int j = y1; j <= y2; j++) {
        for (int k = z1; k <= z2; k++) {
          BlockPos p = new BlockPos(i, j, k);
          Chunk c = world.getChunkFromBlockCoords(p);
          if (c.isLoaded()) {
            TileEntity t = world.getChunkFromBlockCoords(p).getTileEntity(p, EnumCreateEntityType.CHECK);
            if (t != null && teClass.isInstance(t)) {
              tiles.add((T) t);
            }
          }
        }
      }
    }
    return tiles;
  }

  public static String lowercase(String s) {
    String f = "";
    for (int i = 0; i < s.length(); i++) {
      String c = s.substring(i, i + 1);
      if (c.toUpperCase().compareTo(c) == 0) {
        if (i > 0) {
          f += "_";
        }
        f += c.toLowerCase();
      } else {
        f += c;
      }
    }
    return f;
  }

  public static int intColor(int r, int g, int b) {
    return ((255 << 24) + r * 65536 + g * 256 + b);
  }

  public static String getLowercaseClassName(Class c) {
    String[] nameParts = c.getTypeName().split("\\.");
    String className = nameParts[nameParts.length - 1];
    return lowercase(className);
  }
}
