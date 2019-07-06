package epicsquid.mysticallib.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {
  //  public static ParticleRenderer particleRenderer = new ParticleRenderer();

  //  @Override
  //  public void preInit(FMLPreInitializationEvent event) {
  //    super.preInit(event);
  //
  //    MinecraftForge.EVENT_BUS.register(new FXHandler());
  //
  //    try {
  //      Hax.init();
  //    } catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
  //      e.printStackTrace();
  //    }
  //
  //    LibRegistry.registerEntityRenders();
  //    OBJLoader.INSTANCE.addDomain(MysticalLib.MODID);
  //    MinecraftForge.EVENT_BUS.register(new EffectManager());
  //    ModelLoaderRegistry.registerLoader(new CustomModelLoader());
  //    MinecraftForge.EVENT_BUS.post(new RegisterParticleEvent());
  //  }
  //
  //  @Override
  //  public void init(FMLInitializationEvent event) {
  //    super.init(event);
  //    MinecraftForge.EVENT_BUS.post(new RegisterColorHandlersEvent());
  //  }

  @Override
  public void init() {

  }

  @Override
  public World getClientWorld() {
    return Minecraft.getInstance().world;
  }

  @Override
  public PlayerEntity getClientPlayer() {
    return Minecraft.getInstance().player;
  }
}
