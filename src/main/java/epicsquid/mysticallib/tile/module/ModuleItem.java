package epicsquid.mysticallib.tile.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.handlers.MysticalItemHandler;
import epicsquid.mysticallib.tile.TileModular;
import epicsquid.mysticallib.tile.module.FaceConfig.FaceIO;
import epicsquid.mysticallib.util.InventoryUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ModuleItem implements IModule<IItemHandler> {

  public static final @Nonnull String ITEM_MODULE = "ITEM_MODULE";

  private @Nonnull MysticalItemHandler inventory;
  private @Nonnull TileModular tile;
  private Set<Integer> inputSlots = new TreeSet<>();
  private Set<Integer> outputSlots = new TreeSet<>();
  private Map<EnumFacing, ItemIOProxy> ioProxies = new HashMap<>();
  private @Nonnull FaceConfig faceConfig;

  public ModuleItem(@Nonnull TileModular tile, int slotCount, @Nonnull Collection<Integer> inputSlots, @Nonnull Collection<Integer> outputSlots) {
    this.inventory = new MysticalItemHandler(slotCount);
    this.tile = tile;
    this.inputSlots.addAll(inputSlots);
    this.outputSlots.addAll(outputSlots);
    this.faceConfig = tile.getFaceConfig();
    for (EnumFacing dir : EnumFacing.values()) {
      ioProxies.put(dir, constructIOProxy(dir, slotCount));
    }
  }

  @Nonnull
  private ItemIOProxy constructIOProxy(@Nonnull EnumFacing dir, int capacity) {
    return new ItemIOProxy(dir, faceConfig.getIO(dir), capacity);
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<IItemHandler> capability, @Nullable EnumFacing face) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }

  @Override
  @Nonnull
  public IItemHandler getCapability(@Nullable Capability<IItemHandler> capability, @Nullable EnumFacing face) {
    if (face != null) {
      return ioProxies.get(face);
    } else {
      return inventory;
    }
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setTag("inventory", inventory.serializeNBT());
    return tag;
  }

  @Override
  public void readFromNBT(@Nonnull NBTTagCompound tag) {
    inventory.deserializeNBT(tag.getCompoundTag("inventory"));
  }

  @Override
  public void onBroken(@Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {
    if (!world.isRemote) {
      for (int i = 0; i < inventory.getSlots(); i++) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i)));
      }
    }
  }

  @Override
  public void onUpdate(@Nonnull BlockPos pos, @Nonnull World world) {
    // TODO: PIPES????????????
    // Check all connecting tiles for pushing
    for (EnumFacing dir : EnumFacing.values()) {
      if (faceConfig.getIO(dir) == FaceIO.OUT && world.isRemote) {
        // Get the tile next to the block to check its inventory
        TileEntity adjTile = world.getTileEntity(pos.offset(dir));
        if (adjTile != null && adjTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite())) {
          // Get the capability of the adjacent block
          IItemHandler adjInv = adjTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite());
          if (adjInv != null) {
            // Iterate through inventory slots and attempt to push them into the adjacent inventory
            for (int i = 0; i < inventory.getSlots(); i++) {
              if (!inventory.getStackInSlot(i).isEmpty() && outputSlots.contains(i)) {
                int amount = InventoryUtil.attemptInsert(inventory.getStackInSlot(i), adjInv, true);
                if (amount > 0) {
                  int amountInserted = InventoryUtil.attemptInsert(inventory.getStackInSlot(i), adjInv, false);
                  // Only mark the tiles for update if something was inserted
                  if (amountInserted > 0) {
                    inventory.getStackInSlot(i).shrink(amountInserted);
                    tile.markDirty();
                    adjTile.markDirty();
                    // TODO: NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
                    LibEvents.markForUpdate(tile.getPos().offset(dir), adjTile);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  @Nonnull
  @Override
  public String getModuleName() {
    return ModuleItem.ITEM_MODULE;
  }

  @Override
  @Nonnull
  public Capability<IItemHandler> getCapabilityType() {
    return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }

  public class ItemIOProxy extends ItemStackHandler {

    private @Nonnull EnumFacing dir;
    private @Nonnull FaceIO ioMode;

    public ItemIOProxy(@Nonnull EnumFacing dir, @Nonnull FaceIO faceIO, int size) {
      super(size);
      this.dir = dir;
      this.ioMode = faceIO;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      if ((ioMode == FaceIO.IN || ioMode == FaceIO.INOUT) && inputSlots.contains(slot)) {
        tile.markDirty();
        return inventory.insertItem(slot, stack, simulate);
      }
      return stack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if ((ioMode == FaceIO.OUT || ioMode == FaceIO.NEUTRAL || ioMode == FaceIO.INOUT) && outputSlots.contains(slot)) {
        tile.markDirty();
        return inventory.extractItem(slot, amount, simulate);
      }
      return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
      return inventory.getStackInSlot(slot);
    }

    @Override
    public int getSlotLimit(int slot) {
      return inventory.getSlotLimit(slot);
    }

  }
}
