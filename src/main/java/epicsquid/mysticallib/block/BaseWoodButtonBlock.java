package epicsquid.mysticallib.block;

import net.minecraft.block.WoodButtonBlock;

import javax.annotation.Nonnull;

public class BaseWoodButtonBlock extends WoodButtonBlock{
  public final String name;

  public BaseWoodButtonBlock(Properties props, @Nonnull String name) {
    super(props);
    this.name = name;
    setRegistryName(name);
  }
}
