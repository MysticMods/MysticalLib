package epicsquid.mysticallib.material;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class MaterialType extends MaterialTypeBase<MaterialType> implements IArmorMaterial {
  public MaterialType(String name) {
    super(name);
    this.putDamageSpeed(Type.SWORD, 3.0f, -2.4f, Type.SHOVEL, 1.5f, -3.0f, Type.PICKAXE, 1.0f, -2.8f, Type.HOE, 1.0f, -1.0f);
  }

  public MaterialType armorMaterial(int maxDamageFactor, int[] damageReductionAmountArray, SoundEvent soundEvent, float toughness) {
    this.maxDamageFactor = maxDamageFactor;
    this.damageReductionAmountArray = damageReductionAmountArray;
    this.soundEvent = soundEvent;
    this.toughness = toughness;
    return this;
  }

  public MaterialType setArmorMaterial(IArmorMaterial material) {
    this.material = material;
    return this;
  }

  @Override
  public float getToughness() {
    return this.material == null ? toughness : this.material.getToughness();
  }

  @Override
  public int getDurability(EquipmentSlotType slotIn) {
    return material == null ? MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor : this.material.getDurability(slotIn);
  }

  @Override
  public int getDamageReductionAmount(EquipmentSlotType slotIn) {
    return material == null ? this.damageReductionAmountArray[slotIn.getIndex()] : this.material.getDamageReductionAmount(slotIn);
  }

  @Override
  public int getEnchantability() {
    return tier == null ? enchantability : tier.getEnchantability();
  }

  @Override
  public SoundEvent getSoundEvent() {
    return material == null ? soundEvent : this.material.getSoundEvent();
  }

  @Override
  @Nonnull
  public Ingredient getRepairMaterial() {
    return tier == null ? repairMaterial.get() : tier.getRepairMaterial();
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public String getName() {
    return getModId() + ":" + getInternalName();
  }

  // IArmorMaterial overrides
  public int func_200900_a() {
    return material == null ? enchantability : material.getEnchantability();
  }

  public Ingredient func_200898_c() {
    return material == null ? repairMaterial.get() : material.getRepairMaterial();
  }
}
