package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockStairsBase extends StairsBlock {

  public final String name;

  public BlockStairsBase(@Nonnull BlockState base, @NonNull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(base, Block.Properties.create(mat).sound(type).lightValue(15).hardnessAndResistance(hardness));
    this.name = name;
    setRegistryName(name);
  }


}
