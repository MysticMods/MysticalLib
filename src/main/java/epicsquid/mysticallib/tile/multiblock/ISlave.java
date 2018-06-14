package epicsquid.mysticallib.tile.multiblock;

import epicsquid.mysticallib.tile.ITile;
import net.minecraft.util.math.BlockPos;

public interface ISlave extends ITile {
  public void setMaster(BlockPos tile);

  public BlockPos getMaster();
}
