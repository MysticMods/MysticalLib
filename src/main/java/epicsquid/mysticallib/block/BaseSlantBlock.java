package epicsquid.mysticallib.block;

import javax.annotation.Nonnull;

public class BaseSlantBlock extends BaseBlock {
	public final String name;

	public BaseSlantBlock(Properties props, @Nonnull String name) {
		super(props, name);
		this.name = name;
		setRegistryName(name);
	}
}
