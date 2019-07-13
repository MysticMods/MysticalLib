package epicsquid.mysticallib.block;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BaseOreBlock extends OreBlock {
	private final int minXP;
	private final int maxXP;

	public BaseOreBlock(OreBlockProperties props) {
		super(props.getProps());
		this.minXP = props.getMinXp();
		this.maxXP = props.getMaxXp();
	}

	@Override
	protected int func_220281_a(Random rand) {
		return MathHelper.nextInt(rand, minXP, maxXP);
	}
}
