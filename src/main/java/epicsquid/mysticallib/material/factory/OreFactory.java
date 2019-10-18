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
    Block ore = new BaseOreBlock(material.getBlockOreProps()).setRegistryName(modid, material.getName() + getSuffix());
    material.setOre(ore);
    return ore;
  }
}
