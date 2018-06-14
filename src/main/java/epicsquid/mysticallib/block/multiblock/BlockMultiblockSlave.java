package epicsquid.mysticallib.block.multiblock;

import epicsquid.mysticallib.block.BlockTEBase;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.block.BakedModelBlock;
import epicsquid.mysticallib.tile.multiblock.ISlave;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiblockSlave extends BlockTEBase {
  public static final PropertyBool SHADOW = PropertyBool.create("shadow");

  public BlockMultiblockSlave(Material mat, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    setModelCustom(true);
    setHasItem(false);
    setOpacity(false);
    setLightOpacity(0);
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return state.getValue(SHADOW);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, SHADOW);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(SHADOW, meta == 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(SHADOW) ? 1 : 0;
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    TileEntity tile = world.getTileEntity(pos);
    if (tile instanceof ISlave) {
      BlockPos p = ((ISlave) tile).getMaster();
      IBlockState master = world.getBlockState(p);
      return master.getBlock().getPickBlock(master, target, world, pos, player);
    }
    return ItemStack.EMPTY;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (this.hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(getRegistryName().getResourceDomain() + ":blocks/null");
      CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + name),
          new CustomModelBlock(BakedModelBlock.class, defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + name + "#inventory"),
          new CustomModelBlock(BakedModelBlock.class, defaultTex, defaultTex));
    }
  }
}
