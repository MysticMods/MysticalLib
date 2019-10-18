package epicsquid.mysticallib.material.factory;

import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class SlabFactory extends BlockFactory {

  public SlabFactory() {
    super("slab");
  }

  @Override
  public Block create(IMaterial material, String modid) {
    return new SlabBlock(material.getBlockProps()).setRegistryName(modid, material.getName() + getSuffix());
  }
}
