package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHugeMushroomBase extends BlockBase {
  private Block smallBlock;

  public BlockHugeMushroomBase(Material materialIn, SoundType type, float hardness, String name) {
    super(materialIn, type, hardness, name);
  }

  public void setSmallBlock(Block smallBlockIn) {
    this.smallBlock = smallBlockIn;
  }

}
