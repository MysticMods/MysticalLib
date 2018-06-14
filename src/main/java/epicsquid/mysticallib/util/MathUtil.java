package epicsquid.mysticallib.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtil {
  public static double nclamp(double d, double n) {
    return d - Math.floor(d / n) * n;
  }

  public static Vec3d rotateX(Vec3d v, float angle) {
    return new Vec3d(v.x * MathHelper.cos(angle) - v.y * MathHelper.sin(angle), v.x * MathHelper.sin(angle) + v.y * MathHelper.cos(angle), v.z);
  }

  public static Vec3d rotateZ(Vec3d v, float angle) {
    return new Vec3d(v.x, v.y * MathHelper.cos(angle) - v.z * MathHelper.sin(angle), v.y * MathHelper.sin(angle) + v.z * MathHelper.cos(angle));
  }

  public static Vec3d rotateY(Vec3d v, float angle) {
    return new Vec3d(v.z * MathHelper.cos(angle) - v.x * MathHelper.sin(angle), v.y, v.z * MathHelper.sin(angle) + v.x * MathHelper.cos(angle));
  }
}
