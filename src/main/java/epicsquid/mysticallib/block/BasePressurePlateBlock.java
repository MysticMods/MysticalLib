package epicsquid.mysticallib.block;

import net.minecraft.block.PressurePlateBlock;

import javax.annotation.Nonnull;

public class BasePressurePlateBlock extends PressurePlateBlock {
  public final String name;

  public BasePressurePlateBlock(Properties props, @Nonnull String name) {
    super(Sensitivity.EVERYTHING, props);
    this.name = name;
    setRegistryName(name);
  }
}
