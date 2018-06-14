package epicsquid.mysticallib.tile.module;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class FaceConfig {
  public Map<EnumFacing, FaceIO> ioConfig = new HashMap<EnumFacing, FaceIO>();
  public Map<EnumFacing, String> moduleConfig = new HashMap<EnumFacing, String>();

  public FaceConfig() {
    for (EnumFacing e : EnumFacing.values()) {
      ioConfig.put(e, FaceIO.NEUTRAL);
      moduleConfig.put(e, "");
    }
  }

  public NBTTagCompound writeToNBT() {
    NBTTagCompound iotag = new NBTTagCompound();
    NBTTagCompound moduletag = new NBTTagCompound();
    for (EnumFacing e : EnumFacing.values()) {
      iotag.setInteger(e.getName(), ioConfig.get(e).ordinal());
      moduletag.setString(e.getName(), moduleConfig.get(e));
    }
    NBTTagCompound tag = new NBTTagCompound();
    tag.setTag("io", iotag);
    tag.setTag("modules", moduletag);
    return tag;
  }

  public void readFromNBT(NBTTagCompound tag) {
    NBTTagCompound iotag = tag.getCompoundTag("io");
    NBTTagCompound moduletag = tag.getCompoundTag("modules");
    for (EnumFacing e : EnumFacing.values()) {
      if (iotag.hasKey(e.getName())) {
        ioConfig.put(e, FaceIO.values()[iotag.getInteger(e.getName())]);
      }
      if (moduletag.hasKey(e.getName())) {
        moduleConfig.put(e, moduletag.getString(e.getName()));
      }
    }
  }

  public void setIO(EnumFacing face, FaceIO io) {
    ioConfig.replace(face, io);
  }

  public void setModule(EnumFacing face, String module) {
    moduleConfig.replace(face, module);
  }

  public void setAllIO(FaceIO io) {
    for (EnumFacing e : EnumFacing.values()) {
      setIO(e, io);
    }
  }

  public void setAllModules(String module) {
    for (EnumFacing e : EnumFacing.values()) {
      setModule(e, module);
    }
  }

  public enum FaceIO {
    IN, OUT, NEUTRAL, INOUT
  }
}
