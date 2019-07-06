package epicsquid.mysticallib.fx;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.setup.ClientProxy;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FXRegistry {
	public static Map<Integer, Function<CompoundNBT, Void>> effects = new HashMap<>();

	private static int id = 0;

	public static int registerEffect(@Nonnull Function<CompoundNBT, Void> func) {
		if (MysticalLib.proxy instanceof ClientProxy) {
			effects.put(id, func);
		}
		return id++;
	}
}
