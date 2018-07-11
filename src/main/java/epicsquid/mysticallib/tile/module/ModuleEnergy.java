package epicsquid.mysticallib.tile.module;

import java.util.HashMap;
import java.util.Map;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.tile.TileModular;
import epicsquid.mysticallib.tile.module.FaceConfig.FaceIO;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class ModuleEnergy extends Module {
  public EnergyStorage battery;
  public Map<EnumFacing, EnergyIOProxy> ioProxies = new HashMap<EnumFacing, EnergyIOProxy>();
  public TileModular tile = null;
  public int receiveLimit = 0, giveLimit = 0;

  public ModuleEnergy(String name, TileModular tile, int capacity, int receiveLimit, int giveLimit) {
    super(name);
    this.receiveLimit = receiveLimit;
    this.giveLimit = giveLimit;
    this.tile = tile;
    battery = constructBattery(capacity, receiveLimit, giveLimit, 0);
    for (EnumFacing f : EnumFacing.values()) {
      ioProxies.put(f, constructIOProxy(f, capacity, receiveLimit, giveLimit, 0));
    }
  }

  public int getInputLimit() {
    return receiveLimit;
  }

  public int getOutputLimit() {
    return giveLimit;
  }

  public EnergyStorage constructBattery(int capacity, int maxIn, int maxOut, int energy) {
    return new EnergyStorage(capacity, maxIn, maxOut, energy);
  }

  public class EnergyIOProxy extends EnergyStorage {
    EnumFacing face = EnumFacing.NORTH;
    TileModular tile = null;

    public EnergyIOProxy(EnumFacing face, TileModular tile, int capacity, int maxReceive, int maxExtract, int energy) {
      super(capacity, maxReceive, maxExtract, energy);
      this.face = face;
      this.tile = tile;
    }

    public EnergyIOProxy setTile(TileModular tile) {
      this.tile = tile;
      return this;
    }

    public int getMaxEnergyStored() {
      return battery.getMaxEnergyStored();
    }

    @Override
    public int getEnergyStored() {
      return battery.getEnergyStored();
    }

    @Override
    public int receiveEnergy(int amount, boolean simulate) {
      if (tile.config.ioConfig.get(face) == FaceIO.IN || tile.config.ioConfig.get(face) == FaceIO.INOUT) {
        tile.markDirty();
        return battery.receiveEnergy(amount, simulate);
      }
      return 0;
    }

    @Override
    public int extractEnergy(int amount, boolean simulate) {
      if ((tile.config.ioConfig.get(face) == FaceIO.OUT || tile.config.ioConfig.get(face) == FaceIO.NEUTRAL
          || tile.config.ioConfig.get(face) == FaceIO.INOUT)) {
        tile.markDirty();
        return battery.extractEnergy(amount, simulate);
      }
      return 0;
    }

    @Override
    public boolean canReceive() {
      return super.canReceive() && (tile.config.ioConfig.get(face) == FaceIO.IN || tile.config.ioConfig.get(face) == FaceIO.INOUT);
    }

    @Override
    public boolean canExtract() {
      return super.canExtract() && (tile.config.ioConfig.get(face) == FaceIO.OUT || tile.config.ioConfig.get(face) == FaceIO.INOUT);
    }

  }

  public EnergyIOProxy constructIOProxy(EnumFacing face, int capacity, int maxIn, int maxOut, int energy) {
    return new EnergyIOProxy(face, tile, battery.getMaxEnergyStored(), receiveLimit, giveLimit, battery.getEnergyStored());
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing face, TileModular tile) {
    return capability == CapabilityEnergy.ENERGY;
  }

  @Override
  public Object getCapability(Capability<?> capability, EnumFacing face, TileModular tile) {
    if (face != null) {
      return ioProxies.get(face).setTile(tile);
    } else {
      return battery;
    }
  }

  @Override
  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setInteger("energy", battery.getEnergyStored());
    tag.setInteger("capacity", battery.getMaxEnergyStored());
    tag.setInteger("receiveLimit", receiveLimit);
    tag.setInteger("giveLimit", giveLimit);
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    battery = constructBattery(tag.getInteger("capacity"), tag.getInteger("receiveLimit"), tag.getInteger("giveLimit"), tag.getInteger("energy"));
    giveLimit = tag.getInteger("giveLimit");
    receiveLimit = tag.getInteger("receiveLimit");
  }

  @Override
  public void onUpdate(TileModular tile) {
    for (EnumFacing f : EnumFacing.values()) {
      if (hasCapability(CapabilityEnergy.ENERGY, f, tile)) {
        if (tile.config.ioConfig.get(f) == FaceIO.OUT && !tile.getWorld().isRemote && tile.validIOModules.contains(this.getModuleName())) {
          TileEntity t = tile.getWorld().getTileEntity(tile.getPos().offset(f));
          if (t != null && t.hasCapability(CapabilityEnergy.ENERGY, f.getOpposite())) {
            IEnergyStorage s = t.getCapability(CapabilityEnergy.ENERGY, f.getOpposite());
            if (s != null) {
              int amount = s.receiveEnergy(Math.min(giveLimit, battery.getEnergyStored()), true);
              if (amount > 0) {
                s.receiveEnergy(amount, false);
                battery.extractEnergy(amount, false);
                tile.markDirty();
                t.markDirty();
                LibEvents.markForUpdate(tile.getPos().offset(f), t);
              }
            }
          }
        }
      }
    }
  }

  @Override
  public Capability getCapabilityType() {
    return CapabilityEnergy.ENERGY;
  }
}
