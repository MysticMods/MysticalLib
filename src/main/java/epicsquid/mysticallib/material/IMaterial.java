package epicsquid.mysticallib.material;

import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;

public interface IMaterial {

	IItemTier getTier();

	IArmorMaterial getArmor();

	Item.Properties getProps();

	String getName();
}
