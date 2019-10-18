package epicsquid.mysticallib.particle;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.util.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ParticleRegistry {

  public static Map<String, ResourceLocation> particleTextures = new HashMap<String, ResourceLocation>();

  private static Map<String, Constructor<? extends ParticleBase>> particles = new HashMap<String, Constructor<? extends ParticleBase>>();

  public static String registerParticle(@Nonnull String modid, @Nonnull Class<? extends ParticleBase> particleClass, @Nonnull ResourceLocation texture) {
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
