package epicsquid.mysticallib.block;

import net.minecraft.block.Block;

import javax.annotation.Nonnull;

public class BaseBlock extends Block {

	public final String name;

	public BaseBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
