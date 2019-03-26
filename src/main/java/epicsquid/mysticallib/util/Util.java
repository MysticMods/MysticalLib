package epicsquid.mysticallib.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import net.minecraftforge.items.IItemHandler;

public class Util {

  public static Random rand = new Random();

  @Nonnull
  public static <T extends TileEntity> List<T> getTileEntitiesWithin(@Nonnull World world, @Nonnull Class<? extends T> teClass, BlockPos pos, int radius) {
    List<T> tiles = new ArrayList<T>();
    for (int i = pos.getX() - radius; i <= pos.getX() + radius; i++) {
      for (int j = pos.getY() - radius; j <= pos.getY() + radius; j++) {
        for (int k = pos.getZ() - radius; k <= pos.getZ() + radius; k++) {
          BlockPos p = new BlockPos(i, j, k);
          Chunk c = world.getChunkFromBlockCoords(p);
          if (c.isLoaded()) {
            TileEntity t = world.getChunkFromBlockCoords(p).getTileEntity(p, EnumCreateEntityType.CHECK);
            if (t != null && teClass.isInstance(t)) {
              tiles.add((T) t);
            }
          }
        }
      }
    }
    return tiles;
  }

  public static <T extends Entity> List<T> getEntitiesWithinRadius(World world, Class <? extends T > classEntity, BlockPos pos, float xradius, float yradius, float zradius)
  {
    return world.getEntitiesWithinAABB(classEntity, new AxisAlignedBB(pos.getX() - xradius, pos.getY() - yradius, pos.getZ() - zradius,
            pos.getX() + xradius, pos.getY() + yradius, pos.getZ() + zradius));
  }

  public static List<BlockPos> getBlocksWithinRadius(World world, BlockPos pos, float xradius, float yradius, float zradius, Block block){
    List<BlockPos> blockList = new ArrayList<>();
    for(int x = (int) -xradius; x <= xradius; x++){
      for(int z = (int) -zradius; z <= zradius; z++){
        for(int y = (int) -yradius; y <= yradius; y++){
          if(world.getBlockState(pos.add(x, y, z)).getBlock() == block){
            blockList.add(pos.add(x, y, z));
          }
        }
      }
    }
    return blockList;
  }

  @Nonnull
  public static String lowercase(@Nonnull String s) {
    String f = "";
    for (int i = 0; i < s.length(); i++) {
      String c = s.substring(i, i + 1);
      if (c.toUpperCase().compareTo(c) == 0) {
        if (i > 0) {
          f += "_";
        }
        f += c.toLowerCase();
      } else {
        f += c;
      }
    }
    return f;
  }

  public static int intColor(int r, int g, int b) {
    return ((255 << 24) + r * 65536 + g * 256 + b);
  }

  @Nonnull
  public static String getLowercaseClassName(@Nonnull Class c) {
    String[] nameParts = c.getTypeName().split("\\.");
    String className = nameParts[nameParts.length - 1];
    return lowercase(className);
  }

  public static void spawnInventoryInWorld(World world, double x, double y, double z, IItemHandler inventory) {
    if (inventory != null && !world.isRemote) {
      for (int i = 0; i < inventory.getSlots(); i++) {
        if (!inventory.getStackInSlot(i).isEmpty()) {
          world.spawnEntity(new EntityItem(world, x, y, z, inventory.getStackInSlot(i)));
        }
      }
    }
  }
}
