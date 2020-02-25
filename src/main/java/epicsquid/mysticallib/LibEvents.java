package epicsquid.mysticallib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.entity.IDelayedEntityRenderer;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.particle.ParticleBase;
import epicsquid.mysticallib.particle.ParticleRegistry;
import epicsquid.mysticallib.proxy.ClientProxy;
import epicsquid.mysticallib.tile.IDelayedTileRenderer;
import epicsquid.mysticallib.util.FluidTextureUtil;
import epicsquid.mysticallib.world.GenerationData;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
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
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LibEvents {

  public static boolean acceptUpdates = true;

  public static Map<BlockPos, TileEntity> toUpdate = new HashMap<BlockPos, TileEntity>();
  public static Map<BlockPos, TileEntity> overflow = new HashMap<BlockPos, TileEntity>();

  public static int ticks = 0;

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitchPre(TextureStitchEvent.Pre event) {
    FluidTextureUtil.initTextures(event.getMap());
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent event) {
    for (Entry<Class<? extends ParticleBase>, List<ResourceLocation>> e : ParticleRegistry.particleMultiTextures.entrySet()) {
      e.getValue().forEach(event.getMap()::registerSprite);
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      ticks++;
      ClientProxy.particleRenderer.updateParticles();
    }
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRenderAfterWorld(RenderWorldLastEvent event) {
    if (MysticalLib.proxy instanceof ClientProxy) {
      GlStateManager.pushMatrix();
      ClientProxy.particleRenderer.renderParticles(Minecraft.getMinecraft() != null ? Minecraft.getMinecraft().player : null, event.getPartialTicks());
      GlStateManager.popMatrix();
      if (Minecraft.getMinecraft().world != null) {
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

  @SideOnly(Side.CLIENT)
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
