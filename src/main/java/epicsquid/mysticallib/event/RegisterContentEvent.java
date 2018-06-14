package epicsquid.mysticallib.event;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterContentEvent extends Event {
  private List<Item> items;
  private List<Block> blocks;

  public RegisterContentEvent(List<Item> items, List<Block> blocks) {
    super();
    this.items = items;
    this.blocks = blocks;
  }

  public Item addItem(Item item) {
    items.add(item);
    return item;
  }

  public Block addBlock(Block block) {
    blocks.add(block);
    return block;
  }
}
