package epicsquid.mysticallib.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryUtil {
  public static int attemptInsert(ItemStack stack, IItemHandler inventory, boolean simulate) {
    int count = stack.getCount();
    ItemStack toInsert = stack.copy();
    for (int i = 0; i < inventory.getSlots() && !toInsert.isEmpty(); i++) {
      ItemStack s = inventory.insertItem(i, toInsert.copy(), simulate);
      toInsert.setCount(s.getCount());
    }
    return count - toInsert.getCount();
  }

  public static int attemptInsert(ItemStack stack, IItemHandler inventory, boolean simulate, int startSlot, int endSlot) {
    int count = stack.getCount();
    ItemStack toInsert = stack.copy();
    for (int i = startSlot; i < endSlot && !toInsert.isEmpty(); i++) {
      ItemStack s = inventory.insertItem(i, toInsert.copy(), simulate);
      toInsert.setCount(s.getCount());
    }
    return count - toInsert.getCount();
  }

  public static boolean stackMatches(ItemStack stack, Object recipeInput) {
    if (recipeInput instanceof ItemStack) {
      if (!ItemStack.areItemsEqual(stack, (ItemStack) recipeInput)) {
        return false;
      }
    } else if (recipeInput instanceof OreStack) {
      int id = OreDictionary.getOreID(((OreStack) recipeInput).oreId);
      int[] ids = OreDictionary.getOreIDs(stack);
      boolean hasMatch = false;
      for (int pid : ids) {
        if (pid == id) {
          hasMatch = true;
        }
      }
      if (!hasMatch) {
        return hasMatch;
      }
    } else if (recipeInput instanceof String) {
      int id = OreDictionary.getOreID((String) recipeInput);
      int[] ids = OreDictionary.getOreIDs(stack);
      boolean hasMatch = false;
      for (int pid : ids) {
        if (pid == id) {
          hasMatch = true;
        }
      }
      if (!hasMatch) {
        return hasMatch;
      }
    }
    return true;
  }
}
