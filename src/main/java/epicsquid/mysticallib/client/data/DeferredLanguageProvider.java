package epicsquid.mysticallib.client.data;

import epicsquid.mysticallib.util.Util;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class DeferredLanguageProvider extends LanguageProvider {
  public DeferredLanguageProvider(DataGenerator gen, String modid) {
    super(gen, modid, "en_us");
  }

  @Override
  protected void addTranslations() {
  }

  protected String automatic (Supplier<? extends IForgeRegistryEntry<?>> supplier) {
    return Util.englishName(Objects.requireNonNull(supplier.get().getRegistryName(), "Must provide a registered registry item"));
  }

  protected void addBlock (Supplier<? extends Block> block) {
    addBlock(block, automatic(block));
  }

  protected void addItem (Supplier<? extends Item> item) {
    addItem(item, automatic(item));
  }

  protected void addItemGroup (ItemGroup group, String name) {
    add(group.getTranslationKey(), name);
  }

  protected void addEntityType (Supplier<? extends EntityType<?>> entity) {
    addEntityType(entity, automatic(entity));
  }

  protected void addTooltip(Supplier<? extends IItemProvider> obj, String tooltip) {
    add(obj.get().asItem().getTranslationKey() + ".desc", tooltip);
  }

  protected void addTooltip(Supplier<? extends IItemProvider> obj, List<String> tooltip) {
    for (int i = 0; i < tooltip.size(); i++) {
      add(obj.get().asItem().getTranslationKey() + ".desc." + i, tooltip.get(i));
    }
  }
}
