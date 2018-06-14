package epicsquid.mysticallib.tile.multiblock;

import epicsquid.mysticallib.gui.IHUDContainer;
import epicsquid.mysticallib.tile.ITile;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.tile.TileModular;
import epicsquid.mysticallib.tile.module.FaceConfig.FaceIO;
import epicsquid.mysticallib.tile.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileModularSlave extends TileModular implements ISlave, IHUDContainer, ITickable {
  public BlockPos master = null;
  public TileModular tile = null;

  public TileModularSlave() {
    this.config.setAllIO(FaceIO.IN);
  }

  @Override
  public BlockPos getMaster() {
    return master;
  }

  @Override
  public void setMaster(BlockPos pos) {
    master = pos;
    markDirty();
  }

  @Override
  public boolean hasCapability(Capability capability, EnumFacing face) {
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    for (Module m : tile.modules.values()) {
      if (m.hasCapability(capability, face, this)) {
        return true;
      }
    }
    return super.hasCapability(capability, face);
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing face) {
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    for (Module m : tile.modules.values()) {
      if (m.hasCapability(capability, face, this)) {
        return (T) m.getCapability(capability, face, this);
      }
    }
    return super.getCapability(capability, face);
  }

  @Override
  public void markDirty() {
    super.markDirty();
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    if (tile instanceof TileModular) {
      tile.markDirty();
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    tag.setTag("master", NBTUtil.createPosTag(master));
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    master = NBTUtil.getPosFromTag(tag.getCompoundTag("master"));
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY,
      float hitZ) {
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    if (tile == null) {
      return super.activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    } else {
      return ((ITile) tile).activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    super.breakBlock(world, pos, state, player);
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    if (tile != null && !tile.isInvalid()) {
      ((TileBase) tile).breakBlock(world, pos, state, player);
    }
    super.invalidate();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addHUD(float w, float h) {
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    if (tile instanceof IHUDContainer) {
      ((IHUDContainer) tile).addHUD(w, h);
    }
  }

  @Override
  public void update() {
    if (!(tile instanceof TileModular)) {
      tile = (TileModular) world.getTileEntity(master);
    }
    if (tile instanceof TileModular) {
      this.validIOModules = tile.validIOModules;
      for (Module m : tile.modules.values()) {
        m.onUpdate(this);
      }
    }
  }
}
