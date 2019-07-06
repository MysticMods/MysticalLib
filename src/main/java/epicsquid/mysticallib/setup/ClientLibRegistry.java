package epicsquid.mysticallib.setup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.block.INoCullBlock;
import epicsquid.mysticallib.event.RegisterCustomModelsEvent;
import epicsquid.mysticallib.event.RegisterParticleEvent;
import epicsquid.mysticallib.model.BakedModelColorWrapper;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelBlockUnlitWrapper;
import epicsquid.mysticallib.particle.ParticleRegistry;
import epicsquid.mysticallib.particle.particles.ParticleFlame;
import epicsquid.mysticallib.particle.particles.ParticleGlitter;
import epicsquid.mysticallib.particle.particles.ParticleGlow;
import epicsquid.mysticallib.particle.particles.ParticleLeafArc;
import epicsquid.mysticallib.particle.particles.ParticleSmoke;
import epicsquid.mysticallib.particle.particles.ParticleSpark;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientLibRegistry {

  private static Map<Class<? extends Entity>, IRenderFactory> entityRenderMap = new HashMap<>();
  private static Map<Class<? extends TileEntity>, TileEntityRenderer> tileEntityRenderMap = new HashMap<>();

  public static void registerEntityRenderer(@Nonnull Class<? extends Entity> entity, @Nonnull IRenderFactory render) {
    entityRenderMap.put(entity, render);
  }

  public static void registerTileRenderer(@Nonnull Class<? extends TileEntity> entity, @Nonnull TileEntityRenderer render) {
    tileEntityRenderMap.put(entity, render);
  }

  private static Set<ModelResourceLocation> noCullMRLs = new HashSet<>();
  private static Set<ModelResourceLocation> colorMRLs = new HashSet<>();

  @SubscribeEvent
  public void registerModels(@Nonnull ModelRegistryEvent event) {
//    for (Item i : items) {
//      if (i instanceof IModeledObject) {
//        ((IModeledObject) i).initModel();
//      }
//    }
//    for (Block b : blocks) {
//      if (b instanceof IModeledObject) {
//        ((IModeledObject) b).initModel();
//      }
//    }
  }

  /**
   * Call in preInit to register Entity and TE rendering
   */
  public static void registerEntityRenders() {
    for (Map.Entry<Class<? extends Entity>, IRenderFactory> e : entityRenderMap.entrySet()) {
      RenderingRegistry.registerEntityRenderingHandler(e.getKey(), e.getValue());
    }
    for (Map.Entry<Class<? extends TileEntity>, TileEntityRenderer> e : tileEntityRenderMap.entrySet()) {
      ClientRegistry.bindTileEntitySpecialRenderer(e.getKey(), e.getValue());
    }
  }

  @SubscribeEvent
  public void onRegisterCustomModels(@Nonnull RegisterCustomModelsEvent event) {
    for (Block b : blocks) {
      if (b instanceof ICustomModeledObject) {
        ((ICustomModeledObject) b).initCustomModel();
      }
    }
    for (Item i : items) {
      if (i instanceof ICustomModeledObject) {
        ((ICustomModeledObject) i).initCustomModel();
      }
    }
  }

  @SubscribeEvent
  public void onTextureStitch(@Nonnull TextureStitchEvent event) {
    for (Map.Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
      event.getMap().registerSprite(e.getValue());
    }
    for (Map.Entry<ResourceLocation, IModel> e : CustomModelLoader.itemmodels.entrySet()) {
      for (ResourceLocation r : e.getValue().getTextures()) {
        event.getMap().registerSprite(r);
      }
    }
  }

  public class RegisterTintedModelsEvent extends Event {
    public void addModel(ModelResourceLocation mrl) {
      colorMRLs.add(mrl);
    }
  }

  @SubscribeEvent
  public void onModelBake(@Nonnull ModelBakeEvent event) {
    MinecraftForge.EVENT_BUS.post(new RegisterTintedModelsEvent());
    noCullMRLs.clear();
    for (Block b : blocks) {
      if (b instanceof IModeledObject) {
        if (b instanceof INoCullBlock && ((INoCullBlock) b).noCull()) {
          noCullMRLs.addAll(event.getModelManager().getBlockModelShapes().getBlockStateMapper().getVariants(b).values());
        }
      }
    }

    for (ResourceLocation r : CustomModelLoader.itemmodels.keySet()) {
      ModelResourceLocation mrl = new ModelResourceLocation(r.toString().replace("#handlers", ""), "handlers");
      IBakedModel bakedModel = event.getModelRegistry().get(mrl);
      if (bakedModel != null) {
        IModel m = CustomModelLoader.itemmodels.get(r);
        event.getModelRegistry().put(mrl, m.bake(m.getDefaultState(), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter()));
      }
    }

    for (ResourceLocation mrl : event.getModelRegistry().keySet()) {
      if (noCullMRLs.contains(mrl)) {
        event.getModelRegistry().put(mrl, new BakedModelBlockUnlitWrapper(event.getModelManager().getModel(mrl)));
      }
      if (colorMRLs.contains(mrl)) {
        event.getModelRegistry().put(mrl, new BakedModelColorWrapper(event.getModelManager().getModel(mrl)));
      }
    }
  }

  public static String PARTICLE_GLOW, PARTICLE_SMOKE, PARTICLE_SPARK, PARTICLE_GLITTER, PARTICLE_FLAME, PARTICLE_LEAF;

  @SubscribeEvent
  public void onRegisterParticles(@Nonnull RegisterParticleEvent event) {
    PARTICLE_GLOW = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleGlow.class, new ResourceLocation("mysticallib:particle/particle_glow"));
    PARTICLE_SMOKE = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleSmoke.class, new ResourceLocation("mysticallib:particle/particle_smoke"));
    PARTICLE_SPARK = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleSpark.class, new ResourceLocation("mysticallib:particle/particle_sparkle"));
    PARTICLE_GLITTER = ParticleRegistry
        .registerParticle(MysticalLib.MODID, ParticleGlitter.class, new ResourceLocation("mysticallib:particle/particle_sparkle"));
    PARTICLE_FLAME = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleFlame.class, new ResourceLocation("mysticallib:particle/particle_fire"));
    PARTICLE_LEAF = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleLeafArc.class, new ResourceLocation("mysticallib:particle/particle_leaf1"));
  }
}
