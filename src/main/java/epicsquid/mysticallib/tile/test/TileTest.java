package epicsquid.mysticallib.tile.test;

import epicsquid.mysticallib.gui.IHUDContainer;
import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class TileTest extends TileModular implements IHUDContainer {

  @Override
  public void addHUD(float w, float h) {
    RenderHelper.disableStandardItemLighting();
    IHUDContainer.renderBox((int) w / 2 - 8, (int) h / 2 + 8, 16, 16, 48, 112, 64);
    RenderHelper.enableGUIStandardItemLighting();
    Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(Blocks.STONE, 1), (int) w / 2 - 8, (int) h / 2 + 8);
    RenderHelper.disableStandardItemLighting();
  }

}
