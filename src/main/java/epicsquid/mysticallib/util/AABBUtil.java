package epicsquid.mysticallib.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class AABBUtil {
  public static AxisAlignedBB buildFromEntity (Entity entity) {
    return new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.posX, entity.posY, entity.posZ);
  }
}
