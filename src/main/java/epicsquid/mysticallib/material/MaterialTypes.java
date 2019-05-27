package epicsquid.mysticallib.material;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class MaterialTypes {
  public static Map<String, Item.ToolMaterial> materialMap = new HashMap<>();
  public static Map<String, KnifeStats> statsMap = new HashMap<>();
  public static Map<Item.ToolMaterial, KnifeStats> materialToStatsMap = new HashMap<>();

  public static void addMaterial (String name, Item.ToolMaterial material, float damage, float speed) {
    addMaterial(name, material, new KnifeStats(damage, speed));
  }

  public static void addMaterial (String name, Item.ToolMaterial material, KnifeStats stats) {
    materialToStatsMap.put(material, stats);
    statsMap.put(name, stats);
    materialMap.put(name, material);
  }

  public static Item.ToolMaterial material (String name) {
    return materialMap.get(name);
  }

  public static KnifeStats stats (String name) {
    return statsMap.get(name);
  }

  public static KnifeStats stats (Item.ToolMaterial tool) {
    return materialToStatsMap.get(tool);
  }

  public static class KnifeStats {
    public float damage;
    public float speed;

    public KnifeStats(float damage, float speed) {
      this.damage = damage;
      this.speed = speed;
    }
  }
}
