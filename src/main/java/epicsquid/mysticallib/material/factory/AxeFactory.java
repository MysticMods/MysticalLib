package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;

public class AxeFactory extends ItemFactory {

  public AxeFactory() {
    super("axe");
  }

  @Override
  public Item create(IMaterial material, String modid) {
    return new AxeItem(material.getTier(), material.getAttackDamage(getName().toUpperCase()), material.getAttackSpeed(getName().toUpperCase()), material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
