package epicsquid.mysticallib.material;

import epicsquid.mysticallib.material.factory.FactoryPredicates;

import java.util.function.Predicate;

public interface IMetalMaterial extends IMaterial {

  @Override
  default Predicate<IMaterialFactory<?>> matches() {
    return FactoryPredicates.ARMOR.or(FactoryPredicates.METAL).or(FactoryPredicates.STORAGE_BLOCK).or(FactoryPredicates.ORE).or(FactoryPredicates.TOOLS);
  }
}
