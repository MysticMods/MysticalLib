package epicsquid.mysticallib.tile.multiblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.gui.IHUDContainer;
import epicsquid.mysticallib.tile.ITile;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.tile.module.FaceConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileSlave extends TileBase implements ISlave, IHUDContainer {

  private BlockPos master;
  private TileEntity tile;
  private FaceConfig faceIO;

  public TileSlave(@Nullable BlockPos master, @Nullable TileEntity tile) {
    this.master = master;
    this.tile = tile;
    this.faceIO = new FaceConfig(FaceConfig.FaceIO.IN);
  }

  public TileSlave() {
    this(null, null);
  }

  @Override
  @Nullable
  public BlockPos getMasterPos() {
    return master;
  }

  @Override
  public void setMasterPos(@Nullable BlockPos pos) {
    master = pos;
    markDirty();
  }

  @Override
  public boolean hasCapability(@Nonnull Capability capability, @Nullable EnumFacing face) {
    if (tile == null) {
      tile = world.getTileEntity(master);
    }
    if (tile != null) {
      return ((IMaster) tile).hasCapability(capability, face, getPos());
    }
    return super.hasCapability(capability, face);
  }

  @Override
  @Nullable
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing face) {
    if (tile == null) {
      tile = world.getTileEntity(master);
    }
    if (tile != null) {
      return ((IMaster) tile).getCapability(capability, face, getPos());
    }
    return super.getCapability(capability, face);
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound tag) {
    super.writeToNBT(tag);
    tag.setTag("master", NBTUtil.createPosTag(master));
    return tag;
  }

  @Override
  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    super.readFromNBT(tag);
    master = NBTUtil.getPosFromTag(tag.getCompoundTag("master"));
  }

  @Override
  public boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand,
      @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
    if (tile == null) {
      tile = world.getTileEntity(master);
    }
    if (tile == null) {
      return super.activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    } else {
      return ((ITile) tile).activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
  }

  @Override
  public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player) {
    if (tile == null) {
      tile = world.getTileEntity(master);
    }
    if (tile != null && !tile.isInvalid()) {
      ((TileBase) tile).breakBlock(world, pos, state, player);
    }
    super.invalidate();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addHUD(float w, float h) {
    if (tile == null) {
      tile = world.getTileEntity(master);
    }
    if (tile instanceof IHUDContainer) {
      ((IHUDContainer) tile).addHUD(w, h);
    }
  }
}
