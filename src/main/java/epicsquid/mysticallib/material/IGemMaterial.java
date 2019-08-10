package epicsquid.mysticallib.material;

import java.util.function.Predicate;

import epicsquid.mysticallib.material.factory.FactoryPredicates;

public interface IGemMaterial extends IMaterial {

	@Override
	default Predicate<IMaterialFactory<?>> matches() {
		return FactoryPredicates.ARMOR.or(FactoryPredicates.TOOLS).or(FactoryPredicates.ITEM).or(FactoryPredicates.STORAGE_BLOCK).or(FactoryPredicates.ORE);
	}
}
