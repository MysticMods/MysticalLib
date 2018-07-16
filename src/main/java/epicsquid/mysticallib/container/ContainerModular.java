package epicsquid.mysticallib.container;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.tile.TileModular;
import epicsquid.mysticallib.tile.module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerModular extends Container {
  public @Nonnull TileModular tile;
  private @Nonnull IExtendedInventory inventory;

  @Override
  public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
    return true;
  }

  public ContainerModular(@Nonnull TileModular tile) {
    super();
    inventory = new DummyInventory();
    for (Module m : tile.modules.values()) {
      if (m instanceof IExtendedInventory) {
        inventory = (IExtendedInventory) m;
      }
    }
  }

  @Nonnull
  public ContainerModular tryAddSlot(int index, int x, int y) {
    if (index < inventory.getSizeInventory()) {
      this.addSlotToContainer(new SlotInventoryDefault(inventory, index, x, y, false));
    }
    return this;
  }

  @Nonnull
  public ContainerModular tryAddSlot(int index, int x, int y, boolean big) {
    if (index < inventory.getSizeInventory()) {
      this.addSlotToContainer(new SlotInventoryDefault(inventory, index, x, y, big));
    }
    return this;
  }

  public void initContainerSlots(IInventory containerInv) {

  }

  public ContainerModular initPlayerInventory(IInventory playerInv, int offX, int offY) {
    for (int y = 0; y < 3; ++y) {
      for (int x = 0; x < 9; ++x) {
        this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, offX + 8 + x * 18, offY + 84 + y * 18));
      }
    }

    for (int x = 0; x < 9; ++x) {
      this.addSlotToContainer(new Slot(playerInv, x, offX + 8 + x * 18, offY + 142));
    }
    return this;
  }

  @Override
  @Nonnull
  public ItemStack transferStackInSlot(@Nonnull EntityPlayer p, int i) {
    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = (Slot) inventorySlots.get(i);
    if (i >= slot.inventory.getSizeInventory()) {
      return itemstack;
    }
    if (slot != null && slot.getHasStack()) {
      if (slot.inventory instanceof IExtendedInventory && i < slot.inventory.getSizeInventory() && !((IExtendedInventory) slot.inventory)
          .canExtractFromSlot(i)) {
        return itemstack;
      }
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      if (i < inventory.getSizeInventory()) {
        if (!mergeItemStack(itemstack1, inventory.getSizeInventory(), inventorySlots.size(), false) && inventory.isItemValidForSlot(i, itemstack1) && inventory
            .canInsertToSlot(i)) {
          return ItemStack.EMPTY;
        }
      } else {
        boolean isAllowed = false;
        for (int j = inventory.getSizeInventory() - 1; j >= 0; j--) {
          if (inventory.isItemValidForSlot(j, itemstack1) && inventory.canInsertToSlot(j)) {
            isAllowed = true;
          }
        }
        if (!isAllowed) {
          return ItemStack.EMPTY;
        } else if (!mergeItemStack(itemstack1, 0, inventory.getSizeInventory(), false)) {
          return ItemStack.EMPTY;
        }
      }
      if (itemstack1.getCount() == 0) {
        slot.putStack(ItemStack.EMPTY);
      } else {
        slot.onSlotChanged();
      }
    }
    return itemstack;
  }

}
