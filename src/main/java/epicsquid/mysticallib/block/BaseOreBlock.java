package epicsquid.mysticallib.block;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BaseOreBlock extends OreBlock {
  private final int minXP;
  private final int maxXP;

  public BaseOreBlock(Properties props, int minXP, int maxXP) {
    super(props);
    this.minXP = minXP;
    this.maxXP = maxXP;
  }

  @Override
  protected int getExperience(Random rand) {
    return MathHelper.nextInt(rand, minXP, maxXP);
  }
}
