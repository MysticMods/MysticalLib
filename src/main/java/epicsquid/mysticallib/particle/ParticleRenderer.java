package epicsquid.mysticallib.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class ParticleRenderer {

  private static void beginRenderGlobal(BufferBuilder buffer, TextureManager textureManager){
    GlStateManager.enableAlphaTest();
    GlStateManager.enableBlend();
    GlStateManager.alphaFunc(516, 0.003921569F);
    GlStateManager.disableCull();
    GlStateManager.depthMask(false);
  }

  private static void finishRenderGlobal(Tessellator tess){
    tess.draw();
    GlStateManager.enableDepthTest();

    GlStateManager.enableCull();
    GlStateManager.depthMask(true);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
    GlStateManager.disableBlend();
    GlStateManager.alphaFunc(516, 0.1F);
    GlStateManager.enableAlphaTest();
  }

  public static IParticleRenderType PARTICLE_IS_ADDITIVE = new IParticleRenderType() {
    @Override
    public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
      beginRenderGlobal(buffer, textureManager);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE.param);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }
    @Override
    public void finishRender(Tessellator tess) {
      finishRenderGlobal(tess);
    }
    public String toString() {
      return "PARTICLE_IS_ADDITIVE";
    }
  };

  public static IParticleRenderType PARTICLE_IS_ADDITIVE_SEE_THROUGH_BLOCKS = new IParticleRenderType() {
    @Override
    public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
      beginRenderGlobal(buffer, textureManager);
      GlStateManager.disableDepthTest();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE.param);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }
    @Override
    public void finishRender(Tessellator tess) {
      finishRenderGlobal(tess);
    }
    public String toString() {
      return "PARTICLE_IS_ADDITIVE";
    }
  };

  public static IParticleRenderType PARTICLE_SEE_THROUGH_BLOCKS = new IParticleRenderType() {
    @Override
    public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
      beginRenderGlobal(buffer, textureManager);
      GlStateManager.disableDepthTest();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }
    @Override
    public void finishRender(Tessellator tess) {
      finishRenderGlobal(tess);
    }
    public String toString() {
      return "PARTICLE_IS_ADDITIVE";
    }
  };

  public static IParticleRenderType PARTICLE_RENDERER = new IParticleRenderType() {
    @Override
    public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
      beginRenderGlobal(buffer, textureManager);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
      buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }
    @Override
    public void finishRender(Tessellator tess) {
      finishRenderGlobal(tess);
    }
    public String toString() {
      return "PARTICLE_RENDERER";
    }
  };
}
