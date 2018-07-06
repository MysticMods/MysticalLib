package epicsquid.mysticallib.world;

import java.util.Random;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.block.BlockCornerBase;
import epicsquid.mysticallib.block.BlockSlantBase;
import epicsquid.mysticallib.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldGenTest extends WorldGenBase {

  private String structureId = "";

  public static final String[] compass = { "....E....", ".........", "....^....", "....|....", "N.<-O->.S", "....|....", "....v....", ".........",
      "....W....", };

  public WorldGenTest(float chance) {
    super(chance);
    StructureData tower = new StructureData();
    tower.addBlock("R", LibRegistry.test_slant.getDefaultState().withProperty(BlockSlantBase.DIR, 3).withProperty(BlockSlantBase.VERT, 0));
    tower.addBlock("r", LibRegistry.test_slant.getDefaultState().withProperty(BlockSlantBase.DIR, 2).withProperty(BlockSlantBase.VERT, 2));
    tower.addBlock("B", Blocks.STONE.getDefaultState());
    tower.addBlock("1", LibRegistry.test_inner_corner.getDefaultState().withProperty(BlockCornerBase.DIR, 0));
    tower.addBlock("2", LibRegistry.test_inner_corner.getDefaultState().withProperty(BlockCornerBase.DIR, 1));
    tower.addBlock("3", LibRegistry.test_inner_corner.getDefaultState().withProperty(BlockCornerBase.DIR, 2));
    tower.addBlock("4", LibRegistry.test_inner_corner.getDefaultState().withProperty(BlockCornerBase.DIR, 3));
    tower.addBlock(" ", Blocks.AIR.getDefaultState());
    tower.addLayer(new String[] { "BBBBB", "BBBBB", "BBBBB", "BBBBB", "BBBBB", }, 0);
    tower.addLayer(new String[] { "2   3", "     ", "     ", "     ", "1   4", }, 1);
    tower.addLayer(new String[] { "2   3", "     ", "     ", "     ", "1   4", }, 2);
    tower.addLayer(new String[] { "2   3", "     ", "     ", "rrrrr", "RRRRR", }, 3);
    tower.addLayer(new String[] { "2   3", "     ", "rrrrr", "RRRRR", "     ", }, 4);
    tower.addLayer(new String[] { "2   3", "rrrrr", "RRRRR", "     ", "     ", }, 5);
    tower.addLayer(new String[] { "2rrr3", "RRRRR", "     ", "     ", "     ", }, 6);
    tower.addLayer(new String[] { "RRRRR", "     ", "     ", "     ", "     ", }, 7);
    structureId = StructureRegistry.addStructure(Util.getLowercaseClassName(getClass()) + "_0", tower);
  }

  @Override
  public void generateStruct(@Nonnull Random random, int chunkX, int chunkZ, @Nonnull World world, @Nonnull IChunkGenerator chunkGenerator,
      @Nonnull IChunkProvider chunkProvider) {
    GenerationData.get(world).addNode(new GenerationNode(world.getTopSolidOrLiquidBlock(new BlockPos(chunkX * 16, 64, chunkZ * 16)).down(1), structureId,
        Rotation.values()[random.nextInt(4)], Mirror.NONE, true));
  }
}
