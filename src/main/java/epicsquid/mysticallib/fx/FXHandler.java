package epicsquid.mysticallib.fx;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.event.RegisterFXEvent;
import epicsquid.mysticallib.proxy.ClientProxy;
import epicsquid.mysticallib.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Registers effects to the FXRegistry
 */
public class FXHandler {

  public static int FX_BEAM, FX_SLASH, FX_BURST;

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRegisterFX(RegisterFXEvent event) {
    FX_BEAM = FXRegistry.registerEffect(nbt -> {
      EffectBeam beam = new EffectBeam();
      beam.read(nbt);
      EffectManager.addEffect(beam);
      return null;
    });
    FX_SLASH = FXRegistry.registerEffect(nbt -> {
      EffectSlash slash = new EffectSlash();
      slash.read(nbt);
      EffectManager.addEffect(slash);
      return null;
    });
    FX_BURST = FXRegistry.registerEffect(nbt -> {
      for (int i = 0; i < nbt.getInteger("count"); i++) {
        float yaw = Util.rand.nextFloat() * 2.0f * (float) Math.PI;
        float pitch = Util.rand.nextFloat() * 2.0f * (float) Math.PI;
        float speed = Util.rand.nextFloat() * (float) nbt.getDouble("speed");
        float vx = MathHelper.cos(pitch) * MathHelper.sin(yaw) * speed;
        float vy = MathHelper.sin(pitch) * speed;
        float vz = MathHelper.cos(pitch) * MathHelper.cos(yaw) * speed;
        float solid = (Util.rand.nextFloat());
/*        ClientProxy.particleRenderer
            .spawnParticle(Minecraft.getMinecraft().world, LibRegistry.PARTICLE_SMOKE, nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"), vx * 0.25f,
                Math.abs(vy * 0.5f), vz * 0.25f, nbt.getInteger("lifetime"), 0.25f * solid, 0.25f * solid, 0.25f * solid, 0.5f * solid,
                nbt.getDouble("scale") * (0.5f + Util.rand.nextFloat() * 0.5f), 0.75f);
        if (Util.rand.nextInt(4) == 0) {
          ClientProxy.particleRenderer
              .spawnParticle(Minecraft.getMinecraft().world, LibRegistry.PARTICLE_SPARK, nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"), vx,
                  Math.abs(vy * 0.5f), vz, nbt.getInteger("lifetime") * 0.5f, 0.25f, 0.25f, 1.0f, 1.0f,
                  0.5f * Util.rand.nextFloat() * nbt.getDouble("scale") * (0.5f + Util.rand.nextFloat() * 0.5f), 1.5f);
        }*/
      }
      return null;
    });
  }
}
