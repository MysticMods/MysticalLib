package epicsquid.mysticallib.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {
  private OreProperties oreProperties;

  public OreGenerator(OreProperties oreProperties) {
    this.oreProperties = oreProperties;
  }

  public void init() {
    for (Biome biome : ForgeRegistries.BIOMES) {
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(oreProperties.getOreFeature()));
    }
  }
}
