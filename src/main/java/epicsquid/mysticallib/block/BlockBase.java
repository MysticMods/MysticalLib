package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockBase extends Block{

  private VoxelShape box = Block.makeCuboidShape(16.0D,16.0D,16.0D,16.0D,16.0D,16.0D);
  public @Nonnull String name;

  public BlockBase(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name) {
    super(Block.Properties.create(mat).sound(type).lightValue(15).hardnessAndResistance(hardness));
    this.name = name;
    setRegistryName(name);
  }

  /**
   * Builder methods
   * */
  @Nonnull
  public BlockBase setBox(@Nonnull VoxelShape box) {
    this.box = box;
    return this;
  }


  /**
   * Overridden Methods
   * */
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
    return box;
  }

}
