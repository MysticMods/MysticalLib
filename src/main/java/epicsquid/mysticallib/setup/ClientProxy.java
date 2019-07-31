package epicsquid.mysticallib.setup;

import epicsquid.mysticallib.event.RegisterParticleEvent;
import epicsquid.mysticallib.particle.ParticleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy implements IProxy {

	public static ParticleRenderer particleRenderer = new ParticleRenderer();

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
		FMLJavaModLoadingContext.get().getModEventBus().post(new RegisterParticleEvent());
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Override
	public ActiveRenderInfo getClientActiveRenderInfo() {
		return Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
	}
}
