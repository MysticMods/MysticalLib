package epicsquid.mysticallib.util;

import epicsquid.mysticallib.MysticalLib;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ItemUtil {
  public static boolean equalWithoutSize(ItemStack stack1, ItemStack stack2) {
    return (stack1.isDamageable() == stack2.isDamageable() && stack1.getDamage() == stack2.getDamage()) && equalWithoutDamage(stack1, stack2);
  }

  public static boolean equalWithoutDamage(ItemStack stack1, ItemStack stack2) {
    return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
  }

  public static ItemEntity spawnItem(World world, BlockPos pos, ItemStack stack) {
    return spawnItem(world, pos, stack, -1);
  }

  public static ItemEntity spawnItem(World world, BlockPos pos, ItemStack stack, float hoverStart) {
    return spawnItem(world, pos, stack, true, -1, hoverStart);
  }

  public static ItemEntity spawnItem(World world, BlockPos pos, ItemStack stack, int ticks) {
    return spawnItem(world, pos, stack, true, ticks, -1);
  }

  public static ItemEntity spawnItem(World world, BlockPos pos, ItemStack stack, boolean offset) {
    return spawnItem(world, pos, stack, offset, -1, -1);
  }

  public static ItemEntity spawnItem(World world, BlockPos pos, ItemStack stack, boolean offset, int ticks, float hoverStart) {
    return spawnItem(world, pos.getX(), pos.getY(), pos.getZ(), offset, stack, ticks, hoverStart);
  }

  private static Field hoverStart = ObfuscationReflectionHelper.findField(ItemEntity.class, "field_70290_d");
  private static boolean accessible;

  static {
    try {
      Field modifiers = Field.class.getDeclaredField("modifiers");
      modifiers.setAccessible(true);
      modifiers.setInt(hoverStart, hoverStart.getModifiers() & ~Modifier.FINAL);
      accessible = true;
    } catch (IllegalAccessException | NoSuchFieldException ignored) {
      MysticalLib.LOG.error("Unable to make 'hoverStart' accessible.");
      accessible = false;
    }
  }

  public static void setHoverStart(ItemEntity item, float value) {
    if (accessible) {
      try {
        hoverStart.set(item, value);
      } catch (IllegalAccessException ignored) {
      }
    }
  }

  public static ItemEntity spawnItem(World world, double x, double y, double z, boolean offset, ItemStack stack, int ticks, float hoverStart) {
    if (offset) {
      x += 0.5;
      y += 0.5;
      z += 0.5;
    }
    ItemEntity item = new ItemEntity(world, x, y, z, stack);
    if (ticks != -1) {
      item.setPickupDelay(ticks);
    }
    if (hoverStart != -1) {
      setHoverStart(item, hoverStart);
    }
    return spawnItem(world, item);
  }

  public static ItemEntity spawnItem(World world, ItemEntity item) {
    item.setMotion(0, 0, 0);
    world.addEntity(item);
    return item;
  }

/*
  public static ItemStack stackFromState(IBlockState state) {
    Block block = state.getBlock();
    Item item = Item.getItemFromBlock(block);
    int meta = block.getMetaFromState(state);
    return new ItemStack(item, 1, meta);
  }

  @Nullable
  @SuppressWarnings("deprecation")
  public static IBlockState stateFromStack(ItemStack stack) {
    Item item = stack.getItem();
    if (!(item instanceof ItemBlock)) return null;

    Block block = ((ItemBlock) item).getBlock();
    return block.getStateFromMeta(stack.getMetadata());
  }
*/

}
