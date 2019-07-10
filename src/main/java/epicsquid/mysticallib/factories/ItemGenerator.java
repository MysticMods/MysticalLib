package epicsquid.mysticallib.factories;

import epicsquid.mysticallib.material.MaterialProperties;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemGenerator {

	private List<IItemFactory> itemFactories = new ArrayList<>();

	public ItemGenerator addFactory(IItemFactory factory) {
		itemFactories.add(factory);
		return this;
	}

	public ItemGenerator addAllFactories(Collection<IItemFactory> factories) {
		itemFactories.addAll(factories);
		return this;
	}

	public void execute(MaterialProperties props, RegistryEvent.Register<Item> event) {
		itemFactories.forEach(factory -> event.getRegistry().register(factory.create(props)));
	}
}
