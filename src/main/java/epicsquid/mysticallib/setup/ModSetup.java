package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.event.RegisterFXEvent;
import epicsquid.mysticallib.material.MaterialGenerator;
import epicsquid.mysticallib.material.factory.*;
import epicsquid.mysticallib.world.GenerationRegistry;
import epicsquid.mysticallib.world.OreGenerator;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModSetup {

	public ModSetup() {
		MaterialGenerator matGen = MaterialGenerator.getInstance();
		matGen.addBlockFactory(new BlockFactory("block"));
		matGen.addBlockFactory(new OreFactory());
		matGen.addItemFactory(new ItemFactory("ingot"));
		matGen.addItemFactory(new ItemFactory("nugget"));
		matGen.addItemFactory(new ItemFactory());
		matGen.addItemFactory(new AxeFactory());
		matGen.addItemFactory(new ShovelFactory());
		matGen.addItemFactory(new PickaxeFactory());
		matGen.addItemFactory(new HoeFactory());
		matGen.addItemFactory(new SwordFactory());
		matGen.addItemFactory(new KnifeFactory());
		matGen.addItemFactory(new ArmorFactory(EquipmentSlotType.CHEST));
		matGen.addItemFactory(new ArmorFactory(EquipmentSlotType.FEET));
		matGen.addItemFactory(new ArmorFactory(EquipmentSlotType.HEAD));
		matGen.addItemFactory(new ArmorFactory(EquipmentSlotType.LEGS));
		matGen.addItemFactory(new ItemFactory("dust"));
	}

	public void init() {
		FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterFXEvent());
		GenerationRegistry.oreGen.forEach(OreGenerator::init);
	}
}
