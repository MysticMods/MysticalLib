package epicsquid.mysticallib.tile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.gui.GuiHandler;
import epicsquid.mysticallib.tile.module.FaceConfig;
import epicsquid.mysticallib.tile.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

public class TileModular extends TileBase {
  public Map<String, Module> modules = new HashMap<String, Module>();
  public FaceConfig config = new FaceConfig();
  protected boolean hasGui = true;
  public boolean canModifyIO = true;
  public Set<String> validIOModules = new HashSet<String>();

  public TileModular addModule(Module module) {
    modules.put(module.getModuleName(), module);
    validIOModules.add(module.getModuleName());
    return this;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    for (Module m : modules.values()) {
      tag.setTag(m.getModuleName(), m.writeToNBT());
    }
    tag.setTag("faceConfig", config.writeToNBT());
    NBTTagList validIO = new NBTTagList();
    for (String s : validIOModules) {
      validIO.appendTag(new NBTTagString(s));
    }
    tag.setTag("validIO", validIO);
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    for (Module m : modules.values()) {
      if (tag.hasKey(m.getModuleName())) {
        m.readFromNBT(tag.getCompoundTag(m.getModuleName()));
      }
    }
    validIOModules.clear();
    NBTTagList validIO = tag.getTagList("validIO", Constants.NBT.TAG_STRING);
    for (int i = 0; i < validIO.tagCount(); i++) {
      validIOModules.add(validIO.getStringTagAt(i));
    }
    config.readFromNBT(tag.getCompoundTag("faceConfig"));
  }

  @Override
  public boolean hasCapability(Capability capability, EnumFacing facing) {
    for (Module m : modules.values()) {
      if (m.hasCapability(capability, facing, this)) {
        return true;
      }
    }
    return super.hasCapability(capability, facing);
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    for (Module m : modules.values()) {
      if (m.hasCapability(capability, facing, this)) {
        return (T) m.getCapability(capability, facing, this);
      }
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY,
      float hitZ) {
    if (hasGui && GuiHandler.hasGui(getTileName(getClass()))) {
      player.openGui(MysticalLib.INSTANCE, GuiHandler.getGuiID(getTileName(getClass())), world, getPos().getX(), getPos().getY(), getPos().getZ());
      return true;
    }
    return false;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    for (Module m : modules.values()) {
      m.onBroken(world, pos, player);
    }
    super.breakBlock(world, pos, state, player);
  }
}
