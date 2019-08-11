package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;

public class WallFactory extends BlockFactory {

	public WallFactory() {
		super("wall");
	}

	@Override
	public Block create(IMaterial material, String modid) {
		return new WallBlock(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
	}
}
