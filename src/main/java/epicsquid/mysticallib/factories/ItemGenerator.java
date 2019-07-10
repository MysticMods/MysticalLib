package epicsquid.mysticallib.factories;

import epicsquid.mysticallib.material.MaterialProperties;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {

	private List<IItemFactory> itemFactories = new ArrayList<>();

	public void execute(MaterialProperties props, RegistryEvent.Register<Item> event) {
		itemFactories.forEach(factory -> event.getRegistry().register(factory.create(props)));
	}
}
