package epicsquid.mysticallib.item;

import java.util.Set;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelItem;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemToolBase extends ItemTool implements IModeledObject, ICustomModeledObject {

  private boolean hasCustomModel = false;

  protected ItemToolBase(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
    super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
    setTranslationKey(name);
    setRegistryName(LibRegistry.getActiveModid(), name);
  }

  public ItemToolBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  @Override
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "handlers"));
  }

  public void addEffectiveBlock (Block block) {
    this.effectiveBlocks.add(block);
  }

  @Override
  public void initCustomModel() {
    if (this.hasCustomModel) {
      CustomModelLoader.itemmodels
          .put(getRegistryName(), new CustomModelItem(false, new ResourceLocation(getRegistryName().getNamespace() + ":items/" + getRegistryName().getPath())));
    }
  }

  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    return !ItemUtil.equalWithoutDamage(oldStack, newStack);
  }

  @Override
  public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
    return !ItemUtil.equalWithoutDamage(oldStack, newStack);
  }
}
