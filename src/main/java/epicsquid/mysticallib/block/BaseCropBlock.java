package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;

public class BaseCropBlock extends CropsBlock {

	/**
	 * Used for arbitrary crops
	 */
	public BaseCropBlock(Properties props) {
		super(props);
	}

	/**
	 * Scales the bounding box of the crop with it's age
	 */
	@Override
	@Nonnull
	public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
		return Block.makeCuboidShape(0, 0, 0, 16.0D, 2.0D * (state.get(AGE) + 1), 16.0D);
	}
}
