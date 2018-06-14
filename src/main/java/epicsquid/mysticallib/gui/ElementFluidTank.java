package epicsquid.mysticallib.gui;

import epicsquid.mysticallib.util.FluidTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ElementFluidTank implements IGuiElement {
  public int x = 0, y = 0;
  public IFluidTankProperties tank = null;
  public ResourceLocation texture = GuiModular.baseTexture;

  public ElementFluidTank(int x, int y, IFluidTankProperties tank) {
    this.x = x;
    this.y = y;
    this.tank = tank;
  }

  public ElementFluidTank(int x, int y, IFluidTankProperties tank, ResourceLocation texture) {
    this.x = x;
    this.y = y;
    this.tank = tank;
    this.texture = texture;
  }

  @Override
  public void draw(GuiContainer g, float partialTicks, int mouseX, int mouseY) {
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
          g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y + 1 + (64 - (i + 16)) - (height % 16), sprite, 16, (int) 16);
        }
        int remainder = (height - i);
        double swidth = sprite.getMaxU() - sprite.getMinU();
        double sheight = sprite.getMaxV() - sprite.getMinV();
        sheight = sheight * ((float) (height - i) / 16f);
        GuiModular.drawTexturedModalRectWithCustomUV(g.getGuiLeft() + x, g.getGuiTop() + y + 1 + (64) - (height % 16), sprite.getMinU(), sprite.getMinV(),
            sprite.getMinU() + (double) swidth, sprite.getMinV() + (double) sheight, 16, (height - i));
      }
      GlStateManager.disableBlend();
      Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }

    g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 224, 64, 16, 66);
    g.drawTexturedModalRect(g.getGuiLeft() + x, g.getGuiTop() + y, 208, 64, 16, 66);
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
    if (mouseX >= g.getGuiLeft() + x && mouseY >= g.getGuiTop() + y && mouseX < g.getGuiLeft() + x + 16 && mouseY < g.getGuiTop() + y + 66
        && tank.getContents() != null) {
      g.drawHoveringText("" + tank.getContents().amount + " / " + tank.getCapacity(), mouseX, mouseY);
    }
  }

  @Override
  public void onClick(GuiContainer g, int mouseX, int mouseY) {

  }
}
