package epicsquid.mysticallib.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class SpearItem extends SwordItem {
  public SpearItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }
}
