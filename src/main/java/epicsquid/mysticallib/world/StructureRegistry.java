package epicsquid.mysticallib.world;

import java.util.HashMap;
import java.util.Map;

public class StructureRegistry {
  public static Map<String, IGeneratable> structures = new HashMap<>();

  public static String addStructure(String name, IGeneratable data) {
    structures.put(name, data);
    return name;
  }
}
