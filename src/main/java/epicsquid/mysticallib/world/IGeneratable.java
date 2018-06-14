package epicsquid.mysticallib.world;

import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;

public interface IGeneratable {
  public void generateIn(World world, int x, int y, int z, Rotation rotation, Mirror doMirror, boolean replaceWithAir);

  public void calcDimensions();

  public int getWidth();

  public int getLength();
}
