package epicsquid.mysticallib.material.factory;

import java.util.function.Predicate;

import epicsquid.mysticallib.material.IMaterialFactory;

public class FactoryPredicates {

	public static final Predicate<IMaterialFactory<?>> TOOLS = mat -> mat.getName().equals("pickaxe") || mat.getName().equals("axe") || mat.getName().equals("sword") || mat.getName().equals("hoe") || mat.getName().equals("shovel") || mat.getName().equals("knife");
	public static final Predicate<IMaterialFactory<?>> ARMOR = mat -> mat.getName().equals("helmet") || mat.getName().equals("chestplate") || mat.getName().equals("boots") || mat.getName().equals("leggings");
	public static final Predicate<IMaterialFactory<?>> ITEM = mat -> mat.getName().equals("");
	public static final Predicate<IMaterialFactory<?>> STORAGE_BLOCK = mat -> mat.getName().equals("block");
	public static final Predicate<IMaterialFactory<?>> ORE = mat -> mat.getName().equals("ore");
	public static final Predicate<IMaterialFactory<?>> METAL = mat -> mat.getName().equals("ingot") || mat.getName().equals("dust") || mat.getName().equals("nugget");
	public static final Predicate<IMaterialFactory<?>> DECO = mat -> mat.getName().equals("slab") || mat.getName().equals("stair");
	public static final Predicate<IMaterialFactory<?>> WALL = mat -> mat.getName().equals("wall") || mat.getName().equals("stair");
	public static final Predicate<IMaterialFactory<?>> FENCE = mat -> mat.getName().equals("fence") || mat.getName().equals("stair");
	public static final Predicate<IMaterialFactory<?>> BUTTON_WOOD = mat -> mat.getName().equals("button_wood");
	public static final Predicate<IMaterialFactory<?>> BUTTON_STONE = mat -> mat.getName().equals("button_stone");

}
