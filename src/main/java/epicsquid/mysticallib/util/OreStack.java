package epicsquid.mysticallib.util;

import javax.annotation.Nonnull;

public class OreStack {

  private @Nonnull String oreId;
  private int amount;

  public OreStack(@Nonnull String oreId, int amount) {
    this.oreId = oreId;
    this.amount = amount;
  }

  @Nonnull
  public String getOreId() {
    return oreId;
  }

  public int getAmount() {
    return amount;
  }
}
