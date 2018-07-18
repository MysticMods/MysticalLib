package epicsquid.mysticallib.gui;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiFactory {

  @Nonnull
  default Container constructContainer(@Nonnull EntityPlayer player, @Nonnull World world, int x, int y, int z) {
    return constructContainer(player, world.getTileEntity(new BlockPos(x, y, z)));
  }

  @SideOnly(Side.CLIENT)
  @Nonnull
  default Gui constructGui(@Nonnull EntityPlayer player, @Nonnull World world, int x, int y, int z) {
    return constructGui(player, world.getTileEntity(new BlockPos(x, y, z)));
  }

  @Nonnull
  Container constructContainer(@Nonnull EntityPlayer player, @Nonnull TileEntity tile);

  @SideOnly(Side.CLIENT)
  @Nonnull
  Gui constructGui(@Nonnull EntityPlayer player, @Nonnull TileEntity tile);

  @Nonnull
  String getName();
}
