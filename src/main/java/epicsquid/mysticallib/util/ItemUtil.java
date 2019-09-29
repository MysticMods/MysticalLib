package epicsquid.mysticallib.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

// TODO: Move to lib
public class ItemUtil {
  public static boolean equalWithoutSize(ItemStack item1, ItemStack item2) {
    if (item1.getItem() != item2.getItem()) {
      return false;
    } else if (item1.getItemDamage() != item2.getItemDamage()) {
      return false;
    } else if (item1.getTagCompound() == null && item2.getTagCompound() != null) {
      return false;
    } else {
      return (item1.getTagCompound() == null || item1.getTagCompound().equals(item2.getTagCompound())) && item1.areCapsCompatible(item2);
    }
  }

  public static void spawnItem(World world, BlockPos pos, ItemStack stack) {
    spawnItem(world, pos, stack, -1);
  }

  public static void spawnItem(World world, BlockPos pos, ItemStack stack, float hoverStart) {
    spawnItem(world, pos, stack, true, -1, hoverStart);
  }

  public static void spawnItem(World world, BlockPos pos, ItemStack stack, int ticks) {
    spawnItem(world, pos, stack, true, ticks, -1);
  }

  public static void spawnItem(World world, BlockPos pos, ItemStack stack, boolean offset) {
    spawnItem(world, pos, stack, offset, -1, -1);
  }

  public static void spawnItem(World world, BlockPos pos, ItemStack stack, boolean offset, int ticks, float hoverStart) {
    spawnItem(world, pos.getX(), pos.getY(), pos.getZ(), offset, stack, ticks, hoverStart);
  }

  public static void spawnItem(World world, double x, double y, double z, boolean offset, ItemStack stack, int ticks, float hoverStart) {
    if (offset) {
      x += 0.5;
      y += 0.5;
      z += 0.5;
    }
    EntityItem item = new EntityItem(world, x, y, z, stack);
    if (ticks != -1) {
      item.setPickupDelay(ticks);
    }
    if (hoverStart != -1) {
      item.hoverStart = hoverStart;
    }
    spawnItem(world, item);
  }

  public static void spawnItem(World world, EntityItem item) {
    item.motionZ = 0;
    item.motionX = 0;
    item.motionY = 0;
    world.spawnEntity(item);
  }

  public static ItemStack stackFromState (IBlockState state) {
    Block block = state.getBlock();
    Item item = Item.getItemFromBlock(block);
    int meta = block.getMetaFromState(state);
    return new ItemStack(item, 1, meta);
  }

  @Nullable
  @SuppressWarnings("deprecation")
  public static IBlockState stateFromStack (ItemStack stack) {
    Item item = stack.getItem();
    if (!(item instanceof ItemBlock)) return null;

    Block block = ((ItemBlock) item).getBlock();
    return block.getStateFromMeta(stack.getMetadata());
  }

}