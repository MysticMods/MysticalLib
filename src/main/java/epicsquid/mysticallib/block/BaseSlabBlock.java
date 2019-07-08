package epicsquid.mysticallib.block;

import net.minecraft.block.SlabBlock;

import javax.annotation.Nonnull;

public class BaseSlabBlock extends SlabBlock {

	public final String name;

	public BaseSlabBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
