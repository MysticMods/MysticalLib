package epicsquid.mysticallib.block;

import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;

import javax.annotation.Nonnull;

public class BaseLogBlock extends LogBlock  {

  public final String name;

  public BaseLogBlock(Properties props, @Nonnull String name) {
    super(MaterialColor.BROWN, props);
    this.name = name;
    setRegistryName(name);
  }
}
