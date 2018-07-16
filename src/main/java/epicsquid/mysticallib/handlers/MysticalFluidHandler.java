package epicsquid.mysticallib.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * Used to handle any number of SmartTanks
 */
public class MysticalFluidHandler implements IFluidHandler {

  private @Nonnull List<SmartTank> tanks;
  private int maxInsert;
  private int maxExtract;

  public MysticalFluidHandler(int maxTransfer) {
    this(maxTransfer, null);
  }

  public MysticalFluidHandler(int maxTransfer, @Nullable SmartTank... tanks) {
    this(maxTransfer, maxTransfer, tanks);
  }

  public MysticalFluidHandler(int maxInsert, int maxExtract, @Nullable SmartTank... tanks) {
    this.tanks = new ArrayList<>();
    if (tanks != null) {
      Collections.addAll(this.tanks, tanks);
    }
    this.maxInsert = maxInsert;
    this.maxExtract = maxExtract;
  }

  /**
   * Adds a tank to the handler's list of tanks
   */
  public MysticalFluidHandler addTank(@Nonnull SmartTank tank) {
    tanks.add(tank);
    return this;
  }

  @Override
  @Nonnull
  public IFluidTankProperties[] getTankProperties() {
    List<IFluidTankProperties> props = new ArrayList<>();
    for (SmartTank t : tanks) {
      IFluidTankProperties[] tprops = t.getTankProperties();
      for (IFluidTankProperties p : tprops) {
        props.add(p);
      }
    }
    return props.toArray(new IFluidTankProperties[props.size()]);
  }

  @Override
  public int fill(@Nullable FluidStack resource, boolean doFill) {
    for (SmartTank t : tanks) {
      if (t.canFill(resource)) {
        return t.fill(resource, doFill);
      }
    }
    return 0;
  }

  @Override
  public FluidStack drain(@Nullable FluidStack resource, boolean doDrain) {
    for (SmartTank t : tanks) {
      if (t.canDrain(resource)) {
        return t.drain(resource, doDrain);
      }
    }
    return new FluidStack(resource.getFluid(), 0);
  }

  @Override
  public FluidStack drain(int maxDrain, boolean doDrain) {
    for (SmartTank t : tanks) {
      return t.drain(maxDrain, doDrain);
    }
    return null;
  }

  public int getMaxExtract() {
    return maxExtract;
  }

  public int getMaxInsert() {
    return maxInsert;
  }

  @Nonnull
  public List<SmartTank> getTanks() {
    return tanks;
  }

  public void writeToNBT(@Nonnull NBTTagCompound tag) {
    NBTTagCompound tankList = new NBTTagCompound();
    for (int i = 0; i < tanks.size(); i++) {
      tanks.get(i).writeCommon("tank" + i, tag);
    }
    tag.setTag("tanks", tankList);
  }

  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    if (tag.hasKey("tanks")) {
      NBTTagCompound tankList = tag.getCompoundTag("tanks");
      for (int i = 0; i < tanks.size(); i++) {
        tanks.get(i).readCommon("tank" + i, tankList);
      }
    }
  }

}
