package epicsquid.mysticallib.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class IInvWrapper implements IInventory {
  private final IItemHandler handler;

  public IInvWrapper(IItemHandler handler) {
    this.handler = handler;
  }

  @Override
  public int getSizeInventory() {
    return handler.getSlots();
  }

  @Override
  public boolean isEmpty() {
    for (int i = 0; i < handler.getSlots(); i++) {
      ItemStack inSlot = handler.getStackInSlot(i);
      if (!inSlot.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getStackInSlot(int index) {
    return handler.getStackInSlot(index);
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {
    return handler.extractItem(index, count, false);
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {
    return handler.extractItem(index, handler.getSlotLimit(index), false);
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {
    handler.extractItem(index, handler.getSlotLimit(index), false);
    handler.insertItem(index, stack, false);
  }

  @Override
  public void markDirty() {
  }

  @Override
  public boolean isUsableByPlayer(PlayerEntity player) {
    return true;
  }

  @Override
  public void clear() {
    for (int i = 0; i < handler.getSlots(); i++) {
      handler.extractItem(i, handler.getSlotLimit(i), false);
    }
  }
}
