package epicsquid.mysticallib;

import com.mojang.blaze3d.platform.GlStateManager;
import epicsquid.mysticallib.particle.ParticleRegistry;
import epicsquid.mysticallib.particle.ParticleRenderer;
import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.util.FluidTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LibEvents {

	public static int ticks = 0;

	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		FluidTextureUtil.initTextures(event.getMap());
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		for (Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
			event.addSprite(e.getValue());
		}
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			ticks++;
			ClientProxy.particleRenderer.updateParticles();
		}
	}

	@SubscribeEvent
	public void onRenderAfterWorld(RenderWorldLastEvent event) {
		GlStateManager.pushMatrix();
		ClientProxy.particleRenderer.renderParticles(event.getPartialTicks());
		GlStateManager.popMatrix();
	}
}
