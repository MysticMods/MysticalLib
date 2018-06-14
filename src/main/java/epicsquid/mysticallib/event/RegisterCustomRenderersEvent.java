package epicsquid.mysticallib.event;

import epicsquid.mysticallib.LibRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RegisterCustomRenderersEvent {

  public RegisterCustomRenderersEvent() {

  }

  public void addTileRender(Class<? extends TileEntity> c, TileEntitySpecialRenderer r) {
    LibRegistry.registerTileRenderer(c, r);
  }

  public void addEntityender(Class<? extends Entity> c, IRenderFactory r) {
    LibRegistry.registerEntityRenderer(c, r);
  }
}
