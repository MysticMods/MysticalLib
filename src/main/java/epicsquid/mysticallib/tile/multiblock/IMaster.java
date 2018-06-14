package epicsquid.mysticallib.tile.multiblock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

public interface IMaster extends ITile {
  public Collection<BlockPos> getSlaves();

  public void addSlave(BlockPos p);

  public <T> T getCapability(Capability<T> c, EnumFacing face, BlockPos pos);

  public boolean hasCapability(Capability c, EnumFacing face, BlockPos pos);

  public default void breakSlaves(World world) {
    Collection<BlockPos> c = getSlaves();
    for (BlockPos p : c) {
      IBlockState s = world.getBlockState(p);
      world.setBlockToAir(p);
      world.notifyBlockUpdate(p, s, Blocks.AIR.getDefaultState(), 8);
    }
  }

  public static NBTTagList writePosList(Collection<BlockPos> l) {
    NBTTagList n = new NBTTagList();
    for (BlockPos p : l) {
      n.appendTag(NBTUtil.createPosTag(p));
    }
    return n;
  }

  public static List<BlockPos> readPosList(NBTTagCompound c, String s) {
    ArrayList<BlockPos> p = new ArrayList<>();
    NBTTagList l = c.getTagList(s, Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < l.tagCount(); i++) {
      p.add(NBTUtil.getPosFromTag(l.getCompoundTagAt(i)));
    }
    return p;
  }
}
