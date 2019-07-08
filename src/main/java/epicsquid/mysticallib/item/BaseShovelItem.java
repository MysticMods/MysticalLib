package epicsquid.mysticallib.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;

public class BaseShovelItem extends ShovelItem {

	public BaseShovelItem(IItemTier tier, int attackDamage, float attackSpeed, Properties props, String name) {
		super(tier, attackDamage, attackSpeed, props);
		setRegistryName(name);
	}
}
