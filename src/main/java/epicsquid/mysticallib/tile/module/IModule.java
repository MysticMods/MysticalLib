package epicsquid.mysticallib.tile.module;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public interface IModule<T> {

  boolean hasCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing);

  @Nullable
  T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing);

  @Nonnull
  Capability<T> getCapabilityType();

  @Nonnull
  NBTTagCompound writeToNBT();

  void readFromNBT(@Nonnull NBTTagCompound tag);

  @Nonnull
  String getModuleName();

  void onBroken(@Nonnull World world, @Nonnull BlockPos pos, @Nullable EntityPlayer player);

  void onUpdate(@Nonnull BlockPos pos, @Nonnull World world);
}
