package epicsquid.mysticallib.util;

import net.minecraft.item.ItemStack;

public class ItemUtil {
  public static boolean equalWithoutSize(ItemStack stack1, ItemStack stack2) {
    return (stack1.isDamageable() == stack2.isDamageable() && stack1.getDamage() == stack2.getDamage()) && equalWithoutDamage(stack1, stack2);
  }

  public static boolean equalWithoutDamage (ItemStack stack1, ItemStack stack2) {
    return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
  }
}
