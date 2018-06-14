package epicsquid.mysticallib.gui;

import epicsquid.mysticallib.tile.module.ModuleEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ElementEnergyBar implements IGuiElement {
  public int x = 0, y = 0;
  public ModuleEnergy module = null;
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
  public void draw(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    float coeff = 1.0f - (float) module.battery.getEnergyStored() / (float) module.battery.getMaxEnergyStored();
    g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 176, 64, 16, 66);
    g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y + 1 + (int) Math.round((float) 64 * coeff), 192, 65 + (int) Math.round((float) 64 * coeff),
        16, (int) Math.round((float) 64 * (1.0f - coeff)));
    if (mouseX >= g.getGuiLeft() + x && mouseY >= g.getGuiTop() + y && mouseX < g.getGuiLeft() + x + 16 && mouseY < g.getGuiTop() + y + 66) {
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 240, 64, 16, 66);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    }
  }

  @Override
  public void drawTooltip(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
    if (mouseX >= g.getGuiLeft() + x && mouseY >= g.getGuiTop() + y && mouseX < g.getGuiLeft() + x + 16 && mouseY < g.getGuiTop() + y + 66) {
      g.drawHoveringText("" + module.battery.getEnergyStored() + " / " + module.battery.getMaxEnergyStored(), mouseX, mouseY);
    }
  }

  @Override
  public void onClick(GuiContainer g, int mouseX, int mouseY) {

  }
}
