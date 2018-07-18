package epicsquid.mysticallib.gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.container.ContainerModular;
import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiModular extends GuiContainer {
  static ResourceLocation baseTexture = new ResourceLocation("mysticallib:textures/gui/container.png");
  private ResourceLocation texture = new ResourceLocation("mysticallib:textures/gui/container.png");

  private ArrayList<IGuiElement> elements = new ArrayList<>();

  public GuiModular(@Nonnull ContainerModular inventorySlotsIn, int width, int height) {
    super(inventorySlotsIn);
    this.xSize = width;
    this.ySize = height;
  }

  @Override
  public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
    super.mouseClicked(mouseX, mouseY, button);
    for (IGuiElement e : elements) {
      e.onClick(this, mouseX, mouseY);
    }
  }

  @Override
  public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    drawElements(partialTicks, mouseX, mouseY);
  }

  protected void drawElements(float partialTicks, int mouseX, int mouseY) {
    for (IGuiElement e : elements) {
      e.draw(this, partialTicks, mouseX, mouseY);
    }

    for (Slot s : inventorySlots.inventorySlots) {
//      if (s instanceof SlotInventoryDefault && ((SlotInventoryDefault) s).isBig()) {
//        drawTexturedModalRect(guiLeft + s.xPos - 5, guiTop + s.yPos - 5, 176, 32, 26, 26);
//      } else {
        drawTexturedModalRect(guiLeft + s.xPos - 1, guiTop + s.yPos - 1, 208, 32, 18, 18);
//      }
    }
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.drawDefaultBackground();
    super.drawScreen(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);
    for (IGuiElement e : elements) {
      e.drawTooltip(this, 0, mouseX, mouseY);
    }
  }

  @Nonnull
  public GuiModular addElement(@Nonnull IGuiElement e) {
    elements.add(e);
    return this;
  }

  public static void drawTexturedModalRectWithCustomUV(int xCoord, int yCoord, double minU, double minV, double maxU, double maxV, int widthIn, int heightIn) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + heightIn), (double) 0).tex(minU, maxV).endVertex();
    bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + heightIn), (double) 0).tex(maxU, maxV).endVertex();
    bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + 0), (double) 0).tex(maxU, minV).endVertex();
    bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + 0), (double) 0).tex(minU, minV).endVertex();
    tessellator.draw();
  }
}
