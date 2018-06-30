package epicsquid.mysticallib.block;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBase extends Block implements IBlock, IModeledObject, ICustomModeledObject, INoCullBlock {
  private final @Nonnull Item itemBlock;
  public @Nonnull List<ItemStack> drops;
  protected boolean isOpaque = true;
  protected boolean hasCustomModel = false;
  protected boolean hasItems = true;
  protected boolean noCull = false;
  protected AxisAlignedBB box = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
  private BlockRenderLayer layer = BlockRenderLayer.SOLID;
  public @Nonnull String name;

  public BlockBase(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(mat);
    this.name = name;
    setUnlocalizedName(name);
    setRegistryName(LibRegistry.getActiveModid(), name);
    setSoundType(type);
    setLightOpacity(15);
    setHardness(hardness);
    itemBlock = new ItemBlock(this).setRegistryName(LibRegistry.getActiveModid(), name);
  }

  @Nonnull
  public BlockBase setBox(@Nonnull AxisAlignedBB box) {
    this.box = box;
    return this;
  }

  @Override
  @Nonnull
  public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    return box;
  }

  @Nonnull
  public BlockBase setNoCull(boolean noCull) {
    this.noCull = noCull;
    return this;
  }

  @Override
  public boolean noCull() {
    return noCull;
  }

  @Nonnull
  public BlockBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  @Nonnull
  public BlockBase setHarvestReqs(@Nonnull String tool, int level) {
    setHarvestLevel(tool, level);
    return this;
  }

  @Nonnull
  public BlockBase setOpacity(boolean isOpaque) {
    this.isOpaque = isOpaque;
    return this;
  }

  @Nonnull
  public BlockBase setHasItem(boolean hasItem) {
    this.hasItems = hasItem;
    return this;
  }

  @SideOnly(Side.CLIENT)
  public BlockBase setLayer(@Nonnull BlockRenderLayer layer) {
    this.layer = layer;
    return this;
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return isOpaque;
  }

  public boolean hasCustomModel() {
    return hasCustomModel;
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return isOpaque;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initModel() {
    if (hasCustomModel) {
      ModelLoader.setCustomStateMapper(this, new CustomStateMapper());
    }
    if (!hasCustomModel) {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    } else {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(getRegistryName().getResourceDomain() + ":blocks/" + getRegistryName().getResourcePath());
      if (getParentState() != null) {
        defaultTex = new ResourceLocation(
            getParentState().getBlock().getRegistryName().getResourceDomain() + ":blocks/" + getParentState().getBlock().getRegistryName().getResourcePath());
      }
      CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + name),
          new CustomModelBlock(getModelClass(), defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + name + "#inventory"),
          new CustomModelBlock(getModelClass(), defaultTex, defaultTex));
    }
  }

  @Override
  public void getSubBlocks(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
    if (hasItems) {
      super.getSubBlocks(tab, items);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  @Nonnull
  public BlockRenderLayer getBlockLayer() {
    return this.layer;
  }

  @Override
  public Item getItemBlock() {
    return itemBlock;
  }

  @Nullable
  protected IBlockState getParentState() {
    return null;
  }

  @Nonnull
  protected Class<? extends BakedModelBlock> getModelClass() {
    return BakedModelBlock.class;
  }
}
