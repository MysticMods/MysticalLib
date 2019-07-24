package epicsquid.mysticallib.material;

import java.util.Collections;
import java.util.List;

public interface IMetalMaterial extends IMaterial {

	@Override
	default boolean isBlacklist() {
		return true;
	}

	@Override
	default List<String> getWhitelist() {
		return Collections.singletonList("");
	}
}
