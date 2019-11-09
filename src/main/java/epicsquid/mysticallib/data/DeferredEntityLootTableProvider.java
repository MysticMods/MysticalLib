package epicsquid.mysticallib.data;

import epicsquid.mysticallib.MysticalLib;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class DeferredEntityLootTableProvider extends EntityLootTables {
  private static Method addEntityLootTable = ObfuscationReflectionHelper.findMethod(EntityLootTables.class, "func_218582_a", EntityType.class, LootTable.Builder.class);

  static {
    addEntityLootTable.setAccessible(true);
  }

  @Override
  public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
    this.addTables();
  }

  public abstract void addTables ();

  public void entity(Supplier<? extends EntityType> entity, LootTable.Builder builder) {
    try {
      addEntityLootTable.invoke(this, entity.get(), builder);
    } catch (IllegalAccessException | InvocationTargetException e) {
      MysticalLib.LOG.error("Unable to generate entity loot table", e);
    }
  }
}
