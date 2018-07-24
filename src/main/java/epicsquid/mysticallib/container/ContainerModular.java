package epicsquid.mysticallib.container;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

// FIXME: This is just spaghetti
public class ContainerModular extends Container {
  public @Nonnull TileModular tile;
  private @Nonnull ItemStackHandler inventory;

  @Override
  public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
    return true;
  }

  public ContainerModular(@Nonnull TileModular tile) {
    super();
    this.tile = tile;
    if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
      inventory = (ItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    } else {
      inventory = new ItemStackHandler();
    }
  }

  @Nonnull
  public ContainerModular tryAddSlot(int index, int x, int y) {
    if (index < inventory.getSlots()) {
      this.addSlotToContainer(new SlotItemHandler(inventory, index, x, y));
    }
    return this;
  }

  public void initContainerSlots(@Nonnull IItemHandler inv) {

  }

  public ContainerModular initPlayerInventory(@Nonnull InventoryPlayer playerInv, int offX, int offY) {
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

  // TODO Move this to capability if/when it becomes relevant
//  @Override
//  @Nonnull
//  public ItemStack transferStackInSlot(@Nonnull EntityPlayer p, int i) {
//    ItemStack itemstack = ItemStack.EMPTY;
//    Slot slot = inventorySlots.get(i);
//    if (i >= slot.inventory.getSizeInventory()) {
//      return itemstack;
//    }
//    if (slot.getHasStack()) {
//      if (i < slot.inventory.getSizeInventory()) {
//        return itemstack;
//      }
//      ItemStack itemstack1 = slot.getStack();
//      itemstack = itemstack1.copy();
//      if (i < inventory.getSizeInventory()) {
//        if (!mergeItemStack(itemstack1, inventory.getSizeInventory(), inventorySlots.size(), false) && inventory.isItemValidForSlot(i, itemstack1) && inventory
//            .canInsertToSlot(i)) {
//          return ItemStack.EMPTY;
//        }
//      } else {
//        boolean isAllowed = false;
//        for (int j = inventory.getSizeInventory() - 1; j >= 0; j--) {
//          if (inventory.isItemValidForSlot(j, itemstack1) && inventory.canInsertToSlot(j)) {
//            isAllowed = true;
//          }
//        }
//        if (!isAllowed) {
//          return ItemStack.EMPTY;
//        } else if (!mergeItemStack(itemstack1, 0, inventory.getSizeInventory(), false)) {
//          return ItemStack.EMPTY;
//        }
//      }
//      if (itemstack1.getCount() == 0) {
//        slot.putStack(ItemStack.EMPTY);
//      } else {
//        slot.onSlotChanged();
//      }
//    }
//    return itemstack;
//  }

}
