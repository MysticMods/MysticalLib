package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class OreBlock extends Block {
	private final int minXP;
	private final int maxXP;

	public OreBlock(Properties props, int minXp, int maxXp) {
		super(props);
		this.minXP = minXp;
		this.maxXP = maxXp;
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
		if (minXP == -1 || maxXP == -1)
			return 0;

		Random rand = world instanceof World ? ((World) world).rand : new Random();
		return rand.nextInt(maxXP - minXP) + minXP;
	}

}
