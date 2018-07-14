package epicsquid.mysticallib.tile.module;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * Stores the configuration for a block's IO
 */
public class FaceConfig {

  private Map<EnumFacing, FaceIO> ioConfig = new EnumMap<>(EnumFacing.class);

  public FaceConfig() {
    for (EnumFacing e : EnumFacing.values()) {
      ioConfig.put(e, FaceIO.NEUTRAL);
    }
  }

  @Nonnull
  public NBTTagCompound writeToNBT() {
    NBTTagCompound iotag = new NBTTagCompound();
    for (EnumFacing e : EnumFacing.values()) {
      iotag.setInteger(e.getName(), ioConfig.get(e).ordinal());
    }
    NBTTagCompound tag = new NBTTagCompound();
    tag.setTag("io", iotag);
    return tag;
  }

  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    NBTTagCompound iotag = tag.getCompoundTag("io");
    for (EnumFacing e : EnumFacing.values()) {
      if (iotag.hasKey(e.getName())) {
        ioConfig.put(e, FaceIO.values()[iotag.getInteger(e.getName())]);
      }
    }
  }

  public void setIO(@Nonnull EnumFacing face, @Nonnull FaceIO io) {
    ioConfig.replace(face, io);
  }

  public void setAllIO(@Nonnull FaceIO io) {
    for (EnumFacing e : EnumFacing.values()) {
      setIO(e, io);
    }
  }

  @Nonnull
  public FaceIO getIO(@Nonnull EnumFacing dir) {
    return ioConfig.get(dir);
  }

  public enum FaceIO {
    IN,
    OUT,
    NEUTRAL,
    INOUT,
    DISABLED
  }
}
