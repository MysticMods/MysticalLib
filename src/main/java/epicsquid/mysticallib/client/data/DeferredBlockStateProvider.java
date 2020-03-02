package epicsquid.mysticallib.client.data;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.block.NarrowPostBlock;
import epicsquid.mysticallib.block.WidePostBlock;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class DeferredBlockStateProvider extends BlockStateProvider {
  private final String name;

  public DeferredBlockStateProvider(String name, DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
    super(gen, modid, exFileHelper);
    this.name = name;
  }

  @Nonnull
  @Override
  public String getName() {
    return name;
  }

  // Non-override helper functions
  // Taken from Tropicraft
  protected String name(Supplier<? extends Block> block) {
    return block.get().getRegistryName().getPath();
  }

  protected ResourceLocation blockModel(Supplier<? extends Block> block) {
    return blockTexture(block);
  }

  protected ResourceLocation blockTexture(Supplier<? extends Block> block) {
    ResourceLocation base = block.get().getRegistryName();
    return new ResourceLocation(base.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + base.getPath());
  }

  protected ResourceLocation libLod (String texture) {
    return new ResourceLocation(MysticalLib.MODID, texture);
  }

  protected ResourceLocation libBlock (String texture) {
    return new ResourceLocation(MysticalLib.MODID, ModelProvider.BLOCK_FOLDER + "/" + texture);
  }

  protected ResourceLocation blockLocation(String texture) {
    return modLocation(ModelProvider.BLOCK_FOLDER + "/" + texture);
  }


  // Helper functions for deferred blocks
  // Pseudo-overrides
  protected void simpleBlock(Supplier<? extends Block> block) {
    super.simpleBlock(block.get());
  }

  protected void simpleBlock(Supplier<? extends Block> block, Function<ModelFile, ConfiguredModel[]> expander) {
    super.simpleBlock(block.get(), expander);
  }

  protected void simpleBlock(Supplier<? extends Block> block, ModelFile model) {
    super.simpleBlock(block.get(), model);
  }

  protected void simpleBlock(Supplier<? extends Block> block, ConfiguredModel... models) {
    super.simpleBlock(block.get(), models);
  }

  protected void simpleModel(Supplier<? extends Block> block) {
    simpleBlock(block, getExistingFile(blockModel(block)));
  }

  protected void horizontalBlock(Supplier<? extends Block> block, ModelFile model) {
    horizontalBlock(block.get(), model);
  }

  protected void horizontalModel(Supplier<? extends Block> block) {
    horizontalBlock(block, getExistingFile(blockModel(block)));
  }

  protected final Function<BlockState, String> booleanStateDescriptor(BooleanProperty prop) {
    return (BlockState state) -> state.get(prop) ? "on" : "off";
  }

  protected final Function<Object[], ResourceLocation> booleanStateLoc(String base) {
    return (Object[] args) -> new ResourceLocation(modid, String.format(base, args));
  }

  @SafeVarargs
  protected final void horizontalBooleanStateBlock(Supplier<? extends Block> block, Function<Object[], ResourceLocation> rlSupplier, Function<BlockState, String>... descriptors) {
    horizontalBlock(block.get(), (state) ->
        getExistingFile(rlSupplier.apply(Stream.of(descriptors).map(o -> o.apply(state)).toArray(String[]::new))));
  }

  // Methods for stairs, etc, taken from Tropicraft
  protected void stairsBlock(Supplier<? extends StairsBlock> block, String name) {
    stairsBlock(block, name, name);
  }

  protected void stairsBlock(Supplier<? extends StairsBlock> block, String side, String topBottom) {
    stairsBlock(block, side, topBottom, topBottom);
  }

  protected void stairsBlock(Supplier<? extends StairsBlock> block, String side, String top, String bottom) {
    stairsBlock(block.get(), blockLocation(side), blockLocation(top), blockLocation(bottom));
  }

  protected void slabBlock(Supplier<? extends SlabBlock> block, Supplier<? extends Block> doubleBlock) {
    slabBlock(block, doubleBlock, name(doubleBlock));
  }

  protected void slabBlock(Supplier<? extends SlabBlock> block, Supplier<? extends Block> doubleBlock, String texture) {
    slabBlock(block, doubleBlock, texture, texture);
  }

  protected void slabBlock(Supplier<? extends SlabBlock> block, Supplier<? extends Block> doubleBlock, String side, String end) {
    slabBlock(block.get(), doubleBlock.get().getRegistryName(), blockLocation(side), blockLocation(end), blockLocation(end));
  }

  protected void fenceBlock(Supplier<? extends FenceBlock> block, String texture) {
    fenceBlock(block.get(), blockLocation(texture));
    fenceInventory(name(block) + "_inventory", blockLocation(texture));
  }

  protected void fenceGateBlock(Supplier<? extends FenceGateBlock> block, String texture) {
    fenceGateBlock(block.get(), blockLocation(texture));
  }

  protected void wallBlock(Supplier<? extends WallBlock> block, String texture) {
    wallBlock(block.get(), blockLocation(texture));
    wallInventory(name(block) + "_inventory", blockLocation(texture));
  }

  protected void widePostBlock(Supplier<? extends WidePostBlock> block, String texture) {
    getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(getVar(name(block)).parent(getExistingFile(libBlock("wide_post"))).texture("wall", blockLocation(texture))));
  }

  protected void narrowPostBlock(Supplier<? extends NarrowPostBlock> block, String texture) {
    getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(getMultipartBuilder(getName(block)).parent(getExistingFile(libBlock("narrow_post"))).texture("wall", blockLocation(texture))));
  }

  protected void doorBlock(Supplier<? extends DoorBlock> block) {
    doorBlock(block.get(), blockLocation(name(block) + "_bottom"), blockLocation(name(block) + "_top"));
  }

  protected void trapDoorBlock(Supplier<? extends TrapDoorBlock> block) {
    trapdoorBlock(block.get(), blockTexture(block), true);
  }

  protected void logBlock(Supplier<? extends LogBlock> block) {
    logBlock(block.get());
  }
}
