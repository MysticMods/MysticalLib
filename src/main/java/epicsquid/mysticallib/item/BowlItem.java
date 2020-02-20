package epicsquid.mysticallib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;

public class BowlItem extends MultiReturnItem {
  public BowlItem(Properties properties) {
    super(properties);
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.EAT;
  }

  @Override
  protected Item getReturnItem(ItemStack stack) {
    return Items.BOWL;
  }
}
