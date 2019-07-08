package epicsquid.mysticallib.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

import javax.annotation.Nonnull;

public class BaseStairsBlock extends StairsBlock {

	public final String name;

	public BaseStairsBlock(@Nonnull BlockState base, Properties props, @Nonnull String name) {
		super(base, props);
		this.name = name;
		setRegistryName(name);
	}


}
