package epicsquid.mysticallib.block;

import epicsquid.mysticallib.model.IModeledObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockCropBase extends CropsBlock implements IBlock, IModeledObject {

  private final @Nonnull PlantType plantType;

  /**
   * Used for arbitrary crops
   *
   * @param name Name of the crop
   */
  public BlockCropBase(@Nonnull Material material, @Nonnull String name, @Nonnull PlantType plantType) {
    super(Block.Properties.create(material));
    setRegistryName(name);

    this.plantType = plantType;
  }

  /**
   * Controls the type of plant
   */
  @Nonnull
  public PlantType getPlantType() {
    return plantType;
  }

  /**
   * Scales the bounding box of the crop with it's age
   */
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
    return Block.makeCuboidShape(0, 0, 0, 16.0D, 2.0D * (state.get(this.AGE) + 1), 16.0D);
  }

  @Override
  public void initModel() {
  }

  /**
   * Return null since we don't want an item for this block
   */
  @Override
  public Item getItemBlock() {
    return null;
  }
}
