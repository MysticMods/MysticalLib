package epicsquid.mysticallib.block;

import net.minecraft.block.MushroomBlock;

import javax.annotation.Nonnull;

public class BaseMushroomBlock extends MushroomBlock {

  public final String name;

  public BaseMushroomBlock(Properties props, @Nonnull String name) {
    super(props);
    this.name = name;
    setRegistryName(name);
  }
}
