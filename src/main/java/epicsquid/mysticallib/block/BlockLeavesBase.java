package epicsquid.mysticallib.block;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelBlock;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class BlockLeavesBase extends BlockLeaves implements IBlock, IModeledObject, ICustomModeledObject, INoCullBlock {
  private @Nonnull
  Item itemBlock;
  private List<ItemStack> drops;
  private boolean hasCustomModel = false;
  private boolean hasItems = true;
  private boolean noCull = true;
  private boolean isFlammable = false;
  private Supplier<ItemStack> sapling = null;
  private AxisAlignedBB box = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
  private BlockRenderLayer layer = BlockRenderLayer.CUTOUT_MIPPED;
  private int saplingChance;
  public @Nonnull
  String name;

  public BlockLeavesBase(float hardness, @Nonnull String name, Supplier<ItemStack> sapling, int saplingChance) {
    super();
    this.name = name;
    this.sapling = sapling;
    this.saplingChance = saplingChance;
    setTranslationKey(name);
    setRegistryName(LibRegistry.getActiveModid(), name);
    setHardness(hardness);
    itemBlock = new ItemBlock(this).setRegistryName(LibRegistry.getActiveModid(), name);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
  }

  @Nonnull
  public BlockLeavesBase setFlammable(boolean flammable) {
    this.isFlammable = flammable;
    return this;
  }

  @Override
  @Nonnull
  public BlockLeavesBase setResistance(float resistance) {
    super.setResistance(resistance);
    return this;
  }

  @Nonnull
  public BlockLeavesBase setDrops(@Nonnull List<ItemStack> drops) {
    this.drops = drops;
    return this;
  }

  @Override
  protected int getSaplingDropChance(IBlockState state) {
    return saplingChance;
  }

  @Override
  public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    Random rand = world instanceof World ? ((World) world).rand : new Random();
    int chance = getSaplingDropChance(state);

    if (fortune > 0) {
      chance -= 2 << fortune;
      if (chance < 10)
        chance = 10;
    }

    if (rand.nextInt(chance) == 0) {
      if (sapling != null) {
        ItemStack sap = sapling.get();
        if (!sap.isEmpty()) {
          drops.add(sap);
        }
      }
    }
  }

  @Nonnull
  public BlockLeavesBase setBox(@Nonnull AxisAlignedBB box) {
    this.box = box;
    return this;
  }

  @Override
  @Nonnull
  public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    return box;
  }

  @Nonnull
  public BlockLeavesBase setNoCull(boolean noCull) {
    this.noCull = noCull;
    return this;
  }

  @Override
  public boolean noCull() {
    return noCull;
  }

  @Nonnull
  public BlockLeavesBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  @Nonnull
  public BlockLeavesBase setHarvestReqs(@Nonnull String tool, int level) {
    setHarvestLevel(tool, level);
    return this;
  }

  @Nonnull
  public BlockLeavesBase setHasItem(boolean hasItem) {
    this.hasItems = hasItem;
    return this;
  }

  @SideOnly(Side.CLIENT)
  public BlockLeavesBase setLayer(@Nonnull BlockRenderLayer layer) {
    this.layer = layer;
    return this;
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return Blocks.LEAVES.isOpaqueCube(state);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return Blocks.LEAVES.getRenderLayer();
  }

  public boolean hasCustomModel() {
    return hasCustomModel;
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initModel() {
    if (hasCustomModel) {
      ModelLoader.setCustomStateMapper(this, new CustomStateMapper());
    }
    if (!hasCustomModel) {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "handlers"));
      ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(CHECK_DECAY).ignore(DECAYABLE).build());
    } else {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "handlers"));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(getRegistryName().getNamespace() + ":blocks/" + getRegistryName().getPath());
      if (getParentState() != null) {
        defaultTex = new ResourceLocation(
            getParentState().getBlock().getRegistryName().getNamespace() + ":blocks/" + getParentState().getBlock().getRegistryName().getPath());
      }
      CustomModelLoader.blockmodels
          .put(new ResourceLocation(getRegistryName().getNamespace() + ":models/block/" + name), new CustomModelBlock(getModelClass(), defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getNamespace() + ":" + name + "#handlers"),
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
  public BlockPlanks.EnumType getWoodType(int meta) {
    return null;
  }

  @Override
  public Item getItemBlock() {
    return itemBlock;
  }

  @Override
  public ItemBlock setItemBlock(ItemBlock block) {
    this.itemBlock = block;
    return block;
  }

  @Nullable
  protected IBlockState getParentState() {
    return null;
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    if (drops != null && drops.size() > 0) {
      return drops.get(0).getItem();
    }
    return super.getItemDropped(state, rand, fortune);
  }

  @Override
  public boolean isFlammable(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
    return isFlammable || super.isFlammable(world, pos, face);
  }

  @Override
  public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return isFlammable ? 100 : super.getFlammability(world, pos, face);
  }

  @Nonnull
  protected Class<? extends BakedModelBlock> getModelClass() {
    return getModelClass(0);
  }

  @Nonnull
  protected Class<? extends BakedModelBlock> getModelClass(int type) {
    return BakedModelBlock.class;
  }

  @Nonnull
  @Override
  public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return NonNullList.withSize(1, new ItemStack(this, 1));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    int i = 0;

    if (!state.getValue(DECAYABLE)) {
      i |= 4;
    }

    if (state.getValue(CHECK_DECAY)) {
      i |= 8;
    }

    return i;
  }
}
