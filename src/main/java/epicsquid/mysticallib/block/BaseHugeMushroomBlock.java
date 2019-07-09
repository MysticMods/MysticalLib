package epicsquid.mysticallib.block;

import net.minecraft.block.Block;

public class BaseHugeMushroomBlock extends Block {

	private Block smallBlock;

	public BaseHugeMushroomBlock(Properties props) {
		super(props);
	}

	public void setSmallBlock(Block smallBlockIn) {
		this.smallBlock = smallBlockIn;
	}

}
