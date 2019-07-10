package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.event.RegisterFXEvent;
import epicsquid.mysticallib.event.RegisterItemFactoriesEvent;
import epicsquid.mysticallib.world.GenerationRegistry;
import epicsquid.mysticallib.world.OreGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModSetup {

	public void init() {
		FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterFXEvent());
		MinecraftForge.EVENT_BUS.post(new RegisterItemFactoriesEvent());
		GenerationRegistry.oreGen.forEach(OreGenerator::init);
	}
}
