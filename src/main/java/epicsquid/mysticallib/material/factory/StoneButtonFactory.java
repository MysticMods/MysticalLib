package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.block.BaseStoneButtonBlock;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;

public class StoneButtonFactory extends BlockFactory {

  public StoneButtonFactory() {
    super("stone_button");
  }

  @Override
  public Block create(IMaterial material, String modid) {
    return new BaseStoneButtonBlock(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
  }

  @Override
  public String getSuffix() {
    return "_button";
  }
}
