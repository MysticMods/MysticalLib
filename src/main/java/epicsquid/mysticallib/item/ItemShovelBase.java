package epicsquid.mysticallib.item;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelItem;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemShovelBase extends ItemSpade implements IModeledObject, ICustomModeledObject {

  private boolean hasCustomModel = false;
  private final int enchantability;

  public ItemShovelBase(ToolMaterial material, String name, int toolLevel, int maxDamage, int enchantability) {
    super(material);
    setTranslationKey(name);
    setRegistryName(LibRegistry.getActiveModid(), name);
    setHarvestLevel("shovel", toolLevel);
    setMaxDamage(maxDamage);
    this.enchantability = enchantability;
  }

  @Override
  public int getItemEnchantability() {
    return enchantability;
  }

  public ItemShovelBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
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
          new CustomModelItem(false, new ResourceLocation(getRegistryName().getNamespace() + ":items/" + getRegistryName().getPath())));
    }
  }
}
