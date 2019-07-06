package epicsquid.mysticallib.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockSlantBase extends BlockBase {
  public final String name;

  public BlockSlantBase(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(mat, type, hardness, name);
    this.name = name;
    setRegistryName(name);
  }
}
