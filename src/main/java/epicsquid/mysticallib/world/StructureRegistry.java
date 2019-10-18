package epicsquid.mysticallib.world;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class StructureRegistry {

  public static Map<String, IGeneratable> structures = new HashMap<>();

  @Nonnull
  public static String addStructure(@Nonnull String name, @Nonnull IGeneratable data) {
    structures.put(name, data);
    return name;
  }
}
