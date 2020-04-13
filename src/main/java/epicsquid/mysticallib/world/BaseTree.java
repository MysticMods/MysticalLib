package epicsquid.mysticallib.world;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class BaseTree extends Tree {

  private final TreeFeatureConfig config;

  public BaseTree(TreeFeatureConfig config) {
    this.config = config;
  }

  /**
   * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
   */
  @Nullable
  protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean beeHive) {
    return Feature.NORMAL_TREE.withConfiguration(config);
  }
}
