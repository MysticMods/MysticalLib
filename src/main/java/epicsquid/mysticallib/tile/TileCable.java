package epicsquid.mysticallib.tile;

import epicsquid.mysticallib.tile.module.FaceConfig;
import epicsquid.mysticallib.tile.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileCable extends TileModular {
  public float u = 0, v = 0;
  public boolean passOn = true;

  public BlockPos from = null;
  public String type = null;
  public CableNetwork network = null;

  int[] toggles = new int[] { 1, 1, 1, 1, 1, 1 };

  public TileCable(String type) {
    hasGui = false;
    canModifyIO = false;
    from = getPos();
    this.type = type;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    tag.setTag("from", NBTUtil.createPosTag(from));
    if (network != null) {
      tag.setInteger("network", network.ID);
    }
    tag.setIntArray("toggles", toggles);
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    from = NBTUtil.getPosFromTag(tag.getCompoundTag("from"));
    if (tag.hasKey("network") && world != null) {
      network = CableWorldData.get(world).networks.get(tag.getInteger("network"));
    }
    toggles = tag.getIntArray("toggles");
  }

  public boolean canConnect(EnumFacing f) {
    for (Module m : modules.values()) {
      TileEntity t = world.getTileEntity(pos.offset(f));
      if (t != null) {
        if (t.hasCapability(m.getCapabilityType(), f.getOpposite())) {
          if (toggles != null && toggles.length == 6) {
            return toggles[f.ordinal()] == 1;
          } else {
            toggles = new int[] { 1, 1, 1, 1, 1, 1 };
          }
        }
      }
    }
    return false;
  }

  public void updateConnections(IBlockState state) {
    config.setIO(EnumFacing.NORTH, canConnect(EnumFacing.NORTH) ? FaceConfig.FaceIO.IN : FaceConfig.FaceIO.NEUTRAL);
    config.setIO(EnumFacing.SOUTH, canConnect(EnumFacing.SOUTH) ? FaceConfig.FaceIO.IN : FaceConfig.FaceIO.NEUTRAL);
    config.setIO(EnumFacing.EAST, canConnect(EnumFacing.EAST) ? FaceConfig.FaceIO.IN : FaceConfig.FaceIO.NEUTRAL);
    config.setIO(EnumFacing.WEST, canConnect(EnumFacing.WEST) ? FaceConfig.FaceIO.IN : FaceConfig.FaceIO.NEUTRAL);
    config.setIO(EnumFacing.UP, canConnect(EnumFacing.UP) ? FaceConfig.FaceIO.IN : FaceConfig.FaceIO.NEUTRAL);
    config.setIO(EnumFacing.DOWN, canConnect(EnumFacing.DOWN) ? FaceConfig.FaceIO.IN : FaceConfig.FaceIO.NEUTRAL);

    if (!world.isRemote) {
      updateNetwork();
    }

    markDirty();
  }

  public CableNetwork constructNetwork(CableWorldData data) {
    return new CableNetwork(data);
  }

  public void updateNetwork() {
    if (network == null) {
      network = constructNetwork(CableWorldData.get(world));
      network.cables.add(getPos());
      markDirty();
    }
    for (EnumFacing f : EnumFacing.values()) {
      TileEntity t = world.getTileEntity(getPos().offset(f));
      if (t instanceof TileCable && !t.isInvalid() && ((TileCable) t).type.equalsIgnoreCase(type) && toggles[f.ordinal()] == 1
          && ((TileCable) t).toggles[f.getOpposite().ordinal()] == 1) {
        TileCable c = ((TileCable) t);
        if (c.network == null) {
          c.network = network;
          network.tryAddPos(c.getPos());
          c.markDirty();
          c.updateNetwork();
        } else {
          if (c.network.ID != network.ID) {
            c.network.invalidate();
            c.network = network;
            network.tryAddPos(c.getPos());
            c.network.dirty = true;
            c.markDirty();
            c.updateNetwork();
          }
        }
      }
    }
    network.dirty = true;
  }

  public void togglePipe(EnumFacing f) {
    boolean canConnect = canConnect(f);
    if (toggles == null || toggles.length != 6) {
      toggles = new int[] { 1, 1, 1, 1, 1, 1 };
    }
    toggles[f.ordinal()] = toggles[f.ordinal()] == 1 ? 0 : 1;
    boolean newConnect = canConnect(f);
    if (canConnect != newConnect && !world.isRemote) {
      CableNetwork n = network;
      network = null;
      updateConnections(world.getBlockState(pos));
      for (EnumFacing face : EnumFacing.VALUES) {
        TileEntity t = world.getTileEntity(pos.offset(face));
        if (t instanceof TileCable) {
          ((TileCable) t).network = null;
          ((TileCable) t).updateConnections(world.getBlockState(pos.offset(face)));
          ((TileCable) t).network.dirty = true;
        }
      }
      if (n != null) {
        n.dirty = true;
      }
    }
  }

  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
    return newState.getBlock() != oldState.getBlock();
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY,
      float hitZ) {
    return false;
  }

  @Override
  public void invalidate() {
    super.invalidate();
    if (!world.isRemote) {
      if (network != null) {
        network.invalidate();
      }
      for (EnumFacing f : EnumFacing.values()) {
        TileEntity t = world.getTileEntity(getPos().offset(f));
        if (t instanceof TileCable && ((TileCable) t).type == type) {
          TileCable c = ((TileCable) t);
          c.network = null;
          c.updateNetwork();
        }
      }
    }
  }
}
