package epicsquid.mysticallib.block;

import net.minecraft.block.Block;

public class OreBlockProperties {

  private Block.Properties props;
  private int minXp;
  private int maxXp;

  public OreBlockProperties(Block.Properties props, int minXp, int maxXp) {
    this.props = props;
    this.minXp = minXp;
    this.maxXp = maxXp;
  }

  public Block.Properties getProps() {
    return props;
  }

  public int getMinXp() {
    return minXp;
  }

  public int getMaxXp() {
    return maxXp;
  }
}
