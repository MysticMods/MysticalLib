package epicsquid.mysticallib.entity;

import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public interface IDelayedEntityRenderer {

  void renderLater(@Nonnull Entity entity, double dx, double dy, double dz, float yaw, float pTicks);
}
