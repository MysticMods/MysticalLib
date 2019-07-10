package epicsquid.mysticallib.material;

import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MaterialProperties {

	private Supplier<IItemTier> tier;
	private Supplier<IArmorMaterial> armor;
	private Supplier<Item.Properties> itemProps;
	private Map<String, Integer> attackDamageMap = new HashMap<>();
	private Map<String, Float> attackSpeedMap = new HashMap<>();
	private String name;

	public IItemTier getTier() {
		return tier.get();
	}

	public MaterialProperties setTier(Supplier<IItemTier> tier) {
		this.tier = tier;
		return this;
	}

	public IArmorMaterial getArmor() {
		return armor.get();
	}

	public MaterialProperties setArmor(Supplier<IArmorMaterial> armor) {
		this.armor = armor;
		return this;
	}

	public Item.Properties getItemProps() {
		return itemProps.get();
	}

	public MaterialProperties setItemProps(Supplier<Item.Properties> itemProps) {
		this.itemProps = itemProps;
		return this;
	}

	public String getName() {
		return name;
	}

	public MaterialProperties setName(String name) {
		this.name = name;
		return this;
	}

	public MaterialProperties setDamage(int amount) {
		attackDamageMap.putIfAbsent("DEFAULT", amount);
		return this;
	}

	public MaterialProperties setDamage(String type, int amount) {
		attackDamageMap.putIfAbsent(type, amount);
		return this;
	}

	public int getDamage() {
		return attackDamageMap.getOrDefault("DEFAULT", 1);
	}

	public int getDamage(String type) {
		return attackDamageMap.getOrDefault(type == null ? "DEFAULT" : type, 1);
	}

	public MaterialProperties setAttackSpeed(float amount) {
		attackSpeedMap.putIfAbsent("DEFAULT", amount);
		return this;
	}

	public MaterialProperties setAttackSpeed(String type, float amount) {
		attackSpeedMap.putIfAbsent(type, amount);
		return this;
	}

	public float getAttackSpeed() {
		return attackSpeedMap.getOrDefault("DEFAULT", 1.6f);
	}

	public float getAttackSpeed(String type) {
		return attackSpeedMap.getOrDefault(type == null ? "DEFAULT" : type, 1.6f);
	}
}
