package epicsquid.mysticallib.world;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import epicsquid.mysticallib.util.NoiseGenUtil;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenBase implements IWorldGenerator {
  public float spawnChance = 0f;
  public Set<Block> spawnable = new HashSet<>();

  public WorldGenBase(float chance) {
    this.spawnChance = chance;
  }

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    Random rand = NoiseGenUtil.getRandom(chunkX, chunkZ, getClass().getTypeName().hashCode());
    if (rand.nextFloat() < spawnChance) {
      this.generateStruct(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }
  }

  public void generateStruct(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGen, IChunkProvider chunkProv) {
    //
  }

}
