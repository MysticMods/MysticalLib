package epicsquid.mysticallib.particle;

import epicsquid.mysticallib.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

// TODO Investigate vanilla IParticleFactory (see EnchantmentTableParticle)
public class ParticleBase extends SpriteTexturedParticle implements IParticle {

  private int lifetime = 0;

  public ParticleBase(@Nonnull World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
    super(world, x, y, z, 0, 0, 0);
    this.motionX = vx;
    this.motionY = vy;
    this.motionZ = vz;
    if (data.length >= 1) {
      lifetime = (int) data[0];
    }
    this.maxAge = lifetime;
    ResourceLocation texture = ParticleRegistry.particleTextures.get(Util.getLowercaseClassName(getClass()));
    TextureAtlasSprite sprite = Minecraft.getInstance().getTextureMap().getAtlasSprite(texture.toString());
    this.setSprite(sprite);
    this.width = 1.0f;
    this.canCollide = false;
  }

  @Override
  @Nonnull
  public IParticleRenderType getRenderType() {
    return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
  }

  @Override
  public void tick() {
    super.tick();
    this.motionX *= 0.95f;
    this.motionY *= 0.95f;
    this.motionZ *= 0.95f;
    lifetime--;
  }

  protected void tickNoMotion() {
    super.tick();
    lifetime--;
  }

  protected void setDead() {
    this.lifetime = 0;
  }

  @Override
  public boolean alive() {
    return lifetime > 0;
  }

  @Override
  public boolean isAdditive() {
    return false;
  }

  @Override
  public boolean renderThroughBlocks() {
    return false;
  }
}
