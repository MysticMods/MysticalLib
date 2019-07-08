package epicsquid.mysticallib.block;

import net.minecraft.block.TrapDoorBlock;

import javax.annotation.Nonnull;

public class BaseTrapDoorBlock extends TrapDoorBlock {
	public final String name;

	public BaseTrapDoorBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
