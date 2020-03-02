package epicsquid.mysticallib.fx;

import com.mojang.blaze3d.platform.GlStateManager;
import epicsquid.mysticallib.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.CompoundNBT;
import org.lwjgl.opengl.GL11;

public class EffectSlash extends Effect {
  public float yaw = 0, pitch = 0, slashAngle = 0, slashWidth = 0, slashRange = 0, slashRadius = 0;

  public EffectSlash() {
  }

  public EffectSlash(int id) {
    super(id);
  }

  public EffectSlash setSlashProperties(float yaw, float pitch, float angle, float radius, float thickness, float arc) {
    this.yaw = yaw;
    this.pitch = pitch;
    this.slashAngle = angle;
    this.slashRadius = radius;
    this.slashWidth = thickness;
    this.slashRange = arc;
    return this;
  }

  @Override
  public void read(CompoundNBT tag) {
    super.read(tag);
    yaw = tag.getFloat("yaw");
    pitch = tag.getFloat("pitch");
    slashAngle = tag.getFloat("slashAngle");
    slashRadius = tag.getFloat("slashRadius");
    slashWidth = tag.getFloat("slashWidth");
    slashRange = tag.getFloat("slashRange");
  }

  @Override
  public CompoundNBT write() {
    CompoundNBT tag = super.write();
    tag.putFloat("yaw", yaw);
    tag.putFloat("pitch", pitch);
    tag.putFloat("slashAngle", slashAngle);
    tag.putFloat("slashRadius", slashRadius);
    tag.putFloat("slashWidth", slashWidth);
    tag.putFloat("slashRange", slashRange);
    return tag;
  }

  @Override
  public void render(float pticks) {
    Minecraft.getInstance().textureManager.bindTexture(RenderUtil.glow_texture);
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder buffer = tess.getBuffer();
    GlStateManager.translated(getInterpX(pticks), getInterpY(pticks), getInterpZ(pticks));
    GlStateManager.rotatef(-yaw, 0, 1, 0);
    GlStateManager.rotatef(pitch, 1, 0, 0);
    GlStateManager.rotatef(-slashAngle, 0, 0, 1);

    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
    RenderUtil.renderSlash(buffer, 0, 0, 0, r, g, b, a * getLifeCoeff(pticks), slashRadius, slashWidth, slashRange);
    tess.draw();
  }

}
