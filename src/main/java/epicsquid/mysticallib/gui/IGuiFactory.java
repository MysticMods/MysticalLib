package epicsquid.mysticallib.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiFactory {
  default Container constructContainer(EntityPlayer player, World world, int x, int y, int z) {
    return constructContainer(player, world.getTileEntity(new BlockPos(x, y, z)));
  }

  @SideOnly(Side.CLIENT)
  default Gui constructGui(EntityPlayer player, World world, int x, int y, int z) {
    return constructGui(player, world.getTileEntity(new BlockPos(x, y, z)));
  }

  public Container constructContainer(EntityPlayer player, TileEntity tile);

  @SideOnly(Side.CLIENT)
  public Gui constructGui(EntityPlayer player, TileEntity tile);

  public String getName();
}
