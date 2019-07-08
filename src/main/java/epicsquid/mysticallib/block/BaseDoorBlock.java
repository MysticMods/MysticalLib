package epicsquid.mysticallib.block;

import net.minecraft.block.DoorBlock;

import javax.annotation.Nonnull;

public class BaseDoorBlock extends DoorBlock {
	public final String name;

	public BaseDoorBlock(Properties props, @Nonnull String name) {
		super(props);
		this.name = name;
		setRegistryName(name);
	}
}
