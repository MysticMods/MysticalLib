package epicsquid.mysticallib.particle.particles;

import epicsquid.mysticallib.particle.ParticleBase;
import epicsquid.mysticallib.util.Util;
import net.minecraft.world.World;

public class ParticleRain extends ParticleBase {
  public float colorR = 0;
  public float colorG = 0;
  public float colorB = 0;
  public float initScale = 0;
  public float initAlpha = 0;
  public float angularVelocity = 0;

  public ParticleRain(World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
    super(world, x, y, z, vx, vy, vz, data);
    this.colorR = 1f;
    this.colorG = 1f;
    this.colorB = 1f;
    this.setRBGColorF(colorR, colorG, colorB);
    this.setAlphaF((float) data[1]);
    this.initAlpha = (float) data[1];
    this.particleScale = (float) data[2];
    this.initScale = (float) data[2];
    this.angularVelocity = 0; // (float) data[6] * (Util.rand.nextFloat() - 0.5f);
    this.prevParticleAngle = particleAngle;
    this.particleAngle = 0; // Util.rand.nextFloat() * 2.0f * (float) Math.PI;
    this.particleGravity = 0.6f;
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    this.prevParticleAngle = particleAngle;
    this.particleAngle += this.angularVelocity;
    float lifeCoeff = (float) this.particleAge / (float) this.particleMaxAge;
    this.particleScale = initScale - initScale * lifeCoeff;
    //this.particleAlpha = initAlpha * (1.0f - lifeCoeff);
  }

  @Override
  public boolean isAdditive() {
    return false;
  }
}
