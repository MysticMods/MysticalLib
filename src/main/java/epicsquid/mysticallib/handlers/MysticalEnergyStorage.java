package epicsquid.mysticallib.handlers;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class MysticalEnergyStorage extends EnergyStorage {

  public MysticalEnergyStorage(int capacity) {
    this(capacity, 0);
  }

  public MysticalEnergyStorage(int capacity, int maxTransfer) {
    this(capacity, maxTransfer, maxTransfer);
  }

  public MysticalEnergyStorage(int capacity, int maxReceive, int maxExtract) {
    this(capacity, maxReceive, maxExtract, 0);
  }

  public MysticalEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
    super(capacity, maxReceive, maxExtract, energy);
  }

  public int getMaxReceive() {
    return maxReceive;
  }

  public int getMaxExtract() {
    return maxExtract;
  }

  @Nonnull
  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setInteger("energy", getEnergyStored());
    tag.setInteger("capacity", getMaxEnergyStored());
    tag.setInteger("maxReceive", getMaxReceive());
    tag.setInteger("maxExtract", getMaxExtract());
    return tag;
  }

  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    energy = tag.getInteger("energy");
    capacity = tag.getInteger("capacity");
    maxReceive = tag.getInteger("maxReceive");
    maxExtract = tag.getInteger("maxExtract");
  }
}
