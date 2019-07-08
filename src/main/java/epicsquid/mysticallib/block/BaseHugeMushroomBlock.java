package epicsquid.mysticallib.block;

import net.minecraft.block.Block;

public class BaseHugeMushroomBlock extends BaseBlock {

	private Block smallBlock;

	public BaseHugeMushroomBlock(Properties props, String name) {
		super(props, name);
	}

	public void setSmallBlock(Block smallBlockIn) {
		this.smallBlock = smallBlockIn;
	}

}
