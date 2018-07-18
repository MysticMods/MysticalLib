package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.util.Util;
import net.minecraft.client.gui.Gui;

public interface IHUDContainer {

  @Nonnull
  void addHUD(float w, float h);

  static void renderBox(int x, int y, int w, int h, int r, int g, int b) {
    int r2 = r / 4;
    int g2 = g / 4;
    int b2 = b / 4;
    Gui.drawRect(x - 2, y - 1, x + w + 2, y + h + 1, Util.intColor(r2, g2, b2));
    Gui.drawRect(x - 1, y - 2, x + w + 1, y + h + 2, Util.intColor(r2, g2, b2));

    Gui.drawRect(x - 1, y - 1, x + w + 1, y + h + 1, Util.intColor(r, g, b));

    Gui.drawRect(x, y, x + w, y + h, Util.intColor(r2, g2, b2));
  }
}
