package epicsquid.mysticallib.predicates.inventory;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class PredicateFurnaceFuel implements Predicate<ItemStack> {

  @Override
  public boolean test(ItemStack arg0) {
    return TileEntityFurnace.isItemFuel(arg0) || arg0.isEmpty();
  }

}
