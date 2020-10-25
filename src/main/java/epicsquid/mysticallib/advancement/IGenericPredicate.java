package epicsquid.mysticallib.advancement;

import com.google.gson.JsonElement;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;

public interface IGenericPredicate<T> {
  boolean test(EntityPlayerMP player, T condition);

  IGenericPredicate<T> deserialize(@Nullable JsonElement element);
}
