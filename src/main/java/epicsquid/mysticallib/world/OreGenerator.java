package epicsquid.mysticallib.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

  private OreProperties oreProperties;

  public OreGenerator(OreProperties oreProperties) {
    this.oreProperties = oreProperties;
  }

  public void init() {
    for (Biome biome : ForgeRegistries.BIOMES) {
      ConfiguredFeature<?> featureOverworld = Biome.createDecoratedFeature(Feature.ORE,
          oreProperties.getOreFeature(), Placement.COUNT_RANGE, oreProperties.getCountRange());
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new DimensionalOreFeature<>(featureOverworld, DimensionType.OVERWORLD));
    }
  }
}
