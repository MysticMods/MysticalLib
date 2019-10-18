package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;

public class HoeFactory extends ItemFactory {

  public HoeFactory() {
    super("hoe");
  }

  @Override
  public Item create(IMaterial material, String modid) {
    return new HoeItem(material.getTier(), material.getAttackSpeed(getName().toUpperCase()), material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
