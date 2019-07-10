package epicsquid.mysticallib.material;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class BaseItemTier implements IItemTier {

	private int maxUses;
	private float efficiency;
	private float attackDamage;
	private int harvestLevel;
	private int enchantability;
	private Supplier<Ingredient> repairMaterial;

	public BaseItemTier(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial) {
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.harvestLevel = harvestLevel;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getMaxUses() {
		return maxUses;
	}

	@Override
	public float getEfficiency() {
		return efficiency;
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	@Nonnull
	public Ingredient getRepairMaterial() {
		return repairMaterial.get();
	}
}
