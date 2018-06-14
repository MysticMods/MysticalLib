package epicsquid.mysticallib.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class DummyInventory implements IExtendedInventory {
  @Override
  public int getSizeInventory() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public ItemStack getStackInSlot(int index) {
    return null;
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {
    return null;
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {
    return null;
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {

  }

  @Override
  public int getInventoryStackLimit() {
    return 0;
  }

  @Override
  public void markDirty() {

  }

  @Override
  public boolean isUsableByPlayer(EntityPlayer player) {
    return false;
  }

  @Override
  public void openInventory(EntityPlayer player) {

  }

  @Override
  public void closeInventory(EntityPlayer player) {

  }

  @Override
  public boolean isItemValidForSlot(int index, ItemStack stack) {
    return false;
  }

  @Override
  public int getField(int id) {
    return 0;
  }

  @Override
  public void setField(int id, int value) {

  }

  @Override
  public int getFieldCount() {
    return 0;
  }

  @Override
  public void clear() {

  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public boolean hasCustomName() {
    return false;
  }

  @Override
  public ITextComponent getDisplayName() {
    return null;
  }

  @Override
  public boolean canExtractFromSlot(int slot) {
    return false;
  }

  @Override
  public boolean canInsertToSlot(int slot) {
    return false;
  }

}
