package epicsquid.mysticallib.tile;

import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public interface IDelayedTileRenderer {

  void renderLater(@Nonnull TileEntity tile, double x, double y, double z, float partialTicks);
}
