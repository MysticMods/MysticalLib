package epicsquid.mysticallib.item;

import net.minecraft.item.IItemTier;

// TODO fix this once forge changes AT for axes (deliberately ambiguous name)
public class AxeItem extends net.minecraft.item.AxeItem {

	public AxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
}
