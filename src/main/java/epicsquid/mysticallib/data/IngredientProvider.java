package epicsquid.mysticallib.data;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class IngredientProvider<T extends IItemProvider & IForgeRegistryEntry<?>> implements IIngredientProvider {
  private Tag<Item> tag = null;
  private T provider = null;
  private Supplier<? extends T> supplier = null;

  public IngredientProvider(Tag<Item> tag) {
    this.tag = tag;
  }

  public IngredientProvider(T provider) {
    this.provider = provider;
  }

  public IngredientProvider(Supplier<? extends T> supplier) {
    this.supplier = supplier;
  }

  public String safeName() {
    if (tag != null) {
      return safeName(tag.getId());
    } else if (provider != null) {
      return safeName(provider.getRegistryName());
    } else {
      return safeName(supplier.get().getRegistryName());
    }
  }

  private String safeName(ResourceLocation nameSource) {
    return nameSource.getPath().replace('/', '_');
  }

  @Override
  public Ingredient asIngredient() {
    if (tag != null) {
      return Ingredient.fromTag(tag);
    } else if (provider != null) {
      return Ingredient.fromItems(provider.asItem());
    } else {
      return Ingredient.fromItems(supplier.get().asItem());
    }
  }


  @Override
  public InventoryChangeTrigger.Instance hasItem() {
    return hasItem(hasItemInternal().build());
  }

  private ItemPredicate.Builder hasItemInternal() {
    if (provider != null) {
      return ItemPredicate.Builder.create().item(provider);
    } else if (tag != null) {
      return ItemPredicate.Builder.create().tag(tag);
    } else {
      return ItemPredicate.Builder.create().item(supplier.get());
    }
  }
}
