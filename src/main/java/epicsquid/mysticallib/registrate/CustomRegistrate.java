package epicsquid.mysticallib.registrate;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import epicsquid.mysticallib.block.NarrowPostBlock;
import epicsquid.mysticallib.block.WidePostBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

public class CustomRegistrate extends AbstractRegistrate<CustomRegistrate> {
  protected CustomRegistrate(String modid) {
    super(modid);
  }

  public static CustomRegistrate create(String modid) {
    return new CustomRegistrate(modid).registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus());
  }

  public <T extends IRecipeSerializer<?>> RecipeSerializerBuilder<T, CustomRegistrate> recipeSerializer(Supplier<? extends T> factory) {
    return recipeSerializer(this, factory);
  }

  public <T extends IRecipeSerializer<?>, P> RecipeSerializerBuilder<T, P> recipeSerializer(P parent, Supplier<? extends T> factory) {
    return recipeSerializer(parent, currentName(), factory);
  }

  public <T extends IRecipeSerializer<?>, P> RecipeSerializerBuilder<T, P> recipeSerializer(P parent, String name, Supplier<? extends T> factory) {
    return entry(name, callback -> new RecipeSerializerBuilder<>(this, parent, name, callback, factory));
  }

  public <T extends Container> ContainerBuilder<T, CustomRegistrate> containerType(ContainerType.IFactory<T> factory) {
    return containerType(this, factory);
  }

  public <T extends Container, P> ContainerBuilder<T, P> containerType(P parent, ContainerType.IFactory<T> factory) {
    return containerType(parent, currentName(), factory);
  }

  public <T extends Container, P> ContainerBuilder<T, P> containerType(P parent, String name, ContainerType.IFactory<T> factory) {
    return entry(name, callback -> new ContainerBuilder<>(this, parent, name, callback, factory));
  }

  private <T extends Block> Supplier<Material> blockMaterial(RegistryEntry<T> block) {
    return () -> block.lazyMap(Block::getDefaultState).get().getMaterial();
  }

  @SuppressWarnings("unchecked")
  private <T extends StairsBlock, V extends Block> NonNullFunction<Block.Properties, T> stairFactory (RegistryEntry<V> parent) {
    return properties -> (T) new StairsBlock(parent.lazyMap(Block::getDefaultState), Block.Properties.from(parent.get()));
  }

/*  public <T extends StairsBlock, V extends Block> BlockBuilder<T, CustomRegistrate> stairs(RegistryEntry<V> parent) {
    return this.stairs(this.self(), this.currentName(), stairFactory(parent), blockMaterial(parent));
  }*/

  public <T extends StairsBlock, V extends Block> BlockBuilder<T, CustomRegistrate> stairs(String name, RegistryEntry<V> parent) {
    return this.stairs(this.self(), name, stairFactory(parent), blockMaterial(parent));
  }

  public <T extends StairsBlock> BlockBuilder<T, CustomRegistrate> stairs(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, Supplier<Material> material) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, factory, material.get()));
  }

/*  public <V extends Block> BlockBuilder<SlabBlock, CustomRegistrate> slab(RegistryEntry<V> parent) {
    return this.slab(this.self(), this.currentName(), SlabBlock::new, parent);
  }*/

  public <V extends Block> BlockBuilder<SlabBlock, CustomRegistrate> slab(String name, RegistryEntry<V> parent) {
    return this.slab(this.self(), name, SlabBlock::new, parent);
  }

  public <T extends SlabBlock, V extends Block> BlockBuilder<T, CustomRegistrate> slab(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, RegistryEntry<V> parentBlock) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, (o) -> factory.apply(Block.Properties.from(parentBlock.get())), blockMaterial(parentBlock).get()));
  }

  public <V extends Block> BlockBuilder<WallBlock, CustomRegistrate> wall(String name, RegistryEntry<V> parent) {
    return this.wall(this.self(), name, WallBlock::new, parent);
  }

  public <T extends WallBlock, V extends Block> BlockBuilder<T, CustomRegistrate> wall(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, RegistryEntry<V> parentBlock) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, (o) -> factory.apply(Block.Properties.from(parentBlock.get())), blockMaterial(parentBlock).get()));
  }

  public <V extends Block> BlockBuilder<FenceBlock, CustomRegistrate> fence(String name, RegistryEntry<V> parent) {
    return this.fence(this.self(), name, FenceBlock::new, parent);
  }

  public <T extends FenceBlock, V extends Block> BlockBuilder<T, CustomRegistrate> fence(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, RegistryEntry<V> parentBlock) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, (o) -> factory.apply(Block.Properties.from(parentBlock.get())), blockMaterial(parentBlock).get()));
  }

  public <V extends Block> BlockBuilder<FenceGateBlock, CustomRegistrate> gate(String name, RegistryEntry<V> parent) {
    return this.gate(this.self(), name, FenceGateBlock::new, parent);
  }

  public <T extends FenceGateBlock, V extends Block> BlockBuilder<T, CustomRegistrate> gate(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, RegistryEntry<V> parentBlock) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, (o) -> factory.apply(Block.Properties.from(parentBlock.get())), blockMaterial(parentBlock).get()));
  }

  public <V extends Block> BlockBuilder<WidePostBlock, CustomRegistrate> widePost(String name, RegistryEntry<V> parent) {
    return this.widePost(this.self(), name, WidePostBlock::new, parent);
  }

  public <T extends WidePostBlock, V extends Block> BlockBuilder<T, CustomRegistrate> widePost(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, RegistryEntry<V> parentBlock) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, (o) -> factory.apply(Block.Properties.from(parentBlock.get())), blockMaterial(parentBlock).get()));
  }

  public <V extends Block> BlockBuilder<NarrowPostBlock, CustomRegistrate> narrowPost(String name, RegistryEntry<V> parent) {
    return this.narrowPost(this.self(), name, NarrowPostBlock::new, parent);
  }

  public <T extends NarrowPostBlock, V extends Block> BlockBuilder<T, CustomRegistrate> narrowPost(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, RegistryEntry<V> parentBlock) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, (o) -> factory.apply(Block.Properties.from(parentBlock.get())), blockMaterial(parentBlock).get()));
  }

  public BlockBuilder<LogBlock, CustomRegistrate> log(String name, MaterialColor color) {
    return this.log(this.self(), name, (b) -> new LogBlock(color, b), Material.WOOD);
  }

  public <T extends LogBlock> BlockBuilder<T, CustomRegistrate> log(CustomRegistrate parent, String name, NonNullFunction<Block.Properties, T> factory, Material material) {
    return this.entry(name, (callback) -> BlockBuilder.create(this, parent, name, callback, factory, material));
  }


}
