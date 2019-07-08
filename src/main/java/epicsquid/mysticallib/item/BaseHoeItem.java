package epicsquid.mysticallib.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

public class BaseHoeItem extends HoeItem {

	public BaseHoeItem(IItemTier tier, float speed, Properties props, String name) {
		super(tier, speed, props);
		setRegistryName(name);
	}
}
