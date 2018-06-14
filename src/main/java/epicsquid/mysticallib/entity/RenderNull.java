package epicsquid.mysticallib.entity;

import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNull extends RenderEntity {
  public RenderNull(RenderManager renderManager) {
    super(renderManager);
  }

  @Override
  public void doRender(Entity entity, double x, double y, double z, float yaw, float pTicks) {
    //
  }

  @Override
  public void doRenderShadowAndFire(Entity entity, double x, double y, double z, float yaw, float pTicks) {
    //
  }

  public static class Factory implements IRenderFactory {
    @Override
    public RenderEntity createRenderFor(RenderManager manager) {
      return new RenderNull(manager);
    }
  }
}
