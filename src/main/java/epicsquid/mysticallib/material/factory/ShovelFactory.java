package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

public class ShovelFactory extends ItemFactory {

	public ShovelFactory() {
		super("shovel");
	}

	@Override
	public Item create(IMaterial material, String modid) {
		return new ShovelItem(material.getTier(), material.getAttackDamage(getName().toUpperCase()), material.getAttackSpeed(getName().toUpperCase()), material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
	}
}
