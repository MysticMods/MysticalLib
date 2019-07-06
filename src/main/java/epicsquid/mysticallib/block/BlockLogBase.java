package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockLogBase extends LogBlock  {

  public final String name;

  public BlockLogBase(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(MaterialColor.BROWN, Block.Properties.create(mat).sound(type).lightValue(15).hardnessAndResistance(hardness));
    this.name = name;
    setRegistryName(name);
  }
}
