package epicsquid.mysticallib.block;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.tile.ITile;
import epicsquid.mysticallib.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("deprecation")
public class BlockTETrapDoorBase extends BlockTrapDoorBase implements ITileEntityProvider {
  protected Class<? extends TileEntity> teClass;
  public static Set<Class<? extends TileEntity>> classes = new HashSet<>();

  public BlockTETrapDoorBase(@Nonnull Block block, @Nonnull SoundType type, float hardness, @Nonnull String name,
      @Nonnull Class<? extends TileEntity> teClass) {
    super(block, type, hardness, name);
    this.teClass = teClass;
    attemptRegistry(teClass);
  }

  public static void attemptRegistry(@Nonnull Class<? extends TileEntity> c) {
    if (!classes.contains(c)) {
      String[] nameParts = c.getTypeName().split("\\.");
      String className = nameParts[nameParts.length - 1];
      String modid = LibRegistry.getActiveModid();
      GameRegistry.registerTileEntity(c, modid + ":" + Util.lowercase(className));
    }
  }

  @Override
  public boolean onBlockActivated(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand,
      @Nonnull EnumFacing face, float hitX, float hitY, float hitZ) {
    if ((player.getHeldItemMainhand().isEmpty() || Block.getBlockFromItem(player.getHeldItemMainhand().getItem()) == Blocks.AIR) && !player.isSneaking()) {
      return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      return ((ITile) t).activate(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }
    return false;
  }
  
  @Override
  public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
    if (state.getValue(OPEN)) {
      IBlockState down = world.getBlockState(pos.down());
      if (down.getBlock() instanceof BlockLadder) {
        return down.getValue(BlockLadder.FACING) == state.getValue(FACING);
      }
    }
    return false;
  }

  @Override
  public void onBlockHarvested(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      ((ITile) t).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public void onBlockExploded(@Nonnull World world, @Nonnull BlockPos pos, Explosion e) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      ((ITile) t).breakBlock(world, pos, world.getBlockState(pos), null);
    }
    super.onBlockExploded(world, pos, e);
  }

  @Override
  @Nullable
  public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
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
