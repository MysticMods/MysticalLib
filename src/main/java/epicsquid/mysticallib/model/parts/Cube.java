package epicsquid.mysticallib.model.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;

public class Cube {
  public Map<EnumFacing, BakedQuad> quads = new HashMap<EnumFacing, BakedQuad>();
  public List<BakedQuad> unculledQuads = new ArrayList<>();

  public Cube(BakedQuad west, BakedQuad east, BakedQuad down, BakedQuad up, BakedQuad north, BakedQuad south) {
    quads.put(EnumFacing.WEST, west);
    quads.put(EnumFacing.EAST, east);
    quads.put(EnumFacing.DOWN, down);
    quads.put(EnumFacing.UP, up);
    quads.put(EnumFacing.NORTH, north);
    quads.put(EnumFacing.SOUTH, south);
  }

  public void addToList(List<BakedQuad> list, EnumFacing face) {
    if (face != null && quads.containsKey(face)) {
      list.add(quads.get(face));
    } else if (face == null) {
      list.addAll(unculledQuads);
    }
  }

  public Cube setNoCull() {
    unculledQuads.addAll(quads.values());
    quads.clear();
    return this;
  }

  public Cube setNoCull(EnumFacing face) {
    BakedQuad quad = quads.get(face);
    unculledQuads.add(quad);
    quads.remove(face);
    return this;
  }
}
