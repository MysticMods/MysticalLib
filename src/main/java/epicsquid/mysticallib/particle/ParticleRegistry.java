package epicsquid.mysticallib.particle;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.proxy.ClientProxy;
import epicsquid.mysticallib.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleRegistry {

  private static Map<String, Constructor<? extends ParticleBase>> particles = new HashMap<String, Constructor<? extends ParticleBase>>();
  private static Map<String, ResourceLocation> particleTextures = new HashMap<String, ResourceLocation>();

  public static String registerParticle(@Nonnull Class<? extends ParticleBase> particleClass, @Nonnull ResourceLocation texture) {
    String name = Util.getLowercaseClassName(particleClass);
    if (MysticalLib.proxy instanceof ClientProxy) {
      try {
        if (particles.containsKey(name) || particleTextures.containsKey(name)) {
          System.out.println("WARNING: PARTICLE ALREADY REGISTERED WITH NAME \"" + name + "\"!");
        } else {
          particles.put(name,
              particleClass.getConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class, double[].class));
          particleTextures.put(name, texture);
        }
      } catch (NoSuchMethodException | SecurityException e) {
        e.printStackTrace();
      }
    }
    return name;
  }

  public static Map<String, Constructor<? extends ParticleBase>> getParticles() {
    return particles;
  }
}
