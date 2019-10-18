package epicsquid.mysticallib.material;

import epicsquid.mysticallib.material.factory.FactoryPredicates;

import java.util.function.Predicate;

public interface IGemMaterial extends IMaterial {

  @Override
  default Predicate<IMaterialFactory<?>> matches() {
    return FactoryPredicates.ARMOR.or(FactoryPredicates.TOOLS).or(FactoryPredicates.ITEM).or(FactoryPredicates.STORAGE_BLOCK).or(FactoryPredicates.ORE);
  }
}
