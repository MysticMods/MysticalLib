package epicsquid.mysticallib.block.multiblock;

import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IMultiblock {

  @Nonnull
  Map<BlockPos, IBlockState> getSlavePositions(@Nonnull BlockPos pos, @Nonnull EnumFacing face);
}
