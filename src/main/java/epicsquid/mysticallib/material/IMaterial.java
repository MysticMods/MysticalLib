package epicsquid.mysticallib.material;

import epicsquid.mysticallib.block.OreBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public interface IMaterial {

	default void setOre (Block ore) {
	}

	@Nullable
	default Block getOre () {
		return null;
	}

	default boolean hasOre () {
		return getOre() != null;
	}

	IItemTier getTier();

	IArmorMaterial getArmor();

	Item.Properties getItemProps();

	Block.Properties getBlockProps();

	OreBlockProperties getBlockOreProps();

	float getAttackSpeed(String name);

	float getAttackDamage(String name);

	String getName();

	Predicate<IMaterialFactory<?>> matches();

	default int getDurability() {
		return 0;
	}

	// This is a necessary evil... thanks stairs
	default BlockState getDecoBlockstate() {
		return null;
	}
}
