package epicsquid.mysticallib.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class BaseSwordItem extends SwordItem {

	public BaseSwordItem(IItemTier tier, int attackDamage, float attackSpeed, Properties props, String name) {
		super(tier, attackDamage, attackSpeed, props);
		setRegistryName(name);
	}
}
