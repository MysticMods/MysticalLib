package epicsquid.mysticallib.block;

import net.minecraft.block.WallBlock;

import javax.annotation.Nonnull;

public class BaseWallBlock extends WallBlock {
	public final String name;

	public BaseWallBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
