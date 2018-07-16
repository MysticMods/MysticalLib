package epicsquid.mysticallib.block;

import java.util.List;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelStairs;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStairsBase extends BlockStairs implements IBlock, IModeledObject, ICustomModeledObject {
  private final @Nonnull Item itemBlock;
  public List<ItemStack> drops = null;
  private boolean isOpaque = false;
  protected boolean hasCustomModel = false;
  private BlockRenderLayer layer = BlockRenderLayer.SOLID;
  protected IBlockState parent;
  public String name = "";

  public BlockStairsBase(@Nonnull IBlockState base, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(base);
    this.setCreativeTab(null);
    this.parent = base;
    this.name = name;
    setUnlocalizedName(name);
    setRegistryName(name);
    setSoundType(type);
    setHardness(hardness);
    setOpacity(false);
    this.fullBlock = false;
    itemBlock = new ItemBlock(this).setRegistryName(LibRegistry.getActiveModid(), name);
  }

  @Nonnull
  public BlockStairsBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  @Nonnull
  public BlockStairsBase setHarvestReqs(@Nonnull String tool, int level) {
    setHarvestLevel(tool, level);
    return this;
  }

  @Nonnull
  public BlockStairsBase setOpacity(boolean isOpaque) {
    this.isOpaque = isOpaque;
    return this;
  }

  @Nonnull
  public BlockStairsBase setLayer(@Nonnull BlockRenderLayer layer) {
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
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (this.hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(
          parent.getBlock().getRegistryName().getResourceDomain() + ":blocks/" + parent.getBlock().getRegistryName().getResourcePath());
      CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + name),
          new CustomModelBlock(BakedModelStairs.class, defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + name + "#handlers"),
          new CustomModelBlock(BakedModelStairs.class, defaultTex, defaultTex));
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
