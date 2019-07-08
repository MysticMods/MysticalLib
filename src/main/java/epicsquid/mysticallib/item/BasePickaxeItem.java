package epicsquid.mysticallib.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;

public class BasePickaxeItem extends PickaxeItem {

	public BasePickaxeItem(IItemTier tier, int attackDamage, float attackSpeed, Properties props, String name) {
		super(tier, attackDamage, attackSpeed, props);
		setRegistryName(name);
	}
}
