package epicsquid.mysticallib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.platform.GlStateManager;

import epicsquid.mysticallib.entity.IDelayedEntityRenderer;
import epicsquid.mysticallib.particle.ParticleRegistry;
import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.tile.IDelayedTileRenderer;
import epicsquid.mysticallib.util.FluidTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
  public void onTextureStitch(TextureStitchEvent event) {
    for (Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
      event.getMap().registerSprite(e.getValue());
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
      event.getContext(ClientProxy.particleRenderer.renderParticles(event.getPartialTicks()); GlStateManager.popMatrix();
      if (MysticalLib.proxy.getClientWorld() != null) {
        List<TileEntity> list = MysticalLib.proxy.getClientWorld().loadedTileEntityList;
        GlStateManager.pushMatrix();
        for (int i = 0; i < list.size(); i++) {
          TileEntityRenderer render = TileEntityRendererDispatcher.instance.getRenderer(list.get(i));
          if (render instanceof IDelayedTileRenderer) {
            PlayerEntity player = MysticalLib.proxy.getClientPlayer();
            double x = player.lastTickPosX + Minecraft.getInstance().getRenderPartialTicks() * (Minecraft.getInstance().player.posX - player.lastTickPosX);
            double y = player.lastTickPosY + Minecraft.getInstance().getRenderPartialTicks() * (Minecraft.getInstance().player.posY - player.lastTickPosY);
            double z = player.lastTickPosZ + Minecraft.getInstance().getRenderPartialTicks() * (Minecraft.getInstance().player.posZ - player.lastTickPosZ);
            GlStateManager.translated(-x, -y, -z);
            ((IDelayedTileRenderer) render).renderLater(list.get(i), list.get(i).getPos().getX(), list.get(i).getPos().getY(), list.get(i).getPos().getZ(),
                Minecraft.getInstance().getRenderPartialTicks());
            GlStateManager.translated(x, y, z);
          }
        }
        GlStateManager.popMatrix();
        List<Entity> entityList = MysticalLib.proxy.getClientWorld().loadedEntityList;
        GlStateManager.pushMatrix();
        for (int i = 0; i < entityList.size(); i++) {
          EntityRenderer render = Minecraft.getInstance().getRenderManager().entityRenderMap.get(entityList.get(i).getClass());
          if (render instanceof IDelayedEntityRenderer) {
            renderEntityStatic(entityList.get(i), Minecraft.getInstance().getRenderPartialTicks(), true, render);
          }
        }
        GlStateManager.popMatrix();
      }
    }
  }

  public static void renderEntityStatic(@Nonnull Entity entityIn, float partialTicks, boolean b, @Nonnull EntityRenderer render) {
    if (entityIn.ticksExisted == 0) {
      entityIn.lastTickPosX = entityIn.posX;
      entityIn.lastTickPosY = entityIn.posY;
      entityIn.lastTickPosZ = entityIn.posZ;
    }

    double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
    double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
    double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;
    float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
    int i = entityIn.getBrightnessForRender();

    if (entityIn.isBurning()) {
      i = 15728880;
    }

    int j = i % 65536;
    int k = i / 65536;
    OpenGLHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
    GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    ((IDelayedEntityRenderer) render).renderLater(entityIn, -TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY,
        -TileEntityRendererDispatcher.staticPlayerZ, f, partialTicks);
  }
}
