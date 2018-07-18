package epicsquid.mysticallib.tile.multiblock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.tile.ITile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

/**
 * Interface for master tiles of a multiblock
 */
public interface IMaster extends ITile {

  /**
   * Gets the location of slave tiles for the multiblock
   * @return The location of the slave tiles
   */
  @Nonnull
  Collection<BlockPos> getSlaves();

  /**
   * Adds a given pos a valid slave location
   * @param p Position of the slave tile
   */
  void addSlave(@Nonnull BlockPos p);

  /**
   * Gets the capability from the tile with a pos for the slave tile being accessed
   */
  @Nullable
  <T> T getCapability(@Nonnull Capability<T> c, @Nullable EnumFacing face, @Nullable BlockPos pos);

  /**
   * Checks the capability from the tile with a pos for the slave tile being accessed
   */
  boolean hasCapability(@Nonnull Capability c, @Nullable EnumFacing face, @Nullable BlockPos pos);

  default void breakSlaves(@Nonnull World world) {
    Collection<BlockPos> c = getSlaves();
    for (BlockPos p : c) {
      IBlockState s = world.getBlockState(p);
      world.setBlockToAir(p);
      world.notifyBlockUpdate(p, s, Blocks.AIR.getDefaultState(), 8);
    }
  }

  @Nonnull
  static NBTTagList writePosList(@Nonnull Collection<BlockPos> l) {
    NBTTagList n = new NBTTagList();
    for (BlockPos p : l) {
      n.appendTag(NBTUtil.createPosTag(p));
    }
    return n;
  }

  @Nonnull
  static List<BlockPos> readPosList(@Nonnull NBTTagCompound c, String s) {
    ArrayList<BlockPos> p = new ArrayList<>();
    NBTTagList l = c.getTagList(s, Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < l.tagCount(); i++) {
      p.add(NBTUtil.getPosFromTag(l.getCompoundTagAt(i)));
    }
    return p;
  }
}
