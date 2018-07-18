package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.tile.module.ModuleEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ElementEnergyBar implements IGuiElement {
  public int x, y;
  public ModuleEnergy module;
  public ResourceLocation texture = GuiModular.baseTexture;

  public ElementEnergyBar(int x, int y, ModuleEnergy module) {
    this.x = x;
    this.y = y;
    this.module = module;
  }

  public ElementEnergyBar(int x, int y, ModuleEnergy module, ResourceLocation texture) {
    this.x = x;
    this.y = y;
    this.module = module;
    this.texture = texture;
  }

  @Override
  public void draw(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    float coeff = 1.0f - (float) module.getBattery().getEnergyStored() / (float) module.getBattery().getMaxEnergyStored();
    gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 176, 64, 16, 66);
    gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y + 1 + (int) Math.round((float) 64 * coeff), 192, 65 + (int) Math.round((float) 64 * coeff),
        16, (int) Math.round((float) 64 * (1.0f - coeff)));
    if (mouseX >= gui.getGuiLeft() + x && mouseY >= gui.getGuiTop() + y && mouseX < gui.getGuiLeft() + x + 16 && mouseY < gui.getGuiTop() + y + 66) {
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 240, 64, 16, 66);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    }
  }

  @Override
  public void drawTooltip(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    if (mouseX >= gui.getGuiLeft() + x && mouseY >= gui.getGuiTop() + y && mouseX < gui.getGuiLeft() + x + 16 && mouseY < gui.getGuiTop() + y + 66) {
      gui.drawHoveringText("" + module.getBattery().getEnergyStored() + " / " + module.getBattery().getMaxEnergyStored(), mouseX, mouseY);
    }
  }

  @Override
  public void onClick(@Nonnull GuiContainer gui, int mouseX, int mouseY) {

  }
}
