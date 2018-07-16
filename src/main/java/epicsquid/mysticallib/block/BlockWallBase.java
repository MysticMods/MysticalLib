package epicsquid.mysticallib.block;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelWall;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWallBase extends BlockWall implements IBlock, IModeledObject, ICustomModeledObject {
  private final Item itemBlock;
  public List<ItemStack> drops = null;
  private boolean isOpaque = false;
  protected boolean hasCustomModel = false;
  private BlockRenderLayer layer = BlockRenderLayer.SOLID;
  protected Block parent;
  public String name = "";

  public BlockWallBase(@Nonnull Block base, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(base);
    this.setCreativeTab(null);
    this.parent = base;
    this.name = name;
    setUnlocalizedName(name);
    setRegistryName(name);
    setSoundType(type);
    setLightOpacity(0);
    setHardness(hardness);
    setOpacity(false);
    this.fullBlock = false;
    itemBlock = new ItemBlock(this).setRegistryName(LibRegistry.getActiveModid(), name);
  }

  @Nonnull
  public BlockWallBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  @Nonnull
  public BlockWallBase setHarvestReqs(@Nonnull String tool, int level) {
    setHarvestLevel(tool, level);
    return this;
  }

  @Nonnull
  public BlockWallBase setOpacity(boolean isOpaque) {
    this.isOpaque = isOpaque;
    return this;
  }

  @Nonnull
  public BlockWallBase setLayer(@Nonnull BlockRenderLayer layer) {
    this.layer = layer;
    return this;
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return isOpaque;
  }

  public boolean hasCustomModel() {
    return this.hasCustomModel;
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initModel() {
    if (this.hasCustomModel) {
      ModelLoader.setCustomStateMapper(this, new CustomStateMapper());
    }
    if (!this.hasCustomModel) {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "handlers"));
    } else {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "handlers"));
    }
  }

  @Override
  public boolean canPlaceTorchOnTop(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    return true;
  }

  @Override
  public void getSubBlocks(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
    if (tab == this.getCreativeTabToDisplayOn()) {
      list.add(new ItemStack(this, 1));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (this.hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(
          parent.getRegistryName().getResourceDomain() + ":blocks/" + parent.getRegistryName().getResourcePath());
      CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + name),
          new CustomModelBlock(BakedModelWall.class, defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + name + "#handlers"),
          new CustomModelBlock(BakedModelWall.class, defaultTex, defaultTex));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  @Nonnull
  public BlockRenderLayer getBlockLayer() {
    return this.layer;
  }

  @Override
  @Nonnull
  public Item getItemBlock() {
    return itemBlock;
  }
}
