package epicsquid.mysticallib.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryHandler extends ItemStackHandler {
  IExtendedInventory inventory = null;

  public InventoryHandler(int size, @Nonnull IExtendedInventory inventory) {
    super(size);
    this.inventory = inventory;
  }

  @Override
  public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
    if (inventory.canInsertToSlot(slot) && inventory.isItemValidForSlot(slot, stack)) {
      return super.insertItem(slot, stack, simulate);
    } else {
      return stack;
    }
  }

  @Override
  public ItemStack extractItem(int slot, int count, boolean simulate) {
    if (inventory.canExtractFromSlot(slot)) {
      return super.extractItem(slot, count, simulate);
    } else {
      return ItemStack.EMPTY;
    }
  }
}
