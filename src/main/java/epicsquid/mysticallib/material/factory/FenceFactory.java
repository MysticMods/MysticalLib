package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SlabBlock;

public class FenceFactory extends BlockFactory {

	public FenceFactory() {
		super("fence");
	}

	@Override
	public Block create(IMaterial material, String modid) {
		return new FenceBlock(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
	}
}
