package epicsquid.mysticallib.item;

import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDrink extends ItemMultiReturn {
  @Override
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.DRINK;
  }

  @Override
  protected Item getReturnItem(ItemStack stack) {
    return Items.GLASS_BOTTLE;
  }
}
