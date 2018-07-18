package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

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
  private int x, y;
  private TileModular tile;
  private String module;
  private ResourceLocation texture = GuiModular.baseTexture;

  public ElementToggleIOButton(int x, int y, @Nonnull TileModular tile, @Nonnull String module) {
    this.x = x;
    this.y = y;
    this.tile = tile;
    this.module = module;
  }

  public ElementToggleIOButton(int x, int y, @Nonnull TileModular tile, @Nonnull String module, @Nonnull ResourceLocation texture) {
    this.x = x;
    this.y = y;
    this.tile = tile;
    this.module = module;
    this.texture = texture;
  }

  @Override
  public void draw(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
//    if (tile.validIOModules.contains(module)) {
//      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 176, 144, 16, 16);
//    } else
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 192, 144, 16, 16);

    if (mouseX >= gui.getGuiLeft() + x && mouseY >= gui.getGuiTop() + y && mouseX < gui.getGuiLeft() + x + 16 && mouseY < gui.getGuiTop() + y + 16) {
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 208, 144, 16, 16);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    }
  }

  @Override
  public void drawTooltip(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    if (mouseX >= gui.getGuiLeft() + x && mouseY >= gui.getGuiTop() + y && mouseX < gui.getGuiLeft() + x + 16 && mouseY < gui.getGuiTop() + y + 16) {
      String moduleName = I18n.format(TileBase.getTileName(tile.getClass()) + ".module." + module + ".name");
      gui.drawHoveringText(I18n.format("mysticallib.gui.toggleoutput", moduleName), mouseX, mouseY);
    }
  }

  @Override
  public void onClick(GuiContainer gui, int mouseX, int mouseY) {
    if (mouseX >= gui.getGuiLeft() + x && mouseY >= gui.getGuiTop() + y && mouseX < gui.getGuiLeft() + x + 16 && mouseY < gui.getGuiTop() + y + 16) {
      PacketHandler.INSTANCE.sendToServer(new MessageToggleModuleOutput(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), module));
    }
  }
}
