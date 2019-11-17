package epicsquid.mysticallib.util;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class VoxelUtil {
  public static VoxelShape multiOr (VoxelShape base, VoxelShape ... shapes) {
    VoxelShape result = base;

    for (VoxelShape shape : shapes) {
      result = VoxelShapes.or(result, shape);
    }

    return result;
  }
}
