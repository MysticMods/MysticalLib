package epicsquid.mysticallib.particle;

import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ParticleRegistry {

  public static Map<String, ResourceLocation> particleTextures = new HashMap<String, ResourceLocation>();

  private static Map<String, Constructor<? extends ParticleBase>> particles = new HashMap<String, Constructor<? extends ParticleBase>>();

  public static Map<String, Constructor<? extends ParticleBase>> getParticles() {
    return particles;
  }
}
