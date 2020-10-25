package epicsquid.mysticallib.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;

public interface IBlock {

  @Nullable
  Item getItemBlock();

  ItemBlock setItemBlock(ItemBlock block);
}
