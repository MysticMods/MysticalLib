package epicsquid.mysticallib.tile.module;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.tile.TileModular;
import epicsquid.mysticallib.tile.module.FaceConfig.FaceIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class ModuleEnergy implements IModule<IEnergyStorage> {

  public static final @Nonnull String ENERGY_MODULE = "ENERGY_MODULE";

  private @Nonnull EnergyStorage battery;
  private @Nonnull Map<EnumFacing, EnergyIOProxy> ioProxies = new EnumMap<>(EnumFacing.class);
  private int inputLimit, outputLimit;
  private @Nonnull FaceConfig faceConfig;
  private @Nonnull TileModular tile;

  public ModuleEnergy(@Nonnull String name, @Nonnull TileModular tile, int capacity, int inputLimit, int outputLimit) {
    this.inputLimit = inputLimit;
    this.outputLimit = outputLimit;
    this.tile = tile;
    this.faceConfig = tile.getFaceConfig();
    battery = constructBattery(capacity, inputLimit, outputLimit, 0);
    for (EnumFacing f : EnumFacing.values()) {
      ioProxies.put(f, constructIOProxy(f, faceConfig.getIO(f), capacity, inputLimit, outputLimit, 0));
    }
  }

  public int getInputLimit() {
    return inputLimit;
  }

  public int getOutputLimit() {
    return outputLimit;
  }

  @Nonnull
  protected EnergyStorage constructBattery(int capacity, int maxIn, int maxOut, int energy) {
    return new EnergyStorage(capacity, maxIn, maxOut, energy);
  }

  @Nonnull
  protected EnergyIOProxy constructIOProxy(@Nonnull EnumFacing face, @Nonnull FaceIO ioMode, int capacity, int maxIn, int maxOut, int energy) {
    return new EnergyIOProxy(face, ioMode, capacity, maxIn, maxOut, energy);
  }

  @Nonnull
  public EnergyStorage getBattery() {
    return battery;
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<IEnergyStorage> capability, @Nullable EnumFacing face) {
    return capability == CapabilityEnergy.ENERGY;
  }

  @Override
  @Nonnull
  public IEnergyStorage getCapability(@Nullable Capability<IEnergyStorage> capability, @Nullable EnumFacing face) {
    if (face != null) {
      return ioProxies.get(face);
    } else {
      return battery;
    }
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setInteger("energy", battery.getEnergyStored());
    tag.setInteger("capacity", battery.getMaxEnergyStored());
    tag.setInteger("inputLimit", inputLimit);
    tag.setInteger("outputLimit", outputLimit);
    return tag;
  }

  @Override
  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    outputLimit = tag.getInteger("outputLimit");
    inputLimit = tag.getInteger("inputLimit");
    battery = constructBattery(tag.getInteger("capacity"), inputLimit, outputLimit, tag.getInteger("energy"));
  }

  @Override
  public void onUpdate(@Nonnull BlockPos pos, @Nonnull World world) {

    // Check all connecting tiles to
    for (EnumFacing dir : EnumFacing.values()) {
      if (faceConfig.getIO(dir) == FaceIO.OUT && !world.isRemote) {
        // Get the tile next to tbe block to check for its energy level
        TileEntity adjTile = world.getTileEntity(pos.offset(dir));
        if (adjTile != null && adjTile.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite())) {
          // Get the energy capability of adjacent block
          IEnergyStorage adjBattery = adjTile.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite());
          if (adjBattery != null) {
            // Output energy into the battery
            int amount = adjBattery.receiveEnergy(Math.min(outputLimit, battery.getEnergyStored()), true);
            if (amount > 0) {
              adjBattery.receiveEnergy(amount, false);
              battery.extractEnergy(amount, false);
              tile.markDirty();
              adjTile.markDirty();
              LibEvents.markForUpdate(pos.offset(dir), adjTile);
            }
          }
        }
      }
    }
  }

  @Nonnull
  @Override
  public String getModuleName() {
    return ModuleEnergy.ENERGY_MODULE;
  }

  @Override
  public void onBroken(@Nonnull World world, @Nonnull BlockPos pos, @Nullable EntityPlayer player) {
    // Nothing required
  }

  @Override
  @Nonnull
  public Capability<IEnergyStorage> getCapabilityType() {
    return CapabilityEnergy.ENERGY;
  }

  public class EnergyIOProxy extends EnergyStorage {
    private @Nonnull EnumFacing face;
    private @Nonnull FaceIO ioMode;

    public EnergyIOProxy(@Nonnull EnumFacing face, @Nonnull FaceIO ioMode, int capacity, int maxReceive, int maxExtract, int energy) {
      super(capacity, maxReceive, maxExtract, energy);
      this.face = face;
      this.ioMode = ioMode;
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
      if (ioMode == FaceIO.IN || ioMode == FaceIO.INOUT) {
        tile.markDirty();
        return battery.receiveEnergy(amount, simulate);
      }
      return 0;
    }

    @Override
    public int extractEnergy(int amount, boolean simulate) {
      if ((ioMode == FaceIO.OUT || ioMode == FaceIO.NEUTRAL || ioMode == FaceIO.INOUT)) {
        tile.markDirty();
        return battery.extractEnergy(amount, simulate);
      }
      return 0;
    }

    @Override
    public boolean canReceive() {
      return super.canReceive() && (ioMode == FaceIO.IN || ioMode == FaceIO.INOUT);
    }

    @Override
    public boolean canExtract() {
      return super.canExtract() && (ioMode == FaceIO.OUT || ioMode == FaceIO.INOUT);
    }

  }
}
