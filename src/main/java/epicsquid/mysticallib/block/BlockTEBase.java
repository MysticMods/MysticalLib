package epicsquid.mysticallib.block;

import java.util.HashSet;
import java.util.Set;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.tile.ITile;
import epicsquid.mysticallib.util.Util;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTEBase extends BlockBase implements ITileEntityProvider {
  protected Class<? extends TileEntity> teClass;
  static Set<Class<? extends TileEntity>> classes = new HashSet<>();

  public BlockTEBase(Material mat, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name);
    this.teClass = teClass;
    attemptRegistry(teClass);
  }

  public static void attemptRegistry(Class<? extends TileEntity> c) {
    if (!classes.contains(c)) {
      String[] nameParts = c.getTypeName().split("\\.");
      String className = nameParts[nameParts.length - 1];
      String modid = LibRegistry.getActiveModid();
      GameRegistry.registerTileEntity(c, modid + ":" + Util.lowercase(className));
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY,
      float hitZ) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      return ((ITile) t).activate(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }
    return false;
  }

  @Override
  public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      ((ITile) t).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public void onBlockExploded(World world, BlockPos pos, Explosion e) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      ((ITile) t).breakBlock(world, pos, world.getBlockState(pos), null);
    }
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    try {
      return teClass.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

}
