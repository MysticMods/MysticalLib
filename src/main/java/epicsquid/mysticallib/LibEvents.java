package epicsquid.mysticallib;

import com.mojang.blaze3d.platform.GlStateManager;
import epicsquid.mysticallib.particle.ParticleRegistry;
import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.tile.IDelayedTileRenderer;
import epicsquid.mysticallib.util.FluidTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LibEvents {

	private static Map<Class<? extends Entity>, IRenderFactory> entityRenderMap = new HashMap<>();
	private static Map<Class<? extends TileEntity>, TileEntityRenderer> tileEntityRenderMap = new HashMap<>();

	public static boolean acceptUpdates = true;

	public static Map<BlockPos, TileEntity> toUpdate = new HashMap<>();
	public static Map<BlockPos, TileEntity> overflow = new HashMap<>();

	public static int ticks = 0;

	@SubscribeEvent
	public void onTextureStitchPre(TextureStitchEvent.Pre event) {
		FluidTextureUtil.initTextures(event.getMap());
	}

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre event) {
		for (Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
			event.addSprite(e.getValue());
		}
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			ticks++;
			ClientProxy.particleRenderer.updateParticles();
		}
	}

	@SubscribeEvent
	public void onRenderAfterWorld(RenderWorldLastEvent event) {
		if (MysticalLib.proxy instanceof ClientProxy) {
			GlStateManager.pushMatrix();
			ClientProxy.particleRenderer.renderParticles(event.getPartialTicks());
			GlStateManager.popMatrix();
			if (MysticalLib.proxy.getClientWorld() != null) {
				List<TileEntity> list = MysticalLib.proxy.getClientWorld().loadedTileEntityList;
				GlStateManager.pushMatrix();
				for (TileEntity te : list) {
					TileEntityRenderer render = TileEntityRendererDispatcher.instance.getRenderer(te);
					if (render instanceof IDelayedTileRenderer) {
						PlayerEntity player = MysticalLib.proxy.getClientPlayer();
						double x = player.lastTickPosX + Minecraft.getInstance().getRenderPartialTicks() * (Minecraft.getInstance().player.posX - player.lastTickPosX);
						double y = player.lastTickPosY + Minecraft.getInstance().getRenderPartialTicks() * (Minecraft.getInstance().player.posY - player.lastTickPosY);
						double z = player.lastTickPosZ + Minecraft.getInstance().getRenderPartialTicks() * (Minecraft.getInstance().player.posZ - player.lastTickPosZ);
						GlStateManager.translated(-x, -y, -z);
						((IDelayedTileRenderer) render).renderLater(te, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(),
										Minecraft.getInstance().getRenderPartialTicks());
						GlStateManager.translated(x, y, z);
					}
				}
				GlStateManager.popMatrix();
			}
		}
	}
}
