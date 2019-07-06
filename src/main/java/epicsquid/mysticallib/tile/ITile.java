package epicsquid.mysticallib.tile;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITile {

  void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull EntityPlayer player);

  boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand,
      @Nonnull EnumFacing face, float hitX, float hitY, float hitZ);

}
