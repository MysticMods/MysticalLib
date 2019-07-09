package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.world.GenerationRegistry;
import epicsquid.mysticallib.world.OreGenerator;

public class ModSetup {

	public void init() {
		GenerationRegistry.oreGen.forEach(OreGenerator::init);
	}
}
