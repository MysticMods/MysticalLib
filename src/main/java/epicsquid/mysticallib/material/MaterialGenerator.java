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
    itemFactories.stream()
        .filter(material.matches())
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
    List<Block> result = new ArrayList<>();
    blockFactories.stream()
        .filter(material.matches())
        .forEach(factory -> result.add(factory.create(material, modid)));
    if (!result.isEmpty()) {
      result.forEach(registry::register);
    }
    return result;
  }
}
