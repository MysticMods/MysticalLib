package epicsquid.mysticallib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

@SuppressWarnings("NullableProblems")
public class DrinkItem extends Item {
  public DrinkItem(Properties properties) {
    super(properties);
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.DRINK;
  }
}
