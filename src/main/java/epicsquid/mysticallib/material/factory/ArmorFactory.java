package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class ArmorFactory implements IItemMaterialFactory {

	private EquipmentSlotType type;

	public ArmorFactory(EquipmentSlotType type) {
		this.type = type;
	}

	@Override
	public Item create(IMaterial material, String modid) {
		return new ArmorItem(material.getArmor(), type, material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
	}

	@Override
	public String getSuffix() {
		return "_" + getName();
	}

	@Override
	public String getName() {
		switch (type) {
			case FEET:
				return "boots";
			case HEAD:
				return "helmet";
			case LEGS:
				return "leggings";
			case CHEST:
				return "chestplate";
			default:
				return "helmet";
		}
	}
}
