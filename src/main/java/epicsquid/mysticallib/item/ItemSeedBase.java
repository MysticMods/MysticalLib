package epicsquid.mysticallib.item;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelItem;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemSeedBase extends ItemSeeds implements IModeledObject, ICustomModeledObject {

  private boolean hasCustomModel;

  /**
   * Creates a generic seed item with a given farmland and crop block
   * @param name Name of the seed item
   * @param crop Crop block to plant with the seed
   * @param base Block to grow the crop on
   */
  public ItemSeedBase(@Nonnull String name, @Nonnull Block crop, @Nonnull Block base) {
    super(crop, base);
    setUnlocalizedName(name);
    setRegistryName(LibRegistry.getActiveModid(), name);
  }

  /**
   * If set to true, the item won't need a model file defined
   */
  public ItemSeedBase setModelCustom(boolean hasCustomModel) {
    this.hasCustomModel = hasCustomModel;
    return this;
  }

  @Override
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "handlers"));
  }

  @Override
  public void initCustomModel() {
    if (this.hasCustomModel) {
      CustomModelLoader.itemmodels.put(getRegistryName(),
          new CustomModelItem(false, new ResourceLocation(getRegistryName().getResourceDomain() + ":items/" + getRegistryName().getResourcePath())));
    }
  }
}
