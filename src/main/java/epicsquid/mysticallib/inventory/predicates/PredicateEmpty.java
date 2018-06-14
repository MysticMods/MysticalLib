package epicsquid.mysticallib.inventory.predicates;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

public class PredicateEmpty implements Predicate<ItemStack> {

  @Override
  public boolean test(ItemStack arg0) {
    return arg0.isEmpty();
  }

}
