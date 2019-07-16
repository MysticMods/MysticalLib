package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.block.BaseOreBlock;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;

public class OreFactory extends BlockFactory {

	public OreFactory() {
		super("ore");
	}

	@Override
	public Block create(IMaterial material, String modid) {
		return new BaseOreBlock(material.getBlockOreProps()).setRegistryName(modid, material.getName() + getSuffix());
	}
}
