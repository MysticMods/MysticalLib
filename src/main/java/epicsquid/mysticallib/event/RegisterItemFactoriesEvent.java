package epicsquid.mysticallib.event;

import epicsquid.mysticallib.factories.IItemFactory;
import epicsquid.mysticallib.factories.ToolFactories;
import net.minecraftforge.eventbus.api.Event;

public class RegisterItemFactoriesEvent extends Event {

	public RegisterItemFactoriesEvent addFactory(String type, String name, IItemFactory factory) {
		switch (type) {
			case "TOOLS":
				ToolFactories.addFactory(name, factory);
				break;
		}
		return this;
	}

	public RegisterItemFactoriesEvent addOrReplaceFactory(String type, String name, IItemFactory factory) {
		switch (type) {
			case "TOOLS":
				ToolFactories.addOrReplaceFactory(name, factory);
				break;
		}
		return this;
	}
}
