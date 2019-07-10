package epicsquid.mysticallib.item;

import net.minecraft.item.IItemTier;

// TODO fix this once forge changes AT for pickaxes (deliberately ambiguous name)
public class PickaxeItem extends net.minecraft.item.PickaxeItem {

	public PickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
}
