package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.block.BaseStairsBlock;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;

public class StairFactory extends BlockFactory {

	public StairFactory() {
		super("stair");
	}

	@Override
	public Block create(IMaterial material, String modid) {
		return new BaseStairsBlock(material.getDecoBlockstate(), material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
	}
}
