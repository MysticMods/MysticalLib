package epicsquid.mysticallib.material;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class MaterialTypeBase<T extends MaterialTypeBase> implements IItemTier, IArmorMaterial {
  public static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
  public static final UUID MAIN_HAND_MODIFIER = UUID.fromString("0e2c39ce-5247-4095-abf7-d99bd7387a95");
  public static final UUID OFF_HAND_MODIFIER = UUID.fromString("28ad5d13-618f-4c80-8f60-0e0469c1a046");

  protected static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

  protected String name;
  protected IItemTier tier = null;
  protected IArmorMaterial material = null;
  protected int maxUses;
  protected float efficiency;
  protected float attackDamage;
  protected int harvestLevel;
  protected int enchantability;
  protected Supplier<Ingredient> repairMaterial;

  protected int maxDamageFactor;
  protected int[] damageReductionAmountArray;
  protected SoundEvent soundEvent;
  protected float toughness;

  protected Supplier<Supplier<? extends Item>> item;
  protected Supplier<Supplier<? extends Item>> dust;
  protected Supplier<Supplier<? extends Item>> nugget;
  protected Supplier<Supplier<? extends Block>> block;
  protected Supplier<Supplier<? extends Block>> ore;

  protected int maxXP = 0;
  protected int minXP = 0;

  protected String modId;

  protected Object2FloatOpenHashMap<Type> damage = new Object2FloatOpenHashMap<>();
  protected Object2FloatOpenHashMap<Type> speed = new Object2FloatOpenHashMap<>();

  protected List<Type> itemTypes;

  public MaterialTypeBase(String name) {
    this.name = name;
  }

  public T setModId(String modId) {
    this.modId = modId;
    return (T) this;
  }

  public String getModId() {
    return modId;
  }

  public T itemMaterial(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability) {
    this.maxUses = maxUses;
    this.efficiency = efficiency;
    this.attackDamage = attackDamage;
    this.harvestLevel = harvestLevel;
    this.enchantability = enchantability;
    this.repairMaterial = () -> Ingredient.fromItems(item.get().get());
    return (T) this;
  }

  public T itemMaterial(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial) {
    return itemMaterial(maxUses, efficiency, attackDamage, harvestLevel, enchantability);
  }

  public T setItemTier(IItemTier tier) {
    this.tier = tier;
    return (T) this;
  }

  public int getMaxXP() {
    return maxXP;
  }

  public T setMaxXP(int maxXP) {
    this.maxXP = maxXP;
    return (T) this;
  }

  public int getMinXP() {
    return minXP;
  }

  public T setMinXP(int minXP) {
    this.minXP = minXP;
    return (T) this;
  }

  public T putDamageSpeed(Object... entries) {
    if (entries.length % 3 != 0) {
      throw new IllegalArgumentException("Invalid number of arguments to putDamageSpeed");
    }

    for (int i = 0; i < entries.length; i += 3) {
      Type type = (Type) entries[i];
      float damage = (float) entries[i + 1];
      float speed = (float) entries[i + 2];

      this.damage.put(type, damage);
      this.speed.put(type, speed);
    }

    return (T) this;
  }

  public int getDamage(Type type) {
    return (int) damage.getOrDefault(type, 1.0f);
  }

  public int getDamage(String type) {
    return getDamage(Type.byName(type));
  }

  public float getSpeed(Type type) {
    return speed.getOrDefault(type, -1.0f);
  }

  public float getSpeed(String type) {
    return getSpeed(Type.byName(type));
  }

  public List<Type> getItemTypes() {
    return itemTypes;
  }

  public T setItemTypes(Type... types) {
    this.itemTypes = Arrays.asList(types);
    return (T) this;
  }

  public String getInternalName() {
    return name;
  }

  public Supplier<? extends Item> getItem() {
    return item.get();
  }

  public Supplier<? extends Item> getDust() {
    return dust.get();
  }

  public Supplier<? extends Item> getNugget() {
    return nugget.get();
  }

  public Supplier<? extends Block> getBlock() {
    return block.get();
  }

  public Supplier<? extends Block> getOre() {
    return ore.get();
  }

  public T item(Supplier<Supplier<? extends Item>> ingot) {
    this.item = ingot;
    return (T) this;
  }

  public T dust(Supplier<Supplier<? extends Item>> dust) {
    this.dust = dust;
    return (T) this;
  }

  public T nugget(Supplier<Supplier<? extends Item>> nugget) {
    this.nugget = nugget;
    return (T) this;
  }

  public T block(Supplier<Supplier<? extends Block>> block) {
    this.block = block;
    return (T) this;
  }

  public T ore(Supplier<Supplier<? extends Block>> ore) {
    this.ore = ore;
    return (T) this;
  }

  public Supplier<Block.Properties> getBlockProps() {
    return () -> Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1);
  }

  public Supplier<Block.Properties> getOreBlockProperties() {
    return () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(getHarvestLevel());
  }

  public String gemName() {
    return name + "_gem";
  }

  public String ingotName() {
    return name + "_ingot";
  }

  public String dustName() {
    return name + "_dust";
  }

  public String blockName() {
    return name + "_block";
  }

  public String oreName() {
    return name + "_ore";
  }

  public String nuggetName() {
    return name + "_nugget";
  }

  /* Implementation of Material and Armor Material interfaces:
   *   Note: the get-repair-item method inherently goes with the
   *         item supplied by the item tier.                      */

  @Override
  public int getMaxUses() {
    return tier == null ? maxUses : tier.getMaxUses();
  }

  @Override
  public float getEfficiency() {
    return tier == null ? efficiency : tier.getEfficiency();
  }

  @Override
  public float getAttackDamage() {
    return tier == null ? attackDamage : tier.getAttackDamage();
  }

  @Override
  public int getHarvestLevel() {
    return tier == null ? harvestLevel : tier.getHarvestLevel();
  }

  @Override
  public int getEnchantability() {
    return tier == null ? enchantability : tier.getEnchantability();
  }

  @Override
  @Nonnull
  public Ingredient getRepairMaterial() {
    return tier == null ? repairMaterial.get() : tier.getRepairMaterial();
  }

  public enum Type {
    SWORD("sword"),
    KNIFE("knife"),
    PICKAXE("pickaxe"),
    AXE("axe"),
    SHOVEL("shovel"),
    HOE("hoe"),
    SPEAR("spear");

    private final String name;

    Type(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Nullable
    public static Type byName(String name) {
      for (Type i : values()) {
        if (i.getName().equals(name)) {
          return i;
        }
      }

      return null;
    }
  }
}
