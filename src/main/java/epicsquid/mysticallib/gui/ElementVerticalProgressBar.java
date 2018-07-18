package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class ElementVerticalProgressBar implements IGuiElement {
  private int x, y, u1, v1, w, h, u2, v2;
  private int[] data;
  private ResourceLocation texture = GuiModular.baseTexture;

  public ElementVerticalProgressBar(int x, int y, int u1, int v1, int w, int h, int u2, int v2, int[] data) {
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

  public ElementVerticalProgressBar(int x, int y, int u1, int v1, int w, int h, int u2, int v2, int[] data, @Nonnull ResourceLocation texture) {
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
  public void draw(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    if (data.length >= 2) {
      float coeff = 1.0f - (float) data[0] / (float) data[1];
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, u1, v1, w, h);
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y + (int) Math.round((float) h * coeff), u2, v2 + (int) Math.round((float) h * coeff),
          w, (int) Math.round((float) h * (1.0f - coeff)));
    }
  }

  @Override
  public void drawTooltip(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    //
  }

  @Override
  public void onClick(@Nonnull GuiContainer gui, int mouseX, int mouseY) {

  }
}
