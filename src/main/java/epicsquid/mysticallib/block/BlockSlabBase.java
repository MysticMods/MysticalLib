package epicsquid.mysticallib.block;

import java.util.List;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.item.ItemBlockSlab;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelCube;
import epicsquid.mysticallib.model.block.BakedModelSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSlabBase extends BlockSlab implements IBlock, IModeledObject, ICustomModeledObject {
  public static BlockSlabBase dummy = new BlockSlabBase(Material.AIR, SoundType.SNOW, 0f, "null", Blocks.AIR.getDefaultState(), false, Blocks.AIR);
  Item itemBlock = null;
  public List<ItemStack> drops = null;
  boolean isOpaque = false;
  protected boolean hasCustomModel = false;
  BlockRenderLayer layer = BlockRenderLayer.SOLID;
  protected boolean isDouble;
  protected IBlockState parent;
  public String name = "";
  public Block slab = null;

  public BlockSlabBase(Material mat, SoundType type, float hardness, String name, IBlockState parent, boolean isDouble, Block slab) {
    super(mat);
    this.isDouble = isDouble;
    setUnlocalizedName(name);
    setRegistryName(name);
    setSoundType(type);
    setHardness(hardness);
    setOpacity(false);
    this.fullBlock = false;
    this.parent = parent;
    this.slab = slab;
    if (!isDouble) {
      itemBlock = new ItemBlockSlab(this, slab).setRegistryName(LibRegistry.getActiveModid(), name);
    }
  }

  @Override
  public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (tab == this.getCreativeTabToDisplayOn() && !this.isDouble) {
      items.add(new ItemStack(this, 1));
    }
  }

  @Override
  public Block setCreativeTab(CreativeTabs tab) {
    if (!isDouble) {
      itemBlock.setCreativeTab(tab);
    }
    return super.setCreativeTab(tab);
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    if (isDouble) {
      drops.add(new ItemStack(Item.getItemFromBlock(this.slab), 1));
      drops.add(new ItemStack(Item.getItemFromBlock(this.slab), 1));
    } else {
      super.getDrops(drops, world, pos, state, fortune);
    }
  }

  public BlockSlabBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  public BlockSlabBase setHarvestReqs(String tool, int level) {
    setHarvestLevel(tool, level);
    return this;
  }

  public BlockSlabBase setOpacity(boolean isOpaque) {
    this.isOpaque = isOpaque;
    return this;
  }

  public BlockSlabBase setLayer(BlockRenderLayer layer) {
    this.layer = layer;
    return this;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return isOpaque;
  }

  public boolean hasCustomModel() {
    return this.hasCustomModel;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initModel() {
    if (this.hasCustomModel) {
      ModelLoader.setCustomStateMapper(this, new CustomStateMapper());
    }
    if (!this.hasCustomModel) {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    } else {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (this.hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(
          parent.getBlock().getRegistryName().getResourceDomain() + ":blocks/" + parent.getBlock().getRegistryName().getResourcePath());
      if (isDouble) {
        CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + getRegistryName().getResourcePath()),
            new CustomModelBlock(BakedModelCube.class, defaultTex, defaultTex));
        CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + getRegistryName().getResourcePath() + "#inventory"),
            new CustomModelBlock(BakedModelCube.class, defaultTex, defaultTex));
      } else {
        CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + getRegistryName().getResourcePath()),
            new CustomModelBlock(BakedModelSlab.class, defaultTex, defaultTex));
        CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + getRegistryName().getResourcePath() + "#inventory"),
            new CustomModelBlock(BakedModelSlab.class, defaultTex, defaultTex));
      }
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {
    return this.layer;
  }

  @Override
  public Item getItemBlock() {
    return itemBlock;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    IBlockState iblockstate = this.getDefaultState();
    if (!this.isDouble())
      iblockstate = iblockstate.withProperty(HALF, (meta) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);

    return iblockstate;
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HALF);
  }

  @Override
  public String getUnlocalizedName(int meta) {
    return super.getUnlocalizedName();
  }

  @Override
  public boolean isDouble() {
    return isDouble;
  }

  @Override
  public IProperty<?> getVariantProperty() {
    return HALF;
  }

  @Override
  public Comparable<?> getTypeForItem(ItemStack stack) {
    return 0;
  }
}
