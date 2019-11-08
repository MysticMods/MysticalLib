package epicsquid.mysticallib.client.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

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
  protected String name (Supplier<? extends Block> block) {
    return block.get().getRegistryName().getPath();
  }

  protected ResourceLocation blockTexture (Supplier<? extends Block> block) {
    ResourceLocation base = block.get().getRegistryName();
    return new ResourceLocation(base.getNamespace(), folder + "/" + base.getPath());
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
}
