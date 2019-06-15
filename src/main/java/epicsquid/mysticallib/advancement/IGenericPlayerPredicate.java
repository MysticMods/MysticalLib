package epicsquid.mysticallib.advancement;

import com.google.gson.JsonElement;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;

public interface IGenericPlayerPredicate {
    boolean test(EntityPlayerMP player);

    IGenericPlayerPredicate deserialize(@Nullable JsonElement element);
}
