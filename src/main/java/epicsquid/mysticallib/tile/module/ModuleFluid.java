package epicsquid.mysticallib.tile.module;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.handlers.MysticalFluidHandler;
import epicsquid.mysticallib.handlers.SmartTank;
import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ModuleFluid implements IModule<IFluidHandler> {

  public static final @Nonnull String MODULE_FLUID = "MODULE_FLUID";

  private Map<EnumFacing, FluidIOProxy> ioProxies = new HashMap<EnumFacing, FluidIOProxy>();
  private TileModular tile;
  private @Nonnull MysticalFluidHandler manager;
  private @Nonnull FaceConfig faceConfig;

  public ModuleFluid(@Nonnull TileModular tile, int maxTransfer) {
    this(tile, maxTransfer, null);
  }

  public ModuleFluid(@Nonnull TileModular tile, int maxTransfer, @Nullable SmartTank... tanks) {
    this.tile = tile;
    manager = new MysticalFluidHandler(maxTransfer, tanks);
    this.faceConfig = tile.faceConfig;
    for (EnumFacing f : EnumFacing.values()) {
      ioProxies.put(f, constructIOProxy(f));
    }
  }

  public ModuleFluid addTank(@Nonnull SmartTank tank) {
    manager.addTank(tank);
    return this;
  }

  private FluidIOProxy constructIOProxy(@Nonnull EnumFacing face) {
    return new FluidIOProxy(face, faceConfig.getIO(face));
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<IFluidHandler> capability, @Nullable EnumFacing face) {
    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
  }

  @Override
  @Nonnull
  public IFluidHandler getCapability(@Nonnull Capability<IFluidHandler> capability, @Nullable EnumFacing face) {
    if (face != null) {
      return ioProxies.get(face);
    } else {
      return manager;
    }
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT() {
    NBTTagCompound fluidHandler = new NBTTagCompound();
    manager.writeToNBT(fluidHandler);
    return fluidHandler;
  }

  @Override
  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    manager.readFromNBT(tag);
  }

  @Override
  public void onBroken(@Nonnull World world, @Nonnull BlockPos pos, @Nullable EntityPlayer player) {
    // Try and place fluid in world
    for (SmartTank tank : manager.getTanks()) {
      if (tank.getCapacity() >= Fluid.BUCKET_VOLUME && tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
        FluidUtil.tryPlaceFluid(player, world, pos, manager, new FluidStack(tank.getFluid().getFluid(), Fluid.BUCKET_VOLUME));
      }
    }
  }

  @Override
  public void onUpdate(@Nonnull BlockPos pos, @Nonnull World world) {
    for (EnumFacing dir : EnumFacing.values()) {
      if (faceConfig.getIO(dir) == FaceConfig.FaceIO.OUT && !world.isRemote) {
        for (SmartTank tank : manager.getTanks()) {
          if (tank.canDrain() && tank.getFluid() != null && tank.getFluid().getFluid() != null) {
            TileEntity adjTile = world.getTileEntity(pos.offset(dir));
            if (adjTile != null && adjTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, dir.getOpposite())) {
              IFluidHandler adjTank = adjTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, dir.getOpposite());
              if (adjTank != null) {
                int amount = adjTank.fill(new FluidStack(tank.getFluid().getFluid(), Math.min(manager.getMaxExtract(), tank.getFluidAmount())), false);
                if (amount > 0) {
                  FluidStack fl = tank.getFluid().copy();
                  adjTank.fill(new FluidStack(tank.getFluid(), amount), true);
                  tank.drainInternal(new FluidStack(fl.getFluid(), amount), true);
                  tile.markDirty();
                  adjTile.markDirty();
                  LibEvents.markForUpdate(tile.getPos().offset(dir), adjTile);
                  break;
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  @Nonnull
  public Capability<IFluidHandler> getCapabilityType() {
    return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
  }

  @Nonnull
  @Override
  public String getModuleName() {
    return ModuleFluid.MODULE_FLUID;
  }

  public class FluidIOProxy extends MysticalFluidHandler {
    private EnumFacing face;
    private FaceConfig.FaceIO ioMode;

    public FluidIOProxy(@Nonnull EnumFacing face, @Nonnull FaceConfig.FaceIO ioMode) {
      super(manager.getMaxInsert());
      this.face = face;
      this.ioMode = ioMode;
    }

    @Override
    @Nonnull
    public IFluidTankProperties[] getTankProperties() {
      return manager.getTankProperties();
    }

    @Override
    @Nullable
    public FluidStack drain(@Nullable FluidStack stack, boolean simulate) {
      if (ioMode == FaceConfig.FaceIO.OUT || ioMode == FaceConfig.FaceIO.INOUT || ioMode == FaceConfig.FaceIO.NEUTRAL) {
        tile.markDirty();
        return manager.drain(stack, simulate);
      }
      return null;
    }

    @Override
    public int fill(@Nullable FluidStack stack, boolean simulate) {
      if (ioMode == FaceConfig.FaceIO.IN || ioMode == FaceConfig.FaceIO.INOUT) {
        tile.markDirty();
        return manager.fill(stack, simulate);
      }
      return 0;
    }

    @Override
    @Nullable
    public FluidStack drain(int maxDrain, boolean doDrain) {
      if (ioMode == FaceConfig.FaceIO.OUT || ioMode == FaceConfig.FaceIO.INOUT) {
        return manager.drain(maxDrain, doDrain);
      }
      return null;
    }
  }
}
