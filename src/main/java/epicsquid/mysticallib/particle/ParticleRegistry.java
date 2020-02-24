package epicsquid.mysticallib.particle;

import java.lang.reflect.Constructor;
import java.util.*;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.proxy.ClientProxy;
import epicsquid.mysticallib.util.Util;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleRegistry {
  public static EnumParticleTypes RAIN = null;

  public static Map<String, List<ResourceLocation>> particleMultiTextures = new HashMap<>();

  private static Map<String, Constructor<? extends ParticleBase>> particles = new HashMap<>();

  private static Random rand = new Random();

  public static ResourceLocation getTexture (String name) {
    List<ResourceLocation> textures = particleMultiTextures.get(name);
    return textures.get(rand.nextInt(textures.size()));
  }

  public static String registerParticle(@Nonnull String modid, @Nonnull Class<? extends ParticleBase> particleClass, @Nonnull ResourceLocation ... textures) {
    String name = Util.getLowercaseClassName(particleClass);
    if (MysticalLib.proxy instanceof ClientProxy) {
      try {
        if (particles.containsKey(name) || particleMultiTextures.containsKey(name)) {
          System.out.println("WARNING: PARTICLE ALREADY REGISTERED WITH NAME \"" + name + "\"!");
        } else {
          particles.put(name,
              particleClass.getConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class, double[].class));
            particleMultiTextures.put(name, Arrays.asList(textures));
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
