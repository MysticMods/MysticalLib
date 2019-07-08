package epicsquid.mysticallib.item;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class BaseItem extends Item {

  public BaseItem(Properties props, @Nonnull String name) {
    super(props);
    setRegistryName(name);
  }
}
