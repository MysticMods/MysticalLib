package epicsquid.mysticallib.block;

import net.minecraft.block.BushBlock;

import javax.annotation.Nonnull;

public class BaseSaplingBlock extends BushBlock {
	public final String name;

	public BaseSaplingBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
