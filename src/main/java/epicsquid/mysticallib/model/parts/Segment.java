package epicsquid.mysticallib.model.parts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;

public class Segment {
  public Map<EnumFacing, BakedQuad> quads = new HashMap<>();

  public Segment(BakedQuad west, BakedQuad east, BakedQuad down, BakedQuad up, BakedQuad north, BakedQuad south, boolean[] cull) {
    if (cull[0])
      quads.put(EnumFacing.WEST, west);
    if (cull[1])
      quads.put(EnumFacing.EAST, east);
    if (cull[2])
      quads.put(EnumFacing.DOWN, down);
    if (cull[3])
      quads.put(EnumFacing.UP, up);
    if (cull[4])
      quads.put(EnumFacing.NORTH, north);
    if (cull[5])
      quads.put(EnumFacing.SOUTH, south);
  }

  public void addToList(List<BakedQuad> list, EnumFacing face) {
    if (face != null && quads.containsKey(face)) {
      list.add(quads.get(face));
    }
  }
}
