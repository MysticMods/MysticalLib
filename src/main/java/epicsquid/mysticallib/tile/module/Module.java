package epicsquid.mysticallib.tile.module;

import javax.annotation.Nullable;

import epicsquid.mysticallib.tile.TileModular;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public abstract class Module {
  String moduleName;

  public Module(String name) {
    moduleName = name;
  }

  public abstract boolean hasCapability(Capability<?> capability, EnumFacing facing, TileModular tile);

  public abstract Object getCapability(Capability<?> capability, EnumFacing facing, TileModular tile);

  public abstract Capability getCapabilityType();

  public abstract NBTTagCompound writeToNBT();

  public abstract void readFromNBT(NBTTagCompound tag);

  public String getModuleName() {
    return moduleName;
  }

  public void onBroken(World world, BlockPos pos, @Nullable EntityPlayer player) {
    //
  }

  public void onUpdate(TileModular tile) {
    //
  }
}
