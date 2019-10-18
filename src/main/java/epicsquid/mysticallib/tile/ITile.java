package epicsquid.mysticallib.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface ITile {

  void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player);

  boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player, @Nonnull Hand hand,
                   @Nonnull Direction face, float hitX, float hitY, float hitZ);

}
