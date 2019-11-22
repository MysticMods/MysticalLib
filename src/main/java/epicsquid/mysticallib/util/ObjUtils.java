package epicsquid.mysticallib.util;

import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class ObjUtils {
  public static Set<ResourceLocation> OBJ_MODELS = new HashSet<>();
  public static Set<ResourceLocation> OBJ_TEXTURES = new HashSet<>();

  public static void loadModel (ResourceLocation rl) {
    OBJ_MODELS.add(rl);
  }

  public static void loadTexture (ResourceLocation rl) {
    OBJ_TEXTURES.add(rl);
  }
}
