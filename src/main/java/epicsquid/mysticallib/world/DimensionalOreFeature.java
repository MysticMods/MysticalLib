package epicsquid.mysticallib.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nonnull;
import java.util.Random;

public class DimensionalOreFeature extends ConfiguredFeature<OreFeatureConfig, Feature<OreFeatureConfig>> {

  private DimensionType dimType;

  public DimensionalOreFeature(ConfiguredFeature<OreFeatureConfig, ?> feature, DimensionType dimension) {
    super(feature.feature, feature.config);
    this.dimType = dimension;
  }

  @Override
  public boolean place(@Nonnull IWorld world, @Nonnull ChunkGenerator<? extends GenerationSettings> chunk, @Nonnull Random rand, @Nonnull BlockPos pos) {
    if (world.getDimension().getType().equals(dimType)) {
      return super.place(world, chunk, rand, pos);
    }
    return false;
  }
}
