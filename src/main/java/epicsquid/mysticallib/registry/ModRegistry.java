package epicsquid.mysticallib.registry;

import epicsquid.mysticallib.block.*;
import epicsquid.mysticallib.item.KnifeItem;
import epicsquid.mysticallib.material.MaterialType;
import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static epicsquid.mysticallib.material.MaterialType.Type;

public class ModRegistry {
  private final DeferredRegister<Block> blockRegistry;
  private final DeferredRegister<Item> itemRegistry;
  private final DeferredRegister<Fluid> fluidRegistry;
  private final DeferredRegister<Biome> biomeRegistry;
  private final DeferredRegister<SoundEvent> soundRegistry;
  private final DeferredRegister<Enchantment> enchantmentRegistry;
  private final DeferredRegister<Potion> potionRegistry;
  private final DeferredRegister<Effect> effectRegistry;

  private final DeferredRegister<EntityType<?>> entityRegistry;

  private final DeferredRegister<TileEntityType<?>> tileEntityRegistry;
  private final DeferredRegister<ContainerType<?>> containerRegistry;

  private final DeferredRegister<IRecipeSerializer<?>> recipeRegistry;
  private final DeferredRegister<StatType<?>> statRegistry;

  private final DeferredRegister<ParticleType<?>> particleRegistry;
  private final DeferredRegister<PaintingType> paintingRegistry;

  private final DeferredRegister<VillagerProfession> professionRegistry;
  private final DeferredRegister<PointOfInterestType> poiRegistry;
  private final DeferredRegister<MemoryModuleType<?>> memoryRegistry;
  private final DeferredRegister<SensorType<?>> sensorRegistry;
  private final DeferredRegister<Schedule> scheduleRegistry;
  private final DeferredRegister<Activity> activityRegistry;

  private final DeferredRegister<WorldCarver<?>> carverRegistry;
  private final DeferredRegister<SurfaceBuilder<?>> surfaceRegistry;
  private final DeferredRegister<Feature<?>> featureRegistry;
  private final DeferredRegister<Placement<?>> placementRegistry;
  private final DeferredRegister<BiomeProviderType<?, ?>> biomeProviderRegistry;
  private final DeferredRegister<ChunkGeneratorType<?, ?>> chunkGeneratorRegistry;
  private final DeferredRegister<ChunkStatus> chunkStatusRegistry;

  private final DeferredRegister<ModDimension> dimensionRegistry;
  private final DeferredRegister<DataSerializerEntry> dataSerializerRegistry;

  private final String modId;

  private final Set<DeferredRegister<?>> activeRegistries = new HashSet<>();

  public ModRegistry(String modId) {
    this.modId = modId;

    this.blockRegistry = new DeferredRegister<>(ForgeRegistries.BLOCKS, modId);
    this.itemRegistry = new DeferredRegister<>(ForgeRegistries.ITEMS, modId);
    this.fluidRegistry = new DeferredRegister<>(ForgeRegistries.FLUIDS, modId);
    this.biomeRegistry = new DeferredRegister<>(ForgeRegistries.BIOMES, modId);
    this.soundRegistry = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, modId);
    this.enchantmentRegistry = new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, modId);
    this.potionRegistry = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, modId);
    this.effectRegistry = new DeferredRegister<>(ForgeRegistries.POTIONS, modId);
    this.entityRegistry = new DeferredRegister<>(ForgeRegistries.ENTITIES, modId);
    this.tileEntityRegistry = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, modId);
    this.containerRegistry = new DeferredRegister<>(ForgeRegistries.CONTAINERS, modId);
    this.recipeRegistry = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, modId);
    this.statRegistry = new DeferredRegister<>(ForgeRegistries.STAT_TYPES, modId);
    this.particleRegistry = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, modId);
    this.paintingRegistry = new DeferredRegister<>(ForgeRegistries.PAINTING_TYPES, modId);
    this.professionRegistry = new DeferredRegister<>(ForgeRegistries.PROFESSIONS, modId);
    this.poiRegistry = new DeferredRegister<>(ForgeRegistries.POI_TYPES, modId);
    this.memoryRegistry = new DeferredRegister<>(ForgeRegistries.MEMORY_MODULE_TYPES, modId);
    this.sensorRegistry = new DeferredRegister<>(ForgeRegistries.SENSOR_TYPES, modId);
    this.scheduleRegistry = new DeferredRegister<>(ForgeRegistries.SCHEDULES, modId);
    this.activityRegistry = new DeferredRegister<>(ForgeRegistries.ACTIVITIES, modId);
    this.carverRegistry = new DeferredRegister<>(ForgeRegistries.WORLD_CARVERS, modId);
    this.surfaceRegistry = new DeferredRegister<>(ForgeRegistries.SURFACE_BUILDERS, modId);
    this.featureRegistry = new DeferredRegister<>(ForgeRegistries.FEATURES, modId);
    this.placementRegistry = new DeferredRegister<>(ForgeRegistries.DECORATORS, modId);
    this.biomeProviderRegistry = new DeferredRegister<>(ForgeRegistries.BIOME_PROVIDER_TYPES, modId);
    this.chunkGeneratorRegistry = new DeferredRegister<>(ForgeRegistries.CHUNK_GENERATOR_TYPES, modId);
    this.chunkStatusRegistry = new DeferredRegister<>(ForgeRegistries.CHUNK_STATUS, modId);
    this.dimensionRegistry = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, modId);
    this.dataSerializerRegistry = new DeferredRegister<>(ForgeRegistries.DATA_SERIALIZERS, modId);
  }

  public void registerEventBus(IEventBus bus) {
    this.activeRegistries.forEach(o -> o.register(bus));
  }

  public Collection<RegistryObject<Block>> getBlocks() {
    return blockRegistry.getEntries();
  }

  public Collection<RegistryObject<Item>> getItems() {
    return itemRegistry.getEntries();
  }

  public <T extends Item> RegistryObject<T> registerItem(final String name, final Supplier<T> supplier) {
    this.activeRegistries.add(this.itemRegistry);
    return this.itemRegistry.register(name, supplier);
  }

  public <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<T> supplier, final Supplier<Item.Properties> itemblockProperties) {
    this.activeRegistries.add(this.itemRegistry);
    RegistryObject<T> result = registerBlockWithoutItem(name, supplier);
    this.itemRegistry.register(name, blockItem(result, itemblockProperties));
    return result;
  }

  public <T extends Block> RegistryObject<T> registerBlockWithoutItem(final String name, final Supplier<T> supplier) {
    this.activeRegistries.add(this.blockRegistry);
    return this.blockRegistry.register(name, supplier);
  }

  public <T extends Block> Supplier<T> block(Function<Block.Properties, T> creator, Supplier<Block.Properties> properties) {
    return () -> creator.apply(properties.get());
  }

  public Supplier<StairsBlock> stair(RegistryObject<? extends Block> source) {
    return block(b -> new StairsBlock(source.lazyMap(Block::getDefaultState), b), fromBlock(source));
  }

  public Supplier<SlabBlock> slab(Supplier<? extends Block> source) {
    return block(SlabBlock::new, fromBlock(source));
  }

  public Supplier<FenceBlock> fence(Supplier<? extends Block> source) {
    return block(FenceBlock::new, fromBlock(source));
  }

  public Supplier<FenceGateBlock> fenceGate(Supplier<? extends Block> source) {
    return block(FenceGateBlock::new, fromBlock(source));
  }

  public Supplier<WallBlock> wall(Supplier<? extends Block> source) {
    return block(WallBlock::new, fromBlock(source));
  }

  public Supplier<LogBlock> log(MaterialColor topColor, Supplier<Block.Properties> props) {
    return block(b -> new LogBlock(topColor, b), props);
  }

  public Supplier<StoneButtonBlock> stoneButton (Supplier<? extends Block> source) {
    return block(BaseStoneButtonBlock::new, fromBlock(source));
  }

  public Supplier<WoodButtonBlock> woodButton (Supplier<? extends Block> source) {
    return block(BaseWoodButtonBlock::new, fromBlock(source));
  }

  public Supplier<WeightedPressurePlateBlock> weightedPressurePlate (Supplier<? extends Block> source, int maxWeight) {
    return block(b -> new BaseWeightedPressurePlateBlock(maxWeight, b), fromBlock(source));
  }

  public Supplier<PressurePlateBlock> pressurePlate (Supplier<? extends Block> source, PressurePlateBlock.Sensitivity sensitivity) {
    return block(b -> new BasePressurePlateBlock(sensitivity, b), fromBlock(source));
  }

  public Supplier<TrapDoorBlock> trapDoor (Supplier<? extends Block> source) {
    return block(BaseTrapDoorBlock::new, fromBlock(source));
  }

  public Supplier<DoorBlock> door (Supplier<? extends Block> source) {
    return block(BaseDoorBlock::new, fromBlock(source));
  }

  interface ComposingSupplier<T> extends Supplier<T> {
    default <R> ComposingSupplier<R> then(Function<T, R> func) {
      return () -> func.apply(get());
    }
  }

  private ComposingSupplier<Block.Properties> fromBlock(Supplier<? extends Block> source) {
    return () -> {
      Objects.requireNonNull(source.get(), "Registered block is required");
      return Block.Properties.from(source.get());
    };
  }

  public <T extends Block> Supplier<BlockItem> blockItem(RegistryObject<T> block, Supplier<Item.Properties> properties) {
    return () -> new BlockItem(block.get(), properties.get());
  }

  public <T extends Block> Supplier<BlockNamedItem> blockNamedItem(Supplier<RegistryObject<T>> block, Supplier<Item.Properties> properties) {
    return () -> new BlockNamedItem(block.get().get(), properties.get());
  }

  public Supplier<BaseOreBlock> ore(OreBuilder<BaseOreBlock> creator, MaterialType material) {
    return () -> creator.apply(material.getOreBlockProperties().get(), material.getMinXP(), material.getMaxXP());
  }

  public <T extends Item> Supplier<T> item(Function<Item.Properties, T> creator, Supplier<Item.Properties> properties) {
    return () -> creator.apply(properties.get());
  }

  public Supplier<DyeItem> dyeItem(DyeColor dye, Supplier<Item.Properties> properties) {
    return () -> new DyeItem(dye, properties.get());
  }

  public <T extends Entity> Supplier<EntityType<T>> entity(String name, Supplier<EntityType.Builder<T>> builder) {
    return () -> builder.get().build(name);
  }

  public <T extends Item> Supplier<T> tool(ToolBuilder<T> builder, Type Q, MaterialType material, Supplier<Item.Properties> properties) {
    return () -> builder.apply(material, material.getDamage(Q), material.getSpeed(Q), properties.get());
  }

  public Supplier<SwordItem> sword(ToolBuilder<SwordItem> builder, MaterialType material, Supplier<Item.Properties> properties) {
    return tool(builder, Type.SWORD, material, properties);
  }

  public Supplier<PickaxeItem> pickaxe(ToolBuilder<PickaxeItem> builder, MaterialType material, Supplier<Item.Properties> properties) {
    return tool(builder, Type.PICKAXE, material, properties);
  }

  public Supplier<AxeItem> axe(ToolBuilder<AxeItem> builder, MaterialType material, Supplier<Item.Properties> properties) {
    return tool(builder, Type.AXE, material, properties);
  }

  public Supplier<ShovelItem> shovel(ToolBuilder<ShovelItem> builder, MaterialType material, Supplier<Item.Properties> properties) {
    return tool(builder, Type.SHOVEL, material, properties);
  }

  public Supplier<KnifeItem> knife(ToolBuilder<KnifeItem> builder, MaterialType material, Supplier<Item.Properties> properties) {
    return tool(builder, Type.KNIFE, material, properties);
  }

  public Supplier<HoeItem> hoe(HoeBuilder<HoeItem> builder, MaterialType material, Supplier<Item.Properties> properties) {
    return () -> builder.apply(material, material.getSpeed(Type.HOE), properties.get());
  }

  public Supplier<ArmorItem> armor(ArmorBuilder<ArmorItem> builder, MaterialType material, EquipmentSlotType slot, Supplier<Item.Properties> properties) {
    return () -> builder.apply(material, slot, properties.get());
  }

  @FunctionalInterface
  public interface ToolBuilder<V extends Item> {
    V apply(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder);
  }

  @FunctionalInterface
  public interface HoeBuilder<V extends Item> {
    V apply(IItemTier tier, float attackSpeedIn, Item.Properties builder);
  }

  @FunctionalInterface
  public interface ArmorBuilder<V extends Item> {
    V apply(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builder);
  }

  @FunctionalInterface
  public interface OreBuilder<V extends OreBlock> {
    V apply(Block.Properties properties, int maxXP, int minXP);
  }
}
