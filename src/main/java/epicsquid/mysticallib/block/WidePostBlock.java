package epicsquid.mysticallib.block;

import epicsquid.mysticallib.util.VoxelUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WidePostBlock extends Block {
  public static VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);

  public WidePostBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }
}
