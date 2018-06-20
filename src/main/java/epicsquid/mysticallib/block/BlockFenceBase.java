package epicsquid.mysticallib.block;

import java.util.List;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFenceBase extends BlockFence implements IBlock, IModeledObject, ICustomModeledObject {
  Item itemBlock = null;
  public List<ItemStack> drops = null;
  boolean isOpaque = false;
  protected boolean hasCustomModel = false;
  BlockRenderLayer layer = BlockRenderLayer.SOLID;
  protected Block parent;
  public String name = "";

  public BlockFenceBase(Block base, SoundType type, float hardness, String name) {
    super(Material.WOOD, MapColor.AIR);
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

  public BlockFenceBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  public BlockFenceBase setHarvestReqs(String tool, int level) {
    setHarvestLevel(tool, level);
    return this;
  }

  public BlockFenceBase setOpacity(boolean isOpaque) {
    this.isOpaque = isOpaque;
    return this;
  }

  public BlockFenceBase setLayer(BlockRenderLayer layer) {
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
  public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @Override
  public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
    if (tab == this.getCreativeTabToDisplayOn()) {
      list.add(new ItemStack(this, 1));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
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
}
