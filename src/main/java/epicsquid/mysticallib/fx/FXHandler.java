package epicsquid.mysticallib.fx;

import java.util.function.Function;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.event.RegisterFXEvent;
import epicsquid.mysticallib.proxy.ClientProxy;
import epicsquid.mysticallib.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FXHandler {
  public static int FX_BEAM, FX_SLASH, FX_BURST;

  @SubscribeEvent
  public void onRegisterFX(RegisterFXEvent event) {
    FX_BEAM = FXRegistry.registerEffect(new Function<NBTTagCompound, Void>() {
      @Override
      public Void apply(NBTTagCompound t) {
        EffectBeam beam = new EffectBeam();
        beam.read(t);
        EffectManager.addEffect(beam);
        return null;
      }
    });
    FX_SLASH = FXRegistry.registerEffect(new Function<NBTTagCompound, Void>() {
      @Override
      public Void apply(NBTTagCompound t) {
        EffectSlash beam = new EffectSlash();
        beam.read(t);
        EffectManager.addEffect(beam);
        return null;
      }
    });
    FX_BURST = FXRegistry.registerEffect(new Function<NBTTagCompound, Void>() {
      @SideOnly(Side.CLIENT)
      @Override
      public Void apply(NBTTagCompound t) {
        for (int i = 0; i < t.getInteger("count"); i++) {
          float yaw = Util.rand.nextFloat() * 2.0f * (float) Math.PI;
          float pitch = Util.rand.nextFloat() * 2.0f * (float) Math.PI;
          float speed = Util.rand.nextFloat() * (float) t.getDouble("speed");
          float vx = MathHelper.cos(pitch) * MathHelper.sin(yaw) * speed;
          float vy = MathHelper.sin(pitch) * speed;
          float vz = MathHelper.cos(pitch) * MathHelper.cos(yaw) * speed;
          float solid = (Util.rand.nextFloat());
          ClientProxy.particleRenderer
              .spawnParticle(Minecraft.getMinecraft().world, LibRegistry.PARTICLE_SMOKE, t.getDouble("x"), t.getDouble("y"), t.getDouble("z"), vx * 0.25f,
                  Math.abs(vy * 0.5f), vz * 0.25f, new double[] { t.getInteger("lifetime"), 0.25f * solid, 0.25f * solid, 0.25f * solid, 0.5f * solid,
                      t.getDouble("scale") * (0.5f + Util.rand.nextFloat() * 0.5f), 0.75f });
          if (Util.rand.nextInt(4) == 0) {
            ClientProxy.particleRenderer
                .spawnParticle(Minecraft.getMinecraft().world, LibRegistry.PARTICLE_SPARK, t.getDouble("x"), t.getDouble("y"), t.getDouble("z"), vx,
                    Math.abs(vy * 0.5f), vz, new double[] { t.getInteger("lifetime") * 0.5f, 0.25f, 0.25f, 1.0f, 1.0f,
                        0.5f * Util.rand.nextFloat() * t.getDouble("scale") * (0.5f + Util.rand.nextFloat() * 0.5f), 1.5f });
          }
        }
        return null;
      }
    });
  }
}
