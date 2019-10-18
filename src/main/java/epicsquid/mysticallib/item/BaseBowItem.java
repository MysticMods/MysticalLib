package epicsquid.mysticallib.item;

import net.minecraft.item.BowItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class BaseBowItem extends BowItem {

  private final IItemTier tier;

  public BaseBowItem(Properties props, IItemTier tier) {
    super(props);
    this.tier = tier;
  }

  @Override
  public int getItemEnchantability() {
    return tier.getEnchantability();
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    return this.tier.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
  }
}
