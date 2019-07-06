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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LibEvents {

  private static Map<Class<? extends Entity>, IRenderFactory> entityRenderMap = new HashMap<Class<? extends Entity>, IRenderFactory>();
  private static Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> tileEntityRenderMap = new HashMap<Class<? extends TileEntity>, TileEntitySpecialRenderer>();

  public static boolean acceptUpdates = true;

  public static Map<BlockPos, TileEntity> toUpdate = new HashMap<>();
  public static Map<BlockPos, TileEntity> overflow = new HashMap<>();

  public static int ticks = 0;

  @SubscribeEvent
  public void onTextureStitchPre(TextureStitchEvent.Pre event) {
//    FluidTextureUtil.initTextures(event.getMap());
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
      ClientProxy.particleRenderer.renderParticles(MysticalLib.proxy.getClientPlayer(), event.getPartialTicks());
      GlStateManager.popMatrix();
      if (MysticalLib.proxy.getClientWorld() != null) {
        List<TileEntity> list = Minecraft.getMinecraft().world.loadedTileEntityList;
        GlStateManager.pushMatrix();
        for (int i = 0; i < list.size(); i++) {
          TileEntitySpecialRenderer render = TileEntityRendererDispatcher.instance.getRenderer(list.get(i));
          if (render instanceof IDelayedTileRenderer) {
            double x = Minecraft.getMinecraft().player.lastTickPosX + Minecraft.getMinecraft().getRenderPartialTicks() * (Minecraft.getMinecraft().player.posX
                - Minecraft.getMinecraft().player.lastTickPosX);
            double y = Minecraft.getMinecraft().player.lastTickPosY + Minecraft.getMinecraft().getRenderPartialTicks() * (Minecraft.getMinecraft().player.posY
                - Minecraft.getMinecraft().player.lastTickPosY);
            double z = Minecraft.getMinecraft().player.lastTickPosZ + Minecraft.getMinecraft().getRenderPartialTicks() * (Minecraft.getMinecraft().player.posZ
                - Minecraft.getMinecraft().player.lastTickPosZ);
            GlStateManager.translate(-x, -y, -z);
            ((IDelayedTileRenderer) render).renderLater(list.get(i), list.get(i).getPos().getX(), list.get(i).getPos().getY(), list.get(i).getPos().getZ(),
                Minecraft.getMinecraft().getRenderPartialTicks());
            GlStateManager.translate(x, y, z);
          }
        }
        GlStateManager.popMatrix();
        List<Entity> entityList = Minecraft.getMinecraft().world.loadedEntityList;
        GlStateManager.pushMatrix();
        for (int i = 0; i < entityList.size(); i++) {
          Render render = Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entityList.get(i).getClass());
          if (render instanceof IDelayedEntityRenderer) {
            renderEntityStatic(entityList.get(i), Minecraft.getMinecraft().getRenderPartialTicks(), true, render);
          }
        }
        GlStateManager.popMatrix();
      }
    }
  }

  public static void renderEntityStatic(@Nonnull Entity entityIn, float partialTicks, boolean b, @Nonnull Render render) {
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
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    ((IDelayedEntityRenderer) render).renderLater(entityIn, -TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY,
        -TileEntityRendererDispatcher.staticPlayerZ, f, partialTicks);
  }
}
