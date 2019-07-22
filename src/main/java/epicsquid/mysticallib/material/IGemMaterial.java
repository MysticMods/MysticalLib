package epicsquid.mysticallib.material;

import java.util.ArrayList;
import java.util.List;

public interface IGemMaterial extends IMaterial {

	@Override
	default boolean isBlacklist() {
		return true;
	}

	@Override
	default List<String> getWhitelist() {
		List<String> result = new ArrayList<>();
		result.add("ingot");
		result.add("nugget");
		result.add("dust");
		return result;
	}
}
