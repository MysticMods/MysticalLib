package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.item.KnifeItem;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.item.Item;

public class KnifeFactory extends ItemFactory {

  public KnifeFactory() {
    super("knife");
  }

  @Override
  public Item create(IMaterial material, String modid) {
    return new KnifeItem(material.getTier(), material.getAttackDamage(getName().toUpperCase()), material.getAttackSpeed(getName().toUpperCase()), material.getItemProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
