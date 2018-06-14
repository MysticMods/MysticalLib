package epicsquid.mysticallib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public interface IGuiElement {
  public void draw(GuiContainer g, float partialTicks, int mouseX, int mouseY);

  public void drawTooltip(GuiContainer g, float partialTicks, int mouseX, int mouseY);

  public void onClick(GuiContainer g, int mouseX, int mouseY);
}
