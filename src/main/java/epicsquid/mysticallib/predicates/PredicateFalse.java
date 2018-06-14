package epicsquid.mysticallib.predicates;

import java.util.function.Predicate;

public class PredicateFalse<T> implements Predicate<T> {

  @Override
  public boolean test(T arg0) {
    return false;
  }

}
