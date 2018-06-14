package epicsquid.mysticallib.block.multiblock;

import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IMultiblock {
  public Map<BlockPos, IBlockState> getSlavePositions(BlockPos pos, EnumFacing face);
}
