package epicsquid.mysticallib.model.parts;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Cube {

  public Map<Direction, BakedQuad> quads = new EnumMap<>(Direction.class);
  public List<BakedQuad> unculledQuads = new ArrayList<>();

  public Cube(@Nonnull BakedQuad west, @Nonnull BakedQuad east, @Nonnull BakedQuad down, @Nonnull BakedQuad up, @Nonnull BakedQuad north,
              @Nonnull BakedQuad south) {
    quads.put(Direction.WEST, west);
    quads.put(Direction.EAST, east);
    quads.put(Direction.DOWN, down);
    quads.put(Direction.UP, up);
    quads.put(Direction.NORTH, north);
    quads.put(Direction.SOUTH, south);
  }

  public void addToList(@Nonnull List<BakedQuad> list, @Nullable Direction face) {
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

  public Cube setNoCull(@Nonnull Direction face) {
    BakedQuad quad = quads.get(face);
    unculledQuads.add(quad);
    quads.remove(face);
    return this;
  }
}
