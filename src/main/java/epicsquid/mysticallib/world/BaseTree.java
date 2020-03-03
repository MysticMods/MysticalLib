package epicsquid.mysticallib.world;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class BaseTree extends Tree {

  private final TreeFeatureConfig config;

  public BaseTree(TreeFeatureConfig config) {
    this.config = config;
  }

  public ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeaturePublic(Random rand, boolean b) {
    return getTreeFeature(rand, b);
  }

  @Nullable
  @Override
  protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
    return new TreeFeature(TreeFeatureConfig::func_227338_a_).withConfiguration(config);
  }
}
