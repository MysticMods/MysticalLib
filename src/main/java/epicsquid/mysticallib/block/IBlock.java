package epicsquid.mysticallib.block;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

public interface IBlock {

  @Nullable
  Item getItemBlock();
}
