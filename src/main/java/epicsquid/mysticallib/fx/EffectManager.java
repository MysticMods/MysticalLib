package epicsquid.mysticallib.fx;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EffectManager {
  public static ArrayList<Effect> effects = new ArrayList<>();
  public static ArrayList<Effect> toAdd = new ArrayList<>();

  public static void addEffect(Effect e) {
    toAdd.add(e);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onClientTick(ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
        for (int i = 0; i < effects.size(); i++) {
          if (!effects.get(i).dead) {
            effects.get(i).update();
          }
        }
      }
    }
    if (event.phase == TickEvent.Phase.END) {
      for (int i = 0; i < toAdd.size(); i++) {
        effects.add(toAdd.get(i));
      }
      toAdd.clear();
      for (int i = 0; i < effects.size(); i++) {
        if (effects.get(i).dead) {
          effects.remove(i);
          i = Math.max(0, i - 1);
        }
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRenderLast(RenderWorldLastEvent event) {
    for (int i = 0; i < effects.size(); i++) {
      effects.get(i).renderTotal(event.getPartialTicks());
    }
  }
}
