package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.block.BasePressurePlateBlock;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;

public class PressurePlateFactory extends BlockFactory {

  public PressurePlateFactory() {
    super("pressure_plate");
  }

  @Override
  public Block create(IMaterial material, String modid) {
    return new BasePressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
