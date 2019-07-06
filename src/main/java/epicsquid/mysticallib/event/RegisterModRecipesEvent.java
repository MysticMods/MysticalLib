package epicsquid.mysticallib.event;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegisterModRecipesEvent extends Event {
	IForgeRegistryEntry<IRecipe> registry = null;

	public RegisterModRecipesEvent(IForgeRegistryEntry<IRecipe> registry) {
		this.registry = registry;
	}

	public IForgeRegistryEntry<IRecipe> getRegistry() {
		return registry;
	}
}
