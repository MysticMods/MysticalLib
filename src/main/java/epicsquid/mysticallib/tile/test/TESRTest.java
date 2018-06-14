package epicsquid.mysticallib.tile.test;

import org.lwjgl.opengl.GL11;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.struct.Vec4d;
import epicsquid.mysticallib.util.Primitives;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class TESRTest extends TileEntitySpecialRenderer<TileTest> {
  public static ResourceLocation TEXTURE = new ResourceLocation(MysticalLib.MODID + ":textures/blocks/belljar.png");

  public void render(TileTest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder buf = tess.getBuffer();

    GlStateManager.disableCull();
    GlStateManager.enableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    int dfunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
    GlStateManager.depthFunc(GL11.GL_LEQUAL);
    int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
    float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
    GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);

    buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

    Primitives.addCubeToBuffer(buf, x + 2d / 16d, y + 36d / 16d, z + 2d / 16d, x + 14d / 16d, y + 48d / 16d, z + 14d / 16d,
        new Vec4d[] { new Vec4d(0, 0, 0.75d, 0.75d), new Vec4d(0, 0, 0.75d, 0.75d), new Vec4d(0, 0, 0.75d, 0.75d), new Vec4d(0, 0, 0.75d, 0.75d),
            new Vec4d(0, 0, 0.75d, 0.75d), new Vec4d(0, 0, 0.75d, 0.75d) }, 1f, 1f, 1f, 1f, true, true, true, true, true, true);

    tess.draw();

    GlStateManager.alphaFunc(func, ref);
    GlStateManager.depthFunc(dfunc);
    GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.disableBlend();
  }
}
