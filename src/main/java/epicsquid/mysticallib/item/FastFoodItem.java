package epicsquid.mysticallib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FastFoodItem extends Item {
  public FastFoodItem(Properties properties) {
    super(properties);
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    if (stack.getItem().isFood()) {
      return this.getFood().isFastEating() ? 6 : 32;
    } else {
      return 0;
    }
  }
}
