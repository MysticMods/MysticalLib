package epicsquid.mysticallib.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.setup.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleRenderer {
  private CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<>();

  public synchronized void updateParticles() {
    boolean doRemove;
    List<Particle> toRemove = new ArrayList<>();

    for (Particle particle : particles) {
      doRemove = true;
      if (particle != null) {
        if (particle instanceof IParticle) {
          if (((IParticle) particle).alive()) {
            particle.tick();
            doRemove = false;
          }
        }
      }
      if (doRemove) {
        toRemove.add(particle);
      }
    }

    if (!toRemove.isEmpty()) {
      particles.removeAll(toRemove);
    }
  }

  public synchronized void renderParticles(float partialTicks) {
    ActiveRenderInfo renderInfo = MysticalLib.proxy.getClientActiveRenderInfo();
    PlayerEntity player = MysticalLib.proxy.getClientPlayer();

    if (player != null) {
      // TODO: work out what this variable actually was
      int i = 0;
      float f = MathHelper.cos(player.rotationYaw * 0.017453292F) * (float) (1 - i * 2);
      float f1 = MathHelper.sin(player.rotationYaw * 0.017453292F) * (float) (1 - i * 2);
      float f2 = -f1 * MathHelper.sin(player.rotationPitch * 0.017453292F) * (float) (1 - i * 2);
      float f3 = f * MathHelper.sin(player.rotationPitch * 0.017453292F) * (float) (1 - i * 2);
      float f4 = MathHelper.cos(player.rotationPitch * 0.017453292F);
      Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
      Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
      Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
//      Particle.cameraViewDir = player.getLook(partialTicks);
      GlStateManager.enableAlphaTest();
      GlStateManager.enableBlend();
      GlStateManager.alphaFunc(516, 0.003921569F);
      GlStateManager.disableCull();

      GlStateManager.depthMask(false);

      Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      Tessellator tess = Tessellator.getInstance();
      BufferBuilder buffer = tess.getBuffer();

      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      for (Particle particle : particles) {
        if (particle instanceof IParticle) {
          if (!((IParticle) particle).isAdditive()) {
            particle.renderParticle(buffer, renderInfo, partialTicks, f, f4, f1, f2, f3);
          }
        }
      }
      tess.draw();

      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      for (Particle particle : particles) {
        if (particle != null) {
          if (((IParticle) particle).isAdditive()) {
            particle.renderParticle(buffer, renderInfo, partialTicks, f, f4, f1, f2, f3);
          }
        }
      }
      tess.draw();

      GlStateManager.disableDepthTest();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      for (Particle particle : particles) {
        if (particle instanceof IParticle) {
          if (!((IParticle) particle).isAdditive() && ((IParticle) particle).renderThroughBlocks()) {
            particle.renderParticle(buffer, renderInfo, partialTicks, f, f4, f1, f2, f3);
          }
        }
      }
      tess.draw();

      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      for (Particle particle : particles) {
        if (particle != null) {
          if (((IParticle) particle).isAdditive() && ((IParticle) particle).renderThroughBlocks()) {
            particle.renderParticle(buffer, renderInfo, partialTicks, f, f4, f1, f2, f3);
          }
        }
      }
      tess.draw();
      GlStateManager.enableDepthTest();

      GlStateManager.enableCull();
      GlStateManager.depthMask(true);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.disableBlend();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableAlphaTest();
    }
  }

  public void spawnParticle(World world, String particle, double x, double y, double z, double vx, double vy, double vz, double... data) {
    if (MysticalLib.proxy instanceof ClientProxy) {
      try {
        particles.add(ParticleRegistry.getParticles().get(particle).newInstance(world, x, y, z, vx, vy, vz, data));
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
