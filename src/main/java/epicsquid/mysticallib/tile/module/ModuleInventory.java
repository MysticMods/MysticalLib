package epicsquid.mysticallib.tile.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.inventory.IExtendedInventory;
import epicsquid.mysticallib.inventory.InventoryHandler;
import epicsquid.mysticallib.predicates.PredicateTrue;
import epicsquid.mysticallib.tile.TileModular;
import epicsquid.mysticallib.tile.module.FaceConfig.FaceIO;
import epicsquid.mysticallib.util.InventoryUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ModuleInventory extends Module implements IExtendedInventory {
  public InventoryHandler inventory;
  public TileModular tile = null;
  public ArrayList<Predicate<ItemStack>> predicates = new ArrayList<Predicate<ItemStack>>();
  public int slotCount = 0;
  String name = "";
  public Set<Integer> inputSlots = new TreeSet<Integer>();
  public Set<Integer> outputSlots = new TreeSet<Integer>();
  public Map<EnumFacing, ItemIOProxy> ioProxies = new HashMap<EnumFacing, ItemIOProxy>();

  public ModuleInventory(String name, TileModular tile, int slotCount, String containerName, int[] inputSlots, int[] outputSlots) {
    super(name);
    this.slotCount = slotCount;
    inventory = new InventoryHandler(slotCount, this);
    this.tile = tile;
    name = containerName;
    for (int i = 0; i < slotCount; i++) {
      predicates.add(new PredicateTrue());
    }
    for (int i : inputSlots) {
      this.inputSlots.add(i);
    }
    for (int i : outputSlots) {
      this.outputSlots.add(i);
    }
    for (EnumFacing f : EnumFacing.values()) {
      ioProxies.put(f, constructIOProxy(f, slotCount));
    }
  }

  public ModuleInventory(String name, TileModular tile, int slotCount, String containerName, Collection<Integer> inputSlots, Collection<Integer> outputSlots) {
    super(name);
    this.slotCount = slotCount;
    inventory = new InventoryHandler(slotCount, this);
    this.tile = tile;
    name = containerName;
    for (int i = 0; i < slotCount; i++) {
      predicates.add(new PredicateTrue());
    }
    for (int i : inputSlots) {
      this.inputSlots.add(i);
    }
    for (int i : outputSlots) {
      this.outputSlots.add(i);
    }
    for (EnumFacing f : EnumFacing.values()) {
      ioProxies.put(f, constructIOProxy(f, slotCount));
    }
  }

  public ItemIOProxy constructIOProxy(EnumFacing face, int capacity) {
    return new ItemIOProxy(face, tile, capacity, this);
  }

  public class ItemIOProxy extends InventoryHandler {
    EnumFacing face = EnumFacing.NORTH;
    TileModular tile = null;

    public ItemIOProxy(EnumFacing face, TileModular tile, int size, IExtendedInventory inventory) {
      super(size, inventory);
      this.face = face;
      this.tile = tile;
    }

    public ItemIOProxy setTile(TileModular tile) {
      this.tile = tile;
      return this;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      if ((tile.faceConfig.ioConfig.get(face) == FaceIO.IN || tile.faceConfig.ioConfig.get(face) == FaceIO.INOUT) && inputSlots.contains(slot)) {
        return inventory.insertItem(slot, stack, simulate);
      }
      return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if ((tile.faceConfig.ioConfig.get(face) == FaceIO.OUT || tile.faceConfig.ioConfig.get(face) == FaceIO.NEUTRAL || tile.faceConfig.ioConfig.get(face) == FaceIO.INOUT)
          && outputSlots.contains(slot)) {
        return inventory.extractItem(slot, amount, simulate);
      }
      return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
      return inventory.getStackInSlot(slot);
    }

    @Override
    public int getSlotLimit(int slot) {
      return inventory.getSlotLimit(slot);
    }

  }

  public ModuleInventory setSlotPredicate(int slot, Predicate<ItemStack> predicate) {
    predicates.set(slot, predicate);
    return this;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean hasCustomName() {
    return false;
  }

  @Override
  public int getSizeInventory() {
    return slotCount;
  }

  @Override
  public boolean isEmpty() {
    for (int i = 0; i < inventory.getSlots(); i++) {
      if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getStackInSlot(int index) {
    return inventory.getStackInSlot(index);
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {
    ItemStack toReturn = inventory.extractItem(index, count, false);
    markDirty();
    return toReturn;
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {
    ItemStack toReturn = inventory.getStackInSlot(index).copy();
    inventory.setStackInSlot(index, ItemStack.EMPTY);
    markDirty();
    return toReturn;
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {
    inventory.setStackInSlot(index, stack);
    markDirty();
  }

  @Override
  public int getInventoryStackLimit() {
    return 64;
  }

  @Override
  public boolean isUsableByPlayer(EntityPlayer player) {
    return true;
  }

  @Override
  public void openInventory(EntityPlayer player) {

  }

  @Override
  public void closeInventory(EntityPlayer player) {

  }

  @Override
  public int getField(int id) {
    return 0;
  }

  @Override
  public void setField(int id, int value) {

  }

  @Override
  public int getFieldCount() {
    return 0;
  }

  @Override
  public void clear() {
    for (int i = 0; i < inventory.getSlots(); i++) {
      inventory.setStackInSlot(i, ItemStack.EMPTY);
    }
    markDirty();
  }

  @Override
  public boolean isItemValidForSlot(int index, ItemStack stack) {
    return predicates.get(index).test(stack);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public ITextComponent getDisplayName() {
    return new TextComponentString(I18n.format("elmodule.inventory." + getName() + ".name"));
  }

  @Override
  public void markDirty() {
    if (tile != null) {
      tile.markDirty();
    }
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing face, TileModular tile) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }

  @Override
  public Object getCapability(Capability<?> capability, EnumFacing face, TileModular tile) {
    if (face != null) {
      return ioProxies.get(face).setTile(tile);
    } else {
      return inventory;
    }
  }

  @Override
  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setTag("inventory", inventory.serializeNBT());
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    inventory.deserializeNBT(tag.getCompoundTag("inventory"));
  }

  @Override
  public boolean canExtractFromSlot(int slot) {
    return true;
  }

  @Override
  public boolean canInsertToSlot(int slot) {
    return true;
  }

  @Override
  public void onBroken(World world, BlockPos pos, EntityPlayer player) {
    if (!world.isRemote) {
      for (int i = 0; i < getSizeInventory(); i++) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, getStackInSlot(i)));
      }
    }
  }

  @Override
  public void onUpdate(TileModular tile) {
    for (EnumFacing f : EnumFacing.values()) {
      if (hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f, tile)) {
        if (tile.faceConfig.ioConfig.get(f) == FaceIO.OUT && !tile.getWorld().isRemote && tile.validIOModules.contains(this.getModuleName())) {
          TileEntity t = tile.getWorld().getTileEntity(tile.getPos().offset(f));
          if (t != null && t.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite())) {
            IItemHandler inv = t.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
            for (int i = 0; i < inventory.getSlots(); i++) {
              if (!inventory.getStackInSlot(i).isEmpty() && canExtractFromSlot(i) && outputSlots.contains(i)) {
                int amount = InventoryUtil.attemptInsert(inventory.getStackInSlot(i), inv, true);
                if (amount > 0) {
                  InventoryUtil.attemptInsert(inventory.getStackInSlot(i), inv, false);
                  decrStackSize(i, amount);
                  tile.markDirty();
                  t.markDirty();
                  LibEvents.markForUpdate(tile.getPos().offset(f), t);
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  public Capability getCapabilityType() {
    return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }
}
