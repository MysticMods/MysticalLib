package epicsquid.mysticallib.data;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.*;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
public abstract class DeferredRecipeProvider extends RecipeProvider {
  private String modid;

  public DeferredRecipeProvider(DataGenerator generatorIn, String modid) {
    super(generatorIn);
    this.modid = modid;
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void ore(Tag<Item> source, Supplier<T> result, float xp, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(source), result.get(), xp, 200).addCriterion("has_" + safeName(source.getId()), this.hasItem(source)).build(consumer, safeId(result.get()) + "_from_smelting");
    CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(source), result.get(), xp, 100).addCriterion("has_" + safeName(source.getId()), this.hasItem(source)).build(consumer, safeId(result.get()) + "_from_blasting");
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void recycle (Supplier<? extends T> source, Supplier<? extends T> result, float xp, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 200)
        .addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get()))
        .build(consumer, safeId(result.get()) + "_from_smelting");
    CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 100)
        .addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get()))
        .build(consumer, safeId(result.get()) + "_from_blasting");
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void recycle (Supplier<? extends T> source, Supplier<? extends T> result, float xp, String namespace, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 200)
        .addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get()))
        .build(consumer, new ResourceLocation(namespace, safeName(result.get()) + "_from_smelting_" + safeName(source.get())));
    CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 100)
        .addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get()))
        .build(consumer, new ResourceLocation(namespace, safeName(result.get()) + "_from_blasting_" + safeName(source.get())));
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void recycle (Tag<Item> tag, Supplier<? extends T> result, float xp, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(tag), result.get(), xp, 200)
        .addCriterion("has_" + safeName(result.get().getRegistryName()), this.hasItem(result.get()))
        .build(consumer, safeId(result.get()) + "_from_smelting");
    CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(tag), result.get(), xp, 100)
        .addCriterion("has_" + safeName(result.get().getRegistryName()), this.hasItem(result.get()))
        .build(consumer, safeId(result.get()) + "_from_blasting");
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void food(Supplier<? extends T> source, Supplier<? extends T> result, float xp, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 200).addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get())).build(consumer);
    CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 100, IRecipeSerializer.SMOKING).addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get())).build(consumer, safeId(result.get()) + "_from_smoker");
    CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get())).build(consumer, safeId(result.get()) + "_from_campfire");
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void food(Tag<Item> source, Supplier<? extends T> result, float xp, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(source), result.get(), xp, 200).addCriterion("has_" + safeName(source.getId()), this.hasItem(source)).build(consumer);
    CookingRecipeBuilder.cookingRecipe(Ingredient.fromTag(source), result.get(), xp, 100, IRecipeSerializer.SMOKING).addCriterion("has_" + safeName(source.getId()), this.hasItem(source)).build(consumer, safeId(result.get()) + "_from_smoker");
    CookingRecipeBuilder.cookingRecipe(Ingredient.fromTag(source), result.get(), xp, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_" + safeName(source.getId()), this.hasItem(source)).build(consumer, safeId(result.get()) + "_from_campfire");
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void smelting(Supplier<? extends T> source, Supplier<? extends T> result, float xp, boolean blast, Consumer<IFinishedRecipe> consumer) {
    CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 200).addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get())).build(consumer, safeId(result.get()) + "_from_smelting");
    if (blast) {
      CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(source.get()), result.get(), xp, 100).addCriterion("has_" + safeName(source.get().getRegistryName()), this.hasItem(source.get())).build(consumer, safeId(result.get()) + "_from_blasting");
    }
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void storage(Supplier<? extends T> input, Supplier<? extends T> output, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(output.get()).patternLine("###").patternLine("###").patternLine("###").key('#', input.get()).addCriterion("has_at_least_9_" + safeName(input.get()), this.hasItem(MinMaxBounds.IntBound.atLeast(9), input.get())).build(consumer);
    ShapelessRecipeBuilder.shapelessRecipe(input.get(), 9).addIngredient(output.get()).addCriterion("has_" + safeName(output.get()), this.hasItem(output.get())).build(consumer, safeId(input.get()) + "_from_" + safeName(output.get()));
  }

  protected Item getModElement (Tag<Item> input) {
    Item last = Items.AIR;
    for (Item item : input.getAllElements()) {
      last = item;
      if (item.getRegistryName().getNamespace().equals(modid)) {
        return item;
      }
    }
    return last;
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void storage(Tag<Item> input, Supplier<? extends T> output, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(output.get())
        .patternLine("###")
        .patternLine("###")
        .patternLine("###").key('#', input).addCriterion("has_at_least_9_" + safeName(input.getId()), this.hasItem(input)).build(consumer);
    ShapelessRecipeBuilder.shapelessRecipe(getModElement(input), 9).addIngredient(output.get()).addCriterion("has_" + safeName(output.get()), this.hasItem(output.get())).build(consumer, new ResourceLocation(modid, safeName(input.getId()) + "_from_" + safeName(output.get())));
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> ShapelessRecipeBuilder singleItemUnfinished(Supplier<? extends T> source, Supplier<? extends T> result, int required, int amount) {
    return ShapelessRecipeBuilder.shapelessRecipe(result.get(), amount).addIngredient(source.get(), required).addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()));
  }

  protected ResourceLocation safeId(ResourceLocation id) {
    return new ResourceLocation(id.getNamespace(), safeName(id));
  }

  protected ResourceLocation safeId(IForgeRegistryEntry<?> registryEntry) {
    return safeId(registryEntry.getRegistryName());
  }

  protected String safeName(ResourceLocation nameSource) {
    return nameSource.getPath().replace('/', '_');
  }

  protected String safeName(IForgeRegistryEntry<?> registryEntry) {
    return safeName(registryEntry.getRegistryName());
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void dye(Supplier<? extends T> source, Supplier<? extends T> result, int required, int amount, Consumer<IFinishedRecipe> consumer) {
    singleItemUnfinished(source, result, required, amount).build(consumer, new ResourceLocation(modid, result.get().getRegistryName().getPath()));
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void singleItem(Supplier<? extends T> source, Supplier<? extends T> result, int required, int amount, Consumer<IFinishedRecipe> consumer) {
    singleItemUnfinished(source, result, required, amount).build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void planks(Supplier<? extends T> source, Supplier<? extends T> result, Consumer<IFinishedRecipe> consumer) {
    singleItemUnfinished(source, result, 1, 4)
        .setGroup("planks")
        .build(consumer);
  }

  // TWO BY TWO: Items

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void twoByTwo (Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, int count, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), count)
        .patternLine("XX")
        .patternLine("XX")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void twoByTwo (Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    twoByTwo(source, result, group, 4, consumer);
  }

  // Two by Two using Two items

  protected <T extends IIngredientProvider, V extends IItemProvider & IForgeRegistryEntry<?>> void twoTwoByTwo (T source1, T source2, Supplier<? extends V> result, @Nullable String group, int count, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), count)
        .patternLine("XY")
        .patternLine("YX")
        .key('X', source1.asIngredient())
        .key('Y', source2.asIngredient())
        .setGroup(group)
        .addCriterion("has_" + source1.safeName(), source1.hasItem())
        .addCriterion("has_" + source2.safeName(), source2.hasItem())
        .build(consumer);
  }

  protected <T extends IIngredientProvider, V extends IItemProvider & IForgeRegistryEntry<?>> void twoTwoByTwo (T source1, T source2, Supplier<? extends V> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    twoTwoByTwo(source1, source2, result, group, 4, consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void stairs(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, boolean stone, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 4)
        .patternLine("X  ").patternLine("XX ").patternLine("XXX")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
    if (stone) {
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(source.get()), result.get())
          .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
          .build(consumer, safeId(result.get()) + "_from_" + safeName(source.get()) + "_stonecutting");
    }
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void slab(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, boolean stone, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 6)
        .patternLine("XXX")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
    if (stone) {
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(source.get()), result.get(), 2)
          .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
          .build(consumer, safeId(result.get()) + "_from_" + safeName(source.get()) + "_stonecutting");
    }
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void narrowPost(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, boolean stone, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 4)
        .patternLine("X")
        .patternLine("X")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
    if (stone) {
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(source.get()), result.get(), 2)
          .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
          .build(consumer, safeId(result.get()) + "_from_" + safeName(source.get()) + "_stonecutting");
    }
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void widePost(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, boolean stone, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 6)
        .patternLine("X")
        .patternLine("X")
        .patternLine("X")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
    if (stone) {
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(source.get()), result.get(), 2)
          .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
          .build(consumer, safeId(result.get()) + "_from_" + safeName(source.get()) + "_stonecutting");
    }
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void fence(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 3)
        .patternLine("W#W").patternLine("W#W")
        .key('W', source.get())
        .key('#', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void fenceGate(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get())
        .patternLine("#W#").patternLine("#W#")
        .key('W', source.get())
        .key('#', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void wall(Supplier<? extends T> source, Supplier<? extends T> result, boolean stone, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 6)
        .patternLine("XXX").patternLine("XXX")
        .key('X', source.get())
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
    if (stone) {
      SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(source.get()), result.get())
          .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
          .build(consumer, safeId(result.get()) + "_from_" + safeName(source.get()) + "_stonecutting");
    }
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void door(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 3)
        .patternLine("XX").patternLine("XX").patternLine("XX")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void trapDoor(Supplier<? extends T> source, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 2)
        .patternLine("XXX").patternLine("XXX")
        .key('X', source.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(source.get()), this.hasItem(source.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void axe(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XX ")
        .patternLine("XS ")
        .patternLine(" S ")
        .key('X', material.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void pickaxe(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XXX")
        .patternLine(" S ")
        .patternLine(" S ")
        .key('X', material.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void shovel(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X")
        .patternLine("S")
        .patternLine("S")
        .key('X', material.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void sword(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X")
        .patternLine("X")
        .patternLine("S")
        .key('X', material.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void knife(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine(" X")
        .patternLine("S ")
        .key('X', material.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void knife(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine(" X")
        .patternLine("S ")
        .key('X', material)
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void hoe(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XX ")
        .patternLine(" S ")
        .patternLine(" S ")
        .key('X', material.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void boots(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X X")
        .patternLine("X X")
        .key('X', material.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

   protected <T extends IItemProvider & IForgeRegistryEntry<?>> void spear(Supplier<? extends Item> sword, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X")
        .patternLine("S")
        .patternLine("S")
        .key('X', sword.get())
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(sword.get()), this.hasItem(sword.get()))
        .build(consumer, new ResourceLocation(modid, result.get().getRegistryName().getPath()));
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void legs(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XXX")
        .patternLine("X X")
        .patternLine("X X")
        .key('X', material.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void chest(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X X")
        .patternLine("XXX")
        .patternLine("XXX")
        .key('X', material.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void helmet(Supplier<? extends T> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XXX")
        .patternLine("X X")
        .key('X', material.get())
        .setGroup(group)
        .addCriterion("has_" + safeName(material.get()), this.hasItem(material.get()))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void axe(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XX ")
        .patternLine("XS ")
        .patternLine(" S ")
        .key('X', material)
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void pickaxe(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XXX")
        .patternLine(" S ")
        .patternLine(" S ")
        .key('X', material)
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void shovel(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X")
        .patternLine("S")
        .patternLine("S")
        .key('X', material)
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void sword(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X")
        .patternLine("X")
        .patternLine("S")
        .key('X', material)
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void hoe(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XX ")
        .patternLine(" S ")
        .patternLine(" S ")
        .key('X', material)
        .key('S', Tags.Items.RODS_WOODEN)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void boots(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X X")
        .patternLine("X X")
        .key('X', material)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void legs(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XXX")
        .patternLine("X X")
        .patternLine("X X")
        .key('X', material)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void chest(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("X X")
        .patternLine("XXX")
        .patternLine("XXX")
        .key('X', material)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }

  protected <T extends IItemProvider & IForgeRegistryEntry<?>> void helmet(Tag<Item> material, Supplier<? extends T> result, @Nullable String group, Consumer<IFinishedRecipe> consumer) {
    ShapedRecipeBuilder.shapedRecipe(result.get(), 1)
        .patternLine("XXX")
        .patternLine("X X")
        .key('X', material)
        .setGroup(group)
        .addCriterion("has_" + safeName(material.getId()), this.hasItem(material))
        .build(consumer);
  }
}
