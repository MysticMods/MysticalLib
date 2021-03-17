package epicsquid.mysticallib.item;

import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBowl extends ItemMultiReturn {
  public ItemBowl() {
    super();
  }

  @Override
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.EAT;
  }


  @Override
  protected Item getReturnItem(ItemStack stack) {
    return Items.BOWL;
  }
}
