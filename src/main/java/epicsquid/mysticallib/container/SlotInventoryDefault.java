package epicsquid.mysticallib.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class SlotInventoryDefault extends Slot {
  boolean big = false;

  public SlotInventoryDefault(IInventory inventoryIn, int index, int xPosition, int yPosition, boolean isBig) {
    super(inventoryIn, index, xPosition, yPosition);
    this.big = isBig;
  }

  @Override
  public boolean isItemValid(ItemStack stack) {
    return inventory.isItemValidForSlot(this.slotNumber, stack);
  }

  @Override
  public void onSlotChanged() {
    if (inventory instanceof TileEntity) {
      ((TileEntity) inventory).markDirty();
    }
  }

  public boolean isBig() {
    return big;
  }
}
