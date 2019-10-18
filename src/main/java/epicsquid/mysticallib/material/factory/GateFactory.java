package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;

public class GateFactory extends BlockFactory {

  public GateFactory() {
    super("gate");
  }

  @Override
  public Block create(IMaterial material, String modid) {
    return new FenceGateBlock(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
