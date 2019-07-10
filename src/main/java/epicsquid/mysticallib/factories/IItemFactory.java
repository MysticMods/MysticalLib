package epicsquid.mysticallib.factories;

import epicsquid.mysticallib.material.MaterialProperties;
import net.minecraft.item.Item;

public interface IItemFactory {

	Item create(MaterialProperties props);
}
