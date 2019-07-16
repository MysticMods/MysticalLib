package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;

public class PickaxeFactory extends ItemFactory {

	public PickaxeFactory() {
		super("pickaxe");
	}

	@Override
	public Item create(IMaterial material, String modid) {
		return new PickaxeItem(material.getTier(), (int) material.getAttackDamage(getName().toUpperCase()), material.getAttackSpeed(getName().toUpperCase()), material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
	}
}
