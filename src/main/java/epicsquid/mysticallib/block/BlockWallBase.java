package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockWallBase extends WallBlock {
  public final String name;

  public BlockWallBase(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(Block.Properties.create(mat).sound(type).lightValue(15).hardnessAndResistance(hardness));
    this.name = name;
    setRegistryName(name);
  }
}
