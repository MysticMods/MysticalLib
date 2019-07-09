package epicsquid.mysticallib.world;

import epicsquid.mysticallib.util.NoiseGenUtil;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WorldGenBase implements IWorldGenerator {

	public float spawnChance = 0f;
	public Set<Block> spawnable = new HashSet<>();

	// TODO investigate new features system
	public WorldGenBase(float chance) {
		this.spawnChance = chance;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, AbstractChunkProvider chunkProvider) {
		Random rand = NoiseGenUtil.getRandom(chunkX, chunkZ, getClass().getTypeName().hashCode());
		if (rand.nextFloat() < spawnChance) {
			this.generateStruct(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	public void generateStruct(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull ChunkGenerator chunkGen,
														 @Nonnull AbstractChunkProvider chunkProv) {
	}

}
