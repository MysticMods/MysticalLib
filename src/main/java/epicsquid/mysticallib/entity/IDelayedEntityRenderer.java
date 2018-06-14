package epicsquid.mysticallib.entity;

import net.minecraft.entity.Entity;

public interface IDelayedEntityRenderer {
  public void renderLater(Entity entity, double dx, double dy, double dz, float yaw, float pTicks);
}
