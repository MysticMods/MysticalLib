package epicsquid.mysticallib.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AABBUtil {
  public static AxisAlignedBB buildFromEntity (Entity entity) {
    return new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.posX, entity.posY, entity.posZ);
  }

  public static BlockPos max (AxisAlignedBB box) {
    return new BlockPos(box.maxX, box.maxY, box.maxZ);
  }

  public static BlockPos min (AxisAlignedBB box) {
    return new BlockPos(box.minX, box.minY, box.minZ);
  }

  public static List<BlockPos> uniqueZX (AxisAlignedBB box) {
    List<BlockPos> result = new ArrayList<>();
    for (double x = box.minX; x < box.maxX; x++) {
      for (double z = box.minZ; z < box.maxZ; z++) {
        result.add(new BlockPos(x, box.maxY, z));
      }
    }
    return result;
  }

  public static Iterable<BlockPos> unique (AxisAlignedBB box) {
   return BlockPos.getAllInBox((int) box.maxX, (int) box.maxY, (int) box.maxZ, (int) box.minX, (int) box.minY, (int) box.minZ);
  }
}
