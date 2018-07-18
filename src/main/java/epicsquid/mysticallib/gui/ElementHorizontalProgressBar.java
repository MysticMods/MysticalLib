package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class ElementHorizontalProgressBar implements IGuiElement {
  private int x, y, u1, v1, w, h, u2, v2;
  private int[] data;
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

  public ElementHorizontalProgressBar(int x, int y, int u1, int v1, int w, int h, int u2, int v2, int[] data, @Nonnull ResourceLocation texture) {
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
  public void draw(GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    if (data.length >= 2) {
      float coeff = 1.0f - (float) data[0] / (float) data[1];
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, u1, v1, w, h);
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, u2, v2, (int) Math.round((float) w * (1.0f - coeff)), h);
    }
  }

  @Override
  public void drawTooltip(GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    //
  }

  @Override
  public void onClick(GuiContainer gui, int mouseX, int mouseY) {

  }
}
