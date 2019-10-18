package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.event.RegisterParticleEvent;
import epicsquid.mysticallib.particle.ParticleRegistry;
import epicsquid.mysticallib.particle.particles.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientLibRegistry {

  public static String PARTICLE_GLOW, PARTICLE_SMOKE, PARTICLE_SPARK, PARTICLE_GLITTER, PARTICLE_FLAME, PARTICLE_LEAF;

  @SubscribeEvent
  public static void onRegisterParticles(@Nonnull RegisterParticleEvent event) {
    PARTICLE_GLOW = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleGlow.class, new ResourceLocation("mysticallib:particle/particle_glow"));
    PARTICLE_SMOKE = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleSmoke.class, new ResourceLocation("mysticallib:particle/particle_smoke"));
    PARTICLE_SPARK = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleSpark.class, new ResourceLocation("mysticallib:particle/particle_sparkle"));
    PARTICLE_GLITTER = ParticleRegistry
        .registerParticle(MysticalLib.MODID, ParticleGlitter.class, new ResourceLocation("mysticallib:particle/particle_sparkle"));
    PARTICLE_FLAME = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleFlame.class, new ResourceLocation("mysticallib:particle/particle_fire"));
    PARTICLE_LEAF = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleLeafArc.class, new ResourceLocation("mysticallib:particle/particle_leaf1"));
  }
}
