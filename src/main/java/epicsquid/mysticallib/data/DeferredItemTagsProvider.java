package epicsquid.mysticallib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

import java.util.Arrays;
import java.util.function.Supplier;

public abstract class DeferredItemTagsProvider extends ItemTagsProvider {
  protected String name;

  public DeferredItemTagsProvider(DataGenerator generatorIn, String name) {
    super(generatorIn);
    this.name = name;
  }

  @SafeVarargs
  protected final void addItemsToTag(Tag<Item> tag, Supplier<? extends IItemProvider>... items) {
    getBuilder(tag).add(Arrays.stream(items).map(Supplier::get).map(IItemProvider::asItem).toArray(Item[]::new));
  }

  @SafeVarargs
  protected final void appendToTag(Tag<Item> tag, Tag<Item>... toAppend) {
    getBuilder(tag).add(toAppend);
  }

  @Override
  public String getName() {
    return name;
  }
}
