package epicsquid.mysticallib.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy implements IProxy {

  @Override
  public void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
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
