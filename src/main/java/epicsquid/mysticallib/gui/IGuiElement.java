package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.inventory.GuiContainer;

public interface IGuiElement {

  void draw(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY);

  void drawTooltip(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY);

  void onClick(@Nonnull GuiContainer gui, int mouseX, int mouseY);
}
