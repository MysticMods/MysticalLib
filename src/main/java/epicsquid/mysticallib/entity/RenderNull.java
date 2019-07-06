package epicsquid.mysticallib.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Renders an empty entity
 */
public class RenderNull extends EntityRenderer {

  public RenderNull(@Nonnull EntityRendererManager renderManager) {
    super(renderManager);
  }

  @Override
  public void doRender(@Nonnull Entity entity, double x, double y, double z, float yaw, float pTicks) {
    //
  }

  @Override
  public void doRenderShadowAndFire(@Nonnull Entity entity, double x, double y, double z, float yaw, float pTicks) {
    //
  }

  public static class Factory implements IRenderFactory {

    @Override
    public EntityRenderer createRenderFor(EntityRendererManager manager) {
      return new RenderNull(manager);
    }
  }

  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(Entity entity) {
    return null;
  }
}
