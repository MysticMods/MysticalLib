package epicsquid.mysticallib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class ElementHorizontalProgressBar implements IGuiElement {
  public int x = 0, y = 0, u1 = 0, v1 = 0, w = 0, h = 0, u2 = 0, v2 = 0;
  public int[] data = null;
  public ResourceLocation texture = GuiModular.baseTexture;

  public ElementHorizontalProgressBar(int x, int y, int u1, int v1, int w, int h, int u2, int v2, int[] data) {
    this.x = x;
    this.y = y;
    this.u1 = u1;
    this.v1 = v1;
    this.w = w;
    this.h = h;
    this.u2 = u2;
    this.v2 = v2;
    this.data = data;
  }

  public ElementHorizontalProgressBar(int x, int y, int u1, int v1, int w, int h, int u2, int v2, int[] data, ResourceLocation texture) {
    this.x = x;
    this.y = y;
    this.u1 = u1;
    this.v1 = v1;
    this.w = w;
    this.h = h;
    this.u2 = u2;
    this.v2 = v2;
    this.data = data;
    this.texture = texture;
  }

  @Override
  public void draw(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    if (data.length >= 2) {
      float coeff = 1.0f - (float) data[0] / (float) data[1];
      g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, u1, v1, w, h);
      g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, u2, v2, (int) Math.round((float) w * (1.0f - coeff)), h);
    }
  }

  @Override
  public void drawTooltip(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
    //
  }

  @Override
  public void onClick(GuiContainer g, int mouseX, int mouseY) {

  }
}
