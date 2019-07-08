package epicsquid.mysticallib.block;

import net.minecraft.block.FenceBlock;

import javax.annotation.Nonnull;

public class BaseFenceBlock extends FenceBlock {
  public final String name;

  public BaseFenceBlock(Properties props, @Nonnull String name) {
    super(props);
    this.name = name;
    setRegistryName(name);
  }
}
