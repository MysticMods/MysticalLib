package epicsquid.mysticallib.fluid.predicate;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraftforge.fluids.Fluid;

public class PredicateBlacklist implements Predicate<Fluid> {
  Set<Fluid> fluids = new HashSet<>();

  public PredicateBlacklist(Fluid... fluids) {
    for (Fluid f : fluids) {
      this.fluids.add(f);
    }
  }

  @Override
  public boolean test(Fluid t) {
    for (Fluid f : fluids) {
      if (f.getName() == t.getName()) {
        return false;
      }
    }
    return true;
  }

}
