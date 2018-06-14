package epicsquid.mysticallib.fx;

import org.lwjgl.opengl.GL11;

import epicsquid.mysticallib.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EffectBeam extends Effect {
  public double x2 = 0;
  public double y2 = 0;
  public double z2 = 0;
  public float thickness = 0;

  public EffectBeam() {
  }

  public EffectBeam(int id) {
    super(id);
  }

  public EffectBeam setTarget(double x2, double y2, double z2) {
    this.x2 = x2;
    this.y2 = y2;
    this.z2 = z2;
    return this;
  }

  public EffectBeam setThickness(float thick) {
    this.thickness = thick;
    return this;
  }

  @Override
  public void read(NBTTagCompound tag) {
    super.read(tag);
    x2 = tag.getDouble("x2");
    y2 = tag.getDouble("y2");
    z2 = tag.getDouble("z2");
    thickness = tag.getFloat("thick");
  }

  @Override
  public NBTTagCompound write() {
    NBTTagCompound tag = super.write();
    tag.setDouble("x2", x2);
    tag.setDouble("y2", y2);
    tag.setDouble("z2", z2);
    tag.setFloat("thick", thickness);
    return tag;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void render(float pticks) {
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder buffer = tess.getBuffer();

    Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtil.beam_texture);
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
    RenderUtil
        .renderBeam(buffer, x, y, z, x2 * 0.1 + x * 0.9, y2 * 0.1 + y * 0.9, z2 * 0.1 + z * 0.9, r, g, b, 0f, r, g, b, a * getLifeCoeff(pticks), thickness,
            30f);
    RenderUtil
        .renderBeam(buffer, x2 * 0.1 + x * 0.9, y2 * 0.1 + y * 0.9, z2 * 0.1 + z * 0.9, x2 * 0.9 + x * 0.1, y2 * 0.9 + y * 0.1, z2 * 0.9 + z * 0.1, r, g, b,
            a * getLifeCoeff(pticks), r, g, b, a * getLifeCoeff(pticks), thickness, 30f);
    RenderUtil
        .renderBeam(buffer, x2 * 0.9 + x * 0.1, y2 * 0.9 + y * 0.1, z2 * 0.9 + z * 0.1, x2, y2, z2, r, g, b, a * getLifeCoeff(pticks), r, g, b, 0f, thickness,
            30f);
    tess.draw();
  }

}
