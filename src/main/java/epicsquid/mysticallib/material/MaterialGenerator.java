package epicsquid.mysticallib.material;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class MaterialGenerator {

	private static MaterialGenerator instance;

	private List<IMaterialFactory<? extends Item>> itemFactories = new ArrayList<>();
	private List<IMaterialFactory<? extends Block>> blockFactories = new ArrayList<>();

	private MaterialGenerator() {
	}

	public static MaterialGenerator getInstance() {
		return instance == null ? instance = new MaterialGenerator() : instance;
	}

	public void addItemFactory(IMaterialFactory<? extends Item> factory) {
		addItemFactory(factory, false);
	}

	public void addItemFactory(IMaterialFactory<? extends Item> factory, boolean replace) {
		if (replace) {
			itemFactories.removeIf(f -> factory.getName().equals(f.getName()));
		} else if (itemFactories.stream().noneMatch(f -> f.getName().equals(factory.getName()))) {
			itemFactories.add(factory);
		}
	}

	public void generateItems(IMaterial material, IForgeRegistry<Item> registry, String modid) {
		if (!material.getWhitelist().isEmpty()) {
			generateItems(material, registry, modid, material.isBlacklist(), material.getWhitelist());
			return;
		}
		itemFactories.forEach(factory -> registry.register(factory.create(material, modid)));
	}

	public void generateItems(IMaterial material, IForgeRegistry<Item> registry, String modid, List<String> whitelist) {
		generateItems(material, registry, modid, false, whitelist);
	}

	public void generateItems(IMaterial material, IForgeRegistry<Item> registry, String modid, boolean isBlacklist, List<String> whitelist) {
		itemFactories.stream()
						.filter(factory -> isBlacklist != whitelist.contains(factory.getName()))
						.forEach(factory -> registry.register(factory.create(material, modid)));
	}

	public void addBlockFactory(IMaterialFactory<? extends Block> factory) {
		addBlockFactory(factory, false);
	}

	public void addBlockFactory(IMaterialFactory<? extends Block> factory, boolean replace) {
		if (replace) {
			blockFactories.removeIf(f -> factory.getName().equals(f.getName()));
		} else if (blockFactories.stream().noneMatch(f -> f.getName().equals(factory.getName()))) {
			blockFactories.add(factory);
		}
	}

	public List<Block> generateBlocks(IMaterial material, IForgeRegistry<Block> registry, String modid) {
		if (!material.getWhitelist().isEmpty()) {
			return generateBlocks(material, registry, modid, material.isBlacklist(), material.getWhitelist());
		}
		List<Block> result = new ArrayList<>();
		blockFactories.forEach(factory -> result.add(factory.create(material, modid)));
		result.forEach(registry::register);
		return result;
	}

	public List<Block> generateBlocks(IMaterial material, IForgeRegistry<Block> registry, String modid, List<String> whitelist) {
		return generateBlocks(material, registry, modid, false, whitelist);
	}

	public List<Block> generateBlocks(IMaterial material, IForgeRegistry<Block> registry, String modid, boolean isBlacklist, List<String> whitelist) {
		List<Block> result = new ArrayList<>();
		blockFactories.stream()
						.filter(factory -> isBlacklist != whitelist.contains(factory.getName()))
						.forEach(factory -> result.add(factory.create(material, modid)));
		result.forEach(registry::register);
		return result;
	}
}
