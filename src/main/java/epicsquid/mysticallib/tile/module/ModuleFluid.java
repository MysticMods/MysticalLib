package epicsquid.mysticallib.tile.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.fluid.ExtendedFluidTank;
import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ModuleFluid extends Module {
  public List<ExtendedFluidTank> tanks = new ArrayList<>();
  public Map<EnumFacing, FluidIOProxy> ioProxies = new HashMap<EnumFacing, FluidIOProxy>();
  public TileModular tile = null;
  public int receiveLimit, giveLimit = 0;
  public FluidManager manager = new FluidManager();

  public ModuleFluid(String name, TileModular tile, int giveLimit) {
    super(name);
    this.tile = tile;
    this.giveLimit = giveLimit;
    for (EnumFacing f : EnumFacing.values()) {
      ioProxies.put(f, constructIOProxy(f));
    }
  }

  public ModuleFluid addTank(ExtendedFluidTank tank) {
    tanks.add(tank);
    return this;
  }

  public FluidTank constructTank(int capacity) {
    return new FluidTank(capacity);
  }

  public int getInputLimit() {
    return receiveLimit;
  }

  public int getOutputLimit() {
    return giveLimit;
  }

  public class FluidManager implements IFluidHandler {
    @Override
    public IFluidTankProperties[] getTankProperties() {
      List<IFluidTankProperties> props = new ArrayList<>();
      for (ExtendedFluidTank t : tanks) {
        IFluidTankProperties[] tprops = t.getTankProperties();
        for (IFluidTankProperties p : tprops) {
          props.add(p);
        }
      }
      return props.toArray(new IFluidTankProperties[props.size()]);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
      for (ExtendedFluidTank t : tanks) {
        if (t.appliesTo(resource)) {
          return t.fill(resource, doFill);
        }
      }
      return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
      for (ExtendedFluidTank t : tanks) {
        if (t.appliesTo(resource)) {
          return t.drain(resource, doDrain);
        }
      }
      return new FluidStack(resource.getFluid(), 0);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
      for (ExtendedFluidTank t : tanks) {
        return t.drain(maxDrain, doDrain);
      }
      return null;
    }

  }

  public class FluidIOProxy extends FluidManager {
    EnumFacing face = EnumFacing.NORTH;
    TileModular tile = null;

    public FluidIOProxy(EnumFacing face, TileModular tile) {
      super();
      this.face = face;
      this.tile = tile;
    }

    public FluidIOProxy setTile(TileModular tile) {
      this.tile = tile;
      return this;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
      return manager.getTankProperties();
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
      if (ModuleFluid.this.tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.OUT || tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.INOUT
          || ModuleFluid.this.tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.NEUTRAL) {
        ModuleFluid.this.tile.markDirty();
        return manager.drain(stack, simulate);
      }
      return null;
    }

    @Override
    public int fill(FluidStack stack, boolean simulate) {
      if (ModuleFluid.this.tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.IN || tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.INOUT) {
        ModuleFluid.this.tile.markDirty();
        return manager.fill(stack, simulate);
      }
      return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
      if (ModuleFluid.this.tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.OUT || tile.faceConfig.ioConfig.get(face) == FaceConfig.FaceIO.INOUT) {
        return manager.drain(maxDrain, doDrain);
      }
      return null;
    }
  }

  public FluidIOProxy constructIOProxy(EnumFacing face) {
    return new FluidIOProxy(face, tile);
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing face, TileModular tile) {
    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
  }

  @Override
  public Object getCapability(Capability<?> capability, EnumFacing face, TileModular tile) {
    if (face != null) {
      return ioProxies.get(face).setTile(tile);
    } else {
      return manager;
    }
  }

  @Override
  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    NBTTagList tanks = new NBTTagList();
    for (ExtendedFluidTank t : this.tanks) {
      tanks.appendTag(t.writeToNBT(new NBTTagCompound()));
    }
    tag.setTag("tanks", tanks);
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    NBTTagList list = tag.getTagList("tanks", Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < list.tagCount(); i++) {
      tanks.get(i).readFromNBT(list.getCompoundTagAt(i));
    }
  }

  @Override
  public void onUpdate(TileModular tile) {
    for (EnumFacing f : EnumFacing.values()) {
      if (hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f, tile)) {
        if (tile.faceConfig.ioConfig.get(f) == FaceConfig.FaceIO.OUT && !tile.getWorld().isRemote && tile.validIOModules.contains(this.getModuleName())) {
          for (ExtendedFluidTank tank : tanks) {
            if (tank.canOutput() && tank.getFluid() != null && tank.getFluid().getFluid() != null) {
              TileEntity t = tile.getWorld().getTileEntity(tile.getPos().offset(f));
              if (t != null && t.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite())) {
                IFluidHandler s = t.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite());
                if (s != null) {
                  int amount = s.fill(new FluidStack(tank.getFluid().getFluid(), Math.min(this.giveLimit, tank.getFluidAmount())), false);
                  if (amount > 0) {
                    FluidStack fl = tank.getFluid().copy();
                    s.fill(new FluidStack(tank.getFluid(), amount), true);
                    tank.drainInternal(new FluidStack(fl.getFluid(), amount), true);
                    tile.markDirty();
                    t.markDirty();
                    LibEvents.markForUpdate(tile.getPos().offset(f), t);
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  public Capability getCapabilityType() {
    return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
  }
}
