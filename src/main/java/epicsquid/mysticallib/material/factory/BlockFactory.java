package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;

public class BlockFactory implements IBlockMaterialFactory {

	private String name = "";

	public BlockFactory() {

	}

	public BlockFactory(String name) {
		this.name = name;
	}

	@Override
	public Block create(IMaterial material, String modid) {
		return new Block(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
	}

	@Override
	public String getSuffix() {
		return !name.equals("") ? "_" + getName() : "";
	}

	@Override
	public String getName() {
		return name;
	}
}
