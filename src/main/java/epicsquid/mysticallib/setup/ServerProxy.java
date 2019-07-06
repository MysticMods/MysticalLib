package epicsquid.mysticallib.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {

  //  public void preInit(FMLPreInitializationEvent event) {
  //    LibRegistry.initAll();
  //    PacketHandler.registerMessages();
  //    MinecraftForge.EVENT_BUS.post(new RegisterFXEvent());
  //  }
  //
  //  public void init(FMLInitializationEvent event) {
  //    MinecraftForge.EVENT_BUS.post(new RegisterWorldGenEvent());
  //  }
  //

  @Override
  public void init() {

  }

  @Override
  public World getClientWorld() {
    throw new IllegalStateException("Only run this on the client.");
  }

  @Override
  public PlayerEntity getClientPlayer() {
    throw new IllegalStateException("Only run this on the client.");
  }
}
