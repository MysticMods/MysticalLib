package epicsquid.mysticallib.data;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.item.crafting.Ingredient;

public interface IIngredientProvider {
  String safeName();

  Ingredient asIngredient();

  InventoryChangeTrigger.Instance hasItem(MinMaxBounds.IntBound amount);

  InventoryChangeTrigger.Instance hasItem();

  default InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicates) {
    return new InventoryChangeTrigger.Instance(MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicates);
  }
}
