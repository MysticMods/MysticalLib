package epicsquid.mysticallib.block;

import net.minecraft.block.StoneButtonBlock;

import javax.annotation.Nonnull;

public class BaseStoneButtonBlock extends StoneButtonBlock {
  public final String name;

  public BaseStoneButtonBlock(Properties props, @Nonnull String name) {
    super(props);
    this.name = name;
    setRegistryName(name);
  }

}
