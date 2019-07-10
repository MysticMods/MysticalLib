package epicsquid.mysticallib.material;

import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;

import java.util.Map;
import java.util.function.Supplier;

public class MaterialProperties {

	private Supplier<IItemTier> tier;
	private Supplier<IArmorMaterial> armor;
	private Supplier<Item.Properties> itemProps;
	private Map<String, Supplier<Integer>> attackDamageMap;
	private Map<String, Supplier<Float>> attackSpeedMap;
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

	public MaterialProperties setDamage(Supplier<Integer> amount) {
		attackDamageMap.putIfAbsent("DEFAULT", amount);
		return this;
	}

	public MaterialProperties setDamage(String type, Supplier<Integer> amount) {
		attackDamageMap.putIfAbsent(type, amount);
		return this;
	}

	public int getDamage() {
		return attackDamageMap.getOrDefault("DEFAULT", () -> 1).get();
	}

	public int getDamage(String type) {
		return attackDamageMap.getOrDefault(type == null ? "DEFAULT" : type, () -> 1).get();
	}

	public MaterialProperties setAttackSpeed(Supplier<Float> amount) {
		attackSpeedMap.putIfAbsent("DEFAULT", amount);
		return this;
	}

	public MaterialProperties setAttackSpeed(String type, Supplier<Float> amount) {
		attackSpeedMap.putIfAbsent(type, amount);
		return this;
	}

	public float getAttackSpeed() {
		return attackSpeedMap.getOrDefault("DEFAULT", () -> 1.6f).get();
	}

	public float getAttackSpeed(String type) {
		return attackSpeedMap.getOrDefault(type == null ? "DEFAULT" : type, () -> 1.6f).get();
	}
}
