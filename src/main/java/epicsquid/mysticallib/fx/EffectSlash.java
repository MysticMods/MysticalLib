package epicsquid.mysticallib.fx;

import org.lwjgl.opengl.GL11;

import epicsquid.mysticallib.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
  public void read(NBTTagCompound tag) {
    super.read(tag);
    yaw = tag.getFloat("yaw");
    pitch = tag.getFloat("pitch");
    slashAngle = tag.getFloat("slashAngle");
    slashRadius = tag.getFloat("slashRadius");
    slashWidth = tag.getFloat("slashWidth");
    slashRange = tag.getFloat("slashRange");
  }

  @Override
  public NBTTagCompound write() {
    NBTTagCompound tag = super.write();
    tag.setFloat("yaw", yaw);
    tag.setFloat("pitch", pitch);
    tag.setFloat("slashAngle", slashAngle);
    tag.setFloat("slashRadius", slashRadius);
    tag.setFloat("slashWidth", slashWidth);
    tag.setFloat("slashRange", slashRange);
    return tag;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void render(float pticks) {
    Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtil.glow_texture);
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder buffer = tess.getBuffer();
    GlStateManager.translate(getInterpX(pticks), getInterpY(pticks), getInterpZ(pticks));
    GlStateManager.rotate(-yaw, 0, 1, 0);
    GlStateManager.rotate(pitch, 1, 0, 0);
    GlStateManager.rotate(-slashAngle, 0, 0, 1);

    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
    RenderUtil.renderSlash(buffer, 0, 0, 0, r, g, b, a * getLifeCoeff(pticks), slashRadius, slashWidth, slashRange);
    tess.draw();
  }

}
