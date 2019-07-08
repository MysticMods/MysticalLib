package epicsquid.mysticallib.item;

import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;

public class BaseAxeItem extends AxeItem {

	public BaseAxeItem(IItemTier tier, float attackDamage, float attackSpeed, Properties props, String name) {
		super(tier, attackDamage, attackSpeed, props);
		setRegistryName(name);
	}
}
