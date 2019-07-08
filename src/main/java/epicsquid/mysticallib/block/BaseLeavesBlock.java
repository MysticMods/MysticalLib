package epicsquid.mysticallib.block;

import net.minecraft.block.LeavesBlock;

import javax.annotation.Nonnull;

public class BaseLeavesBlock extends LeavesBlock {
	public final String name;

	public BaseLeavesBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
