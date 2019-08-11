package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.block.BaseWoodButtonBlock;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WoodButtonBlock;

public class WoodButtonFactory extends BlockFactory {

	public WoodButtonFactory() {
		super("wood_button");
	}

	@Override
	public Block create(IMaterial material, String modid) {
		return new BaseWoodButtonBlock(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
	}

	@Override
	public String getSuffix() {
		return "_button";
	}
}
