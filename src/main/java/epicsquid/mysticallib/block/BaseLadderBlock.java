package epicsquid.mysticallib.block;

import net.minecraft.block.LadderBlock;

import javax.annotation.Nonnull;

public class BaseLadderBlock extends LadderBlock {
  public final String name;

  public BaseLadderBlock(Properties props, @Nonnull String name) {
    super(props);
    this.name = name;
    setRegistryName(name);
  }
}
