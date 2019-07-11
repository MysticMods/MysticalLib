package epicsquid.mysticallib.factories;

import epicsquid.mysticallib.item.AxeItem;
import epicsquid.mysticallib.item.KnifeItem;
import epicsquid.mysticallib.item.PickaxeItem;
import epicsquid.mysticallib.material.MaterialProperties;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ToolType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ToolFactories {

	private static final Map<String, IItemFactory> FACTORIES = new HashMap<>();

	public static Collection<IItemFactory> getFactories() {
		return FACTORIES.values();
	}

	public static void addFactory(String name, IItemFactory factory) {
		FACTORIES.putIfAbsent(name, factory);
	}

	public static void addOrReplaceFactory(String name, IItemFactory factory) {
		FACTORIES.put(name, factory);
	}

	public static class SwordFactory implements IItemFactory {
		@Override
		public Item create(MaterialProperties props) {
			return new SwordItem(props.getTier(), props.getDamage("SWORD"), props.getAttackSpeed("SWORD"), props.getItemProps()).setRegistryName(props.getName());
		}
	}

	public static class PickaxeFactory implements IItemFactory {
		@Override
		public Item create(MaterialProperties props) {
			return new PickaxeItem(props.getTier(), props.getDamage(), props.getAttackSpeed(), props.getItemProps().addToolType(ToolType.PICKAXE, props.getTier().getHarvestLevel())).setRegistryName(props.getName());
		}
	}

	public static class AxeFactory implements IItemFactory {
		@Override
		public Item create(MaterialProperties props) {
			return new AxeItem(props.getTier(), props.getDamage("AXE"), props.getAttackSpeed("AXE"), props.getItemProps().addToolType(ToolType.AXE, props.getTier().getHarvestLevel())).setRegistryName(props.getName());
		}
	}

	public static class ShovelFactory implements IItemFactory {
		@Override
		public Item create(MaterialProperties props) {
			return new ShovelItem(props.getTier(), props.getDamage(), props.getAttackSpeed(), props.getItemProps().addToolType(ToolType.SHOVEL, props.getTier().getHarvestLevel())).setRegistryName(props.getName());
		}
	}

	public static class HoeFactory implements IItemFactory {
		@Override
		public Item create(MaterialProperties props) {
			return new HoeItem(props.getTier(), props.getAttackSpeed(), props.getItemProps()).setRegistryName(props.getName());
		}
	}

	public static class KnifeFactory implements IItemFactory {
		@Override
		public Item create(MaterialProperties props) {
			return new KnifeItem(props.getTier(), props.getDamage("KNIFE"), props.getAttackSpeed("KNIFE"), props.getItemProps()).setRegistryName(props.getName());
		}
	}
}
