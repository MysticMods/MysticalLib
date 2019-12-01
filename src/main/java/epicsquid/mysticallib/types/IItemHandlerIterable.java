package epicsquid.mysticallib.types;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Iterator;

public class IItemHandlerIterable implements Iterable<ItemStack> {
  private final IItemHandler handler;
  private int slot;

  public IItemHandlerIterable(IItemHandler handler) {
    this.handler = handler;
    this.slot = 0;
  }

  @Override
  public Iterator<ItemStack> iterator() {
    return new IItemHandlerIterator();
  }

  public class IItemHandlerIterator implements Iterator<ItemStack> {
    @Override
    public boolean hasNext() {
      return slot + 1 < handler.getSlots();
    }

    @Override
    public ItemStack next() {
      return handler.getStackInSlot(slot++);
    }
  }
}
