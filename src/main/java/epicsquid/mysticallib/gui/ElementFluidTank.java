package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.util.FluidTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ElementFluidTank implements IGuiElement {
  public int x, y;
  private IFluidTankProperties tank;
  private ResourceLocation texture = GuiModular.baseTexture;

  public ElementFluidTank(int x, int y, @Nonnull IFluidTankProperties tank) {
    this.x = x;
    this.y = y;
    this.tank = tank;
  }

  public ElementFluidTank(int x, int y, @Nonnull IFluidTankProperties tank, @Nonnull ResourceLocation texture) {
    this.x = x;
    this.y = y;
    this.tank = tank;
    this.texture = texture;
  }

  @Override
  public void draw(@Nonnull GuiContainer gui, float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    if (tank != null && tank.getContents() != null && tank.getContents().getFluid() != null) {
      float coeff = (float) tank.getContents().amount / (float) tank.getCapacity();
      Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().getTextureMapBlocks().LOCATION_BLOCKS_TEXTURE);
      TextureAtlasSprite sprite = FluidTextureUtil.stillTextures.get(tank.getContents().getFluid());
      GlStateManager.enableBlend();
      int height = (int) Math.round((float) 64 * coeff);
      if (sprite != null) {
        int i = 0;
        for (i = 0; i <= height - 16; i += 16) {
          gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y + 1 + (64 - (i + 16)) - (height % 16), sprite, 16, (int) 16);
        }
        int remainder = (height - i);
        double swidth = sprite.getMaxU() - sprite.getMinU();
        double sheight = sprite.getMaxV() - sprite.getMinV();
        sheight = sheight * ((float) (height - i) / 16f);
        GuiModular.drawTexturedModalRectWithCustomUV(gui.getGuiLeft() + x, gui.getGuiTop() + y + 1 + (64) - (height % 16), sprite.getMinU(), sprite.getMinV(),
            sprite.getMinU() + (double) swidth, sprite.getMinV() + (double) sheight, 16, (height - i));
      }
      GlStateManager.disableBlend();
      Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }

    gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 224, 64, 16, 66);
    gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, 208, 64, 16, 66);
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
    if (mouseX >= gui.getGuiLeft() + x && mouseY >= gui.getGuiTop() + y && mouseX < gui.getGuiLeft() + x + 16 && mouseY < gui.getGuiTop() + y + 66
        && tank.getContents() != null) {
      gui.drawHoveringText("" + tank.getContents().amount + " / " + tank.getCapacity(), mouseX, mouseY);
    }
  }

  @Override
  public void onClick(@Nonnull GuiContainer gui, int mouseX, int mouseY) {

  }
}
