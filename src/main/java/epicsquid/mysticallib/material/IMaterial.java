package epicsquid.mysticallib.material;

import epicsquid.mysticallib.block.OreBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;

import java.util.List;

public interface IMaterial {

	IItemTier getTier();

	IArmorMaterial getArmor();

	Item.Properties getItemProps();

	Block.Properties getBlockProps();

	OreBlockProperties getBlockOreProps();

	float getAttackSpeed(String name);

	float getAttackDamage(String name);

	String getName();

	List<String> getWhitelist();

	default boolean isBlacklist() {
		return true;
	}
}
