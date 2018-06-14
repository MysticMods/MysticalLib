package epicsquid.mysticallib.gui;

import epicsquid.mysticallib.network.MessageToggleModuleOutput;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class ElementToggleIOButton implements IGuiElement {
  public int x = 0, y = 0;
  public TileModular tile = null;
  public String module = "";
  public ResourceLocation texture = GuiModular.baseTexture;

  public ElementToggleIOButton(int x, int y, TileModular tile, String module) {
    this.x = x;
    this.y = y;
    this.tile = tile;
    this.module = module;
  }

  public ElementToggleIOButton(int x, int y, TileModular tile, String module, ResourceLocation texture) {
    this.x = x;
    this.y = y;
    this.tile = tile;
    this.module = module;
    this.texture = texture;
  }

  @Override
  public void draw(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
    if (tile.validIOModules.contains(module)) {
      g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 176, 144, 16, 16);
    } else {
      g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 192, 144, 16, 16);
    }
    if (mouseX >= g.getGuiLeft() + x && mouseY >= g.getGuiTop() + y && mouseX < g.getGuiLeft() + x + 16 && mouseY < g.getGuiTop() + y + 16) {
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 208, 144, 16, 16);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    }
  }

  @Override
  public void drawTooltip(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    if (mouseX >= g.getGuiLeft() + x && mouseY >= g.getGuiTop() + y && mouseX < g.getGuiLeft() + x + 16 && mouseY < g.getGuiTop() + y + 16) {
      String moduleName = I18n.format(TileBase.getTileName(tile.getClass()) + ".module." + module + ".name");
      g.drawHoveringText(I18n.format("elulib.gui.toggleoutput", moduleName), mouseX, mouseY);
    }
  }

  @Override
  public void onClick(GuiContainer g, int mouseX, int mouseY) {
    if (mouseX >= g.getGuiLeft() + x && mouseY >= g.getGuiTop() + y && mouseX < g.getGuiLeft() + x + 16 && mouseY < g.getGuiTop() + y + 16) {
      PacketHandler.INSTANCE.sendToServer(new MessageToggleModuleOutput(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), module));
    }
  }
}
