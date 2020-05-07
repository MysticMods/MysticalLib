package epicsquid.mysticallib.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class OreProperties {

  private Supplier<ConfiguredFeature<OreFeatureConfig, OreFeature>> oreFeature;
  private CountRangeConfig countRange;
  private Predicate<Biome> match;

  public OreProperties(Supplier<ConfiguredFeature<OreFeatureConfig, OreFeature>> oreFeature, CountRangeConfig countRange, Predicate<Biome> match) {
    this.oreFeature = oreFeature;
    this.countRange = countRange;
    this.match = match;
  }

  public OreProperties(Supplier<ConfiguredFeature<OreFeatureConfig, OreFeature>> oreFeature, CountRangeConfig countRange) {
    this(oreFeature, countRange, x -> true);
  }

  public ConfiguredFeature<OreFeatureConfig, OreFeature> getOreFeature() {
    return oreFeature.get();
  }

  public CountRangeConfig getCountRange() {
    return countRange;
  }

  public Predicate<Biome> getMatch() {
    return match;
  }
}
