package epicsquid.mysticallib.material;

import java.util.ArrayList;
import java.util.List;

public interface IMetalMaterial extends IMaterial {

	@Override
	default boolean isBlacklist() {
		return true;
	}

	@Override
	default List<String> getWhitelist() {
		List<String> result = new ArrayList<>();
		result.add("");
		return result;
	}
}
