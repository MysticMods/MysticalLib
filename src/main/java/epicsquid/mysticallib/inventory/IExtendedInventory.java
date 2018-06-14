package epicsquid.mysticallib.inventory;

import net.minecraft.inventory.IInventory;

public interface IExtendedInventory extends IInventory {
  public boolean canExtractFromSlot(int slot);

  public boolean canInsertToSlot(int slot);
}
