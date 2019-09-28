package epicsquid.mysticallib.fx;

import epicsquid.mysticallib.MysticalLib;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EffectManager {
	public static ArrayList<Effect> effects = new ArrayList<>();
	public static ArrayList<Effect> toAdd = new ArrayList<>();

	public static void addEffect(Effect e) {
		toAdd.add(e);
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			if (MysticalLib.proxy.getClientPlayer() != null && MysticalLib.proxy.getClientWorld() != null) {
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

	@SubscribeEvent
	public void onRenderLast(RenderWorldLastEvent event) {
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).renderTotal(event.getPartialTicks());
		}
	}
}
