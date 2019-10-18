package epicsquid.mysticallib.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class StructureData implements IGeneratable {
  public Map<Vec3i, String> data = new HashMap<>();
  public Map<String, BlockState> blocks = new HashMap<>();
  private int width = 0, height = 0, length = 0;

  public void addBlock(@Nonnull String string, @Nonnull BlockState state) {
    blocks.put(string, state);
  }

  public void addLayer(@Nonnull String[] layer, int y) {
    for (int i = 0; i < layer.length; i++) {
      for (int j = 0; j < layer[i].length(); j++) {
        data.put(new Vec3i(i, y, j), layer[i].substring(j, j + 1));
      }
    }
  }

  public void calcDimensions() {
    int minX = 0, minY = 0, minZ = 0, maxX = 0, maxY = 0, maxZ = 0;
    for (Vec3i v : data.keySet()) {
      if (v.getX() < minX)
        minX = v.getX();
      if (v.getY() < minY)
        minY = v.getY();
      if (v.getZ() < minZ)
        minZ = v.getZ();
      if (v.getX() > maxX)
        maxX = v.getX();
      if (v.getY() > maxY)
        maxY = v.getY();
      if (v.getY() > maxZ)
        maxZ = v.getZ();
    }
    width = maxX - minX;
    height = maxY - minY;
    length = maxZ - minZ;
  }

  public int getWidth() {
    return width;
  }

  public int getLength() {
    return length;
  }

  @Override
  public void generateIn(@Nonnull World world, int x, int y, int z, @Nonnull Rotation rotation, @Nonnull Mirror doMirror, boolean replaceWithAir,
                         boolean force) {
    calcDimensions();
    for (Entry<Vec3i, String> e : data.entrySet()) {
      Vec3i v = e.getKey();
      String b = e.getValue();
      if (rotation == Rotation.CLOCKWISE_180) {
        placeBlock(world, new BlockPos(x + v.getX(), y + v.getY(), z - v.getZ() + length), blocks.get(b), rotation, doMirror, replaceWithAir, force);
      }
      if (rotation == Rotation.COUNTERCLOCKWISE_90) {
        placeBlock(world, new BlockPos(x + v.getZ(), y + v.getY(), z + v.getX()), blocks.get(b), rotation, doMirror, replaceWithAir, force);
      }
      if (rotation == Rotation.NONE) {
        placeBlock(world, new BlockPos(x - v.getX() + width, y + v.getY(), z + v.getZ()), blocks.get(b), rotation, doMirror, replaceWithAir, force);
      }
      if (rotation == Rotation.CLOCKWISE_90) {
        placeBlock(world, new BlockPos(x - v.getZ() + length, y + v.getY(), z - v.getX() + width), blocks.get(b), rotation, doMirror, replaceWithAir, force);
      }
    }
  }

  private void placeBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Rotation rotation, @Nonnull Mirror mirror, boolean replaceWithAir, boolean force) {
    placeBlock(world, pos, state, rotation, mirror, replaceWithAir, force, x -> true);
  }

  private void placeBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Rotation rotation, @Nonnull Mirror mirror,
                          boolean replaceWithAir, boolean force, Predicate<BlockState> replaceable) {
    BlockState at = world.getBlockState(pos);
    if (world.isAirBlock(pos) || at.getBlock().isReplaceableOreGen(state, world, pos, replaceable) || force) {
      if (state.getBlock() != Blocks.AIR || state.getBlock() == Blocks.AIR && replaceWithAir) {
        world.setBlockState(pos, state.rotate(rotation).mirror(mirror));
      }
    }
  }
}
