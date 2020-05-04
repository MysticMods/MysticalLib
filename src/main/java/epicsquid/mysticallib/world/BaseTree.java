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

  @Nullable
  protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean beeHive) {
    return Feature.NORMAL_TREE.configure(config);
  }
}
