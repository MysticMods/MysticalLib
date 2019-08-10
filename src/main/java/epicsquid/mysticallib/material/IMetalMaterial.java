package epicsquid.mysticallib.material;

import java.util.function.Predicate;

import epicsquid.mysticallib.material.factory.FactoryPredicates;

public interface IMetalMaterial extends IMaterial {

	@Override
	default Predicate<IMaterialFactory<?>> matches() {
		return FactoryPredicates.ARMOR.or(FactoryPredicates.METAL).or(FactoryPredicates.STORAGE_BLOCK).or(FactoryPredicates.ORE).or(FactoryPredicates.TOOLS);
	}
}
