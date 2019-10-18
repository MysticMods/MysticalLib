package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class SwordFactory extends ItemFactory {

  public SwordFactory() {
    super("sword");
  }

  @Override
  public Item create(IMaterial material, String modid) {
    return new SwordItem(material.getTier(), (int) material.getAttackDamage(getName().toUpperCase()), material.getAttackSpeed(getName().toUpperCase()), material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
