package epicsquid.mysticallib.tile;

import java.util.HashSet;
import java.util.Set;

import akka.japi.Pair;
import epicsquid.mysticallib.tile.module.FaceConfig;
import epicsquid.mysticallib.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class CableNetwork {
  public int ID = 0;
  public Set<BlockPos> cables = new HashSet<BlockPos>();
  private Set<BlockPos> removals = new HashSet<BlockPos>();
  public boolean valid = true;
  public String type = null;
  public boolean dirty = true;
  public CableWorldData data = null;

  public Set<Pair<TileEntity, EnumFacing>> connections = new HashSet<Pair<TileEntity, EnumFacing>>();

  public CableNetwork(CableWorldData data) {
    ID = CableManager.getNextID(data);
    this.data = data;
    data.networks.put(ID, this);
    data.markDirty();
  }

  public CableNetwork(CableWorldData data, int id) {
    ID = id;
    this.data = data;
    data.networks.put(ID, this);
    data.markDirty();
  }

  public void highlight(World world) {
    for (BlockPos p : cables) {
      IBlockState s = Blocks.STAINED_GLASS.getStateFromMeta(Util.rand.nextInt(16));
      world.setBlockState(p.up(), s);
      world.notifyBlockUpdate(p.up(), Blocks.AIR.getDefaultState(), s, 8);
    }
    for (Pair<TileEntity, EnumFacing> t : connections) {
      IBlockState s = Blocks.STAINED_GLASS.getStateFromMeta(Util.rand.nextInt(16));
      world.setBlockState(t.first().getPos().up(), s);
      world.notifyBlockUpdate(t.first().getPos().up(), Blocks.AIR.getDefaultState(), s, 8);
    }
  }

  public void invalidate() {
    valid = false;
  }

  public void tryAddPos(BlockPos pos) {
    if (!cables.contains(pos)) {
      cables.add(pos);
      dirty = true;
      data.markDirty();
    }
  }

  public void tickRemovals() {
    for (BlockPos p : removals) {
      cables.remove(p);
    }
    removals.clear();
  }

  public boolean needsDistributionTick() {
    return false;
  }

  public void updateConnections(World world) {
    dirty = false;
    connections.clear();
    for (BlockPos p : cables) {
      /**
       * THIS IS WHERE EXU2 AND RT BREAK!
       *
       * Printing the BlockPos works, and will successfully print the accurate block positions of linked cables.
       * However, the tile entity is almost always null, and no classname will print.
       * Why? I do not know.
       */
      //System.out.println(p);
      TileEntity t = world.getTileEntity(p);
      //if (t != null) System.out.println(t.getClass().getSimpleName());
      if (t instanceof TileCable) {
        TileCable c = ((TileCable) t);
        processUpdateFor(c);
      }
    }
  }

  public void distribute() {

  }

  public void processUpdateFor(TileCable c) {
    for (EnumFacing f : EnumFacing.values()) {
      if (c.config.ioConfig.get(f) != FaceConfig.FaceIO.NEUTRAL) {
        TileEntity t2 = c.getWorld().getTileEntity(c.getPos().offset(f));
        if (!(t2 instanceof TileCable) && t2 != null && !t2.getWorld().isAirBlock(t2.getPos()) && !cables.contains(t2.getPos())) {
          connections.add(new Pair<TileEntity, EnumFacing>(t2, f.getOpposite()));
        }
      }
    }
  }

  public void readFromNBT(NBTTagCompound tag) {
    NBTTagList list = tag.getTagList("positions", Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < list.tagCount(); i++) {
      cables.add(NBTUtil.getPosFromTag(list.getCompoundTagAt(i)));
    }
    type = tag.getString("type");
    data.markDirty();
  }

  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    NBTTagList positions = new NBTTagList();
    for (BlockPos p : cables) {
      positions.appendTag(NBTUtil.createPosTag(p));
    }
    tag.setTag("positions", positions);
    tag.setString("type", type);
    return tag;
  }
}
