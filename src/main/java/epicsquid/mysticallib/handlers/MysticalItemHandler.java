package epicsquid.mysticallib.handlers;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Mystical lib's implementation of
 */
public class MysticalItemHandler extends ItemStackHandler {

  public MysticalItemHandler() {
  }

  public MysticalItemHandler(int size) {
    super(size);
  }

  public MysticalItemHandler(@Nonnull NonNullList<ItemStack> stacks) {
    super(stacks);
  }
}
