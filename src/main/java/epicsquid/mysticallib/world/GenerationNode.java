package epicsquid.mysticallib.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenerationNode {
  String structure = "";
  Rotation rotation = Rotation.NONE;
  Mirror mirror = Mirror.NONE;
  public BlockPos pos = new BlockPos(-1, -1, -1);
  boolean replaceWithAir = false;
  public boolean isAlive = true;

  public GenerationNode(NBTTagCompound tag) {
    readFromNBT(tag);
  }

  public GenerationNode(BlockPos pos, String structure, Rotation rotation, Mirror mirror, boolean replaceWithAir) {
    this.pos = pos;
    this.structure = structure;
    this.rotation = rotation;
    this.mirror = mirror;
    this.replaceWithAir = replaceWithAir;
  }

  public void update(World world) {
    if (structure.length() > 0) {
      IGeneratable data = StructureRegistry.structures.get(structure);
      if (data != null) {
        data.calcDimensions();
        if (world.isAreaLoaded(new BlockPos(pos.getX() - 8, pos.getY(), pos.getZ() - 8),
            new BlockPos(pos.getX() + data.getWidth() + 8, pos.getY(), pos.getZ() + data.getLength() + 8))) {
          data.generateIn(world, pos.getX(), pos.getY(), pos.getZ(), rotation, mirror, replaceWithAir);
          isAlive = false;
        }
      } else {
        isAlive = false;
      }
    }
  }

  protected void readFromNBT(NBTTagCompound compound) {
    structure = compound.getString("structure");
    rotation = Rotation.values()[compound.getInteger("rotation")];
    mirror = Mirror.values()[compound.getInteger("mirror")];
    replaceWithAir = compound.getBoolean("replaceWithAir");
    pos = NBTUtil.getPosFromTag(compound.getCompoundTag("pos"));
  }

  public NBTTagCompound writeToNBT() {
    NBTTagCompound compound = new NBTTagCompound();
    compound.setTag("pos", NBTUtil.createPosTag(pos));
    compound.setString("structure", structure);
    compound.setInteger("rotation", rotation.ordinal());
    compound.setInteger("mirror", mirror.ordinal());
    compound.setBoolean("replaceWithAir", replaceWithAir);
    return compound;
  }
}
