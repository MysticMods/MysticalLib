package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class OreBlock extends Block {
	private final int minXP;
	private final int maxXP;
	private final int level;
	private final ToolType tool;

	public OreBlock(Properties props, int level, int minXp, int maxXp, ToolType tool) {
		super(props);
		this.level = level;
		this.tool = tool;
		this.minXP = minXp;
		this.maxXP = maxXp;
	}

	@Nullable
	@Override
	public ToolType getHarvestTool(BlockState state) {
		return tool;
	}

	@Override
	public int getHarvestLevel(BlockState state) {
		return level;
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
		if (minXP == -1 || maxXP == -1)
			return 0;

		Random rand = world instanceof World ? ((World) world).rand : new Random();
		return rand.nextInt(maxXP - minXP) + minXP;
	}

}
