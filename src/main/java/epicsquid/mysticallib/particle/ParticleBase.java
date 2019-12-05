package epicsquid.mysticallib.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ParticleBase extends SpriteTexturedParticle implements IParticle {

  ResourceLocation textureLocation = new ResourceLocation("mysticallib:textures/particles/particle_glow.png");

  public ParticleBase(@Nonnull World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
    super(world, x, y, z, 0, 0, 0);
    this.motionX = vx;
    this.motionY = vy;
    this.motionZ = vz;
    this.width = 1.0f;
    this.canCollide = false;
  }

  @Override
  public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    float f = this.getScale(partialTicks);
    float f1 = 0;
    float f2 = 1;
    float f3 = 0;
    float f4 = 1;
    float f5 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosX, this.posX) - interpPosX);
    float f6 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosY, this.posY) - interpPosY);
    float f7 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosZ, this.posZ) - interpPosZ);
    int i = this.getBrightnessForRender(partialTicks);
    int j = i >> 16 & '\uffff';
    int k = i & '\uffff';
    Vec3d[] avec3d = new Vec3d[]{new Vec3d((double)(-rotationX * f - rotationXY * f), (double)(-rotationZ * f), (double)(-rotationYZ * f - rotationXZ * f)), new Vec3d((double)(-rotationX * f + rotationXY * f), (double)(rotationZ * f), (double)(-rotationYZ * f + rotationXZ * f)), new Vec3d((double)(rotationX * f + rotationXY * f), (double)(rotationZ * f), (double)(rotationYZ * f + rotationXZ * f)), new Vec3d((double)(rotationX * f - rotationXY * f), (double)(-rotationZ * f), (double)(rotationYZ * f - rotationXZ * f))};
    if (this.particleAngle != 0.0F) {
      float f8 = MathHelper.lerp(partialTicks, this.prevParticleAngle, this.particleAngle);
      float f9 = MathHelper.cos(f8 * 0.5F);
      float f10 = (float)((double)MathHelper.sin(f8 * 0.5F) * entityIn.getLookDirection().x);
      float f11 = (float)((double)MathHelper.sin(f8 * 0.5F) * entityIn.getLookDirection().y);
      float f12 = (float)((double)MathHelper.sin(f8 * 0.5F) * entityIn.getLookDirection().z);
      Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

      for(int l = 0; l < 4; ++l) {
        avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
      }
    }

    Minecraft.getInstance().getTextureManager().bindTexture(textureLocation);
    buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex((double)f2, (double)f4).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex((double)f2, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex((double)f1, (double)f4).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();

  }

  public void setTextureLocation(ResourceLocation textureLocation) {
    this.textureLocation = textureLocation;
  }

  @Override
  @Nonnull
  public IParticleRenderType getRenderType() {
    return ParticleRenderer.PARTICLE_RENDERER;
  }

  @Override
  public boolean alive() {
    return true;
  }

}
