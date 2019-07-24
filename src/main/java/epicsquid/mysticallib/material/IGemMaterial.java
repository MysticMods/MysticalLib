package epicsquid.mysticallib.material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IGemMaterial extends IMaterial {

	@Override
	default boolean isBlacklist() {
		return true;
	}

	@Override
	default List<String> getWhitelist() {
		return Arrays.asList("ingot", "nugget", "dust");
	}
}
