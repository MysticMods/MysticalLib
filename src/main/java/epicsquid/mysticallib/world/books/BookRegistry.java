package epicsquid.mysticallib.world.books;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BookRegistry extends WorldSavedData {
  public static String id = "BookRegistry";

  public static BookRegistry getBookRegistry() {
    WorldServer server = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
    BookRegistry registry = (BookRegistry) server.getMapStorage().getOrLoadData(BookRegistry.class, id);

    if (registry == null) {
      registry = new BookRegistry();
      server.getMapStorage().setData(id, registry);
    }

    return registry;
  }

  public Map<String, Object2BooleanOpenHashMap<UUID>> data = new HashMap<>();

  public BookRegistry() {
    super(id);
  }

  public void gaveBook (EntityPlayer player, String bookName) {
    Object2BooleanOpenHashMap<UUID> map = data.computeIfAbsent(bookName, book -> new Object2BooleanOpenHashMap<>());
    map.defaultReturnValue(false);
    map.put(player.getUniqueID(), true);
    markDirty();
  }

  public boolean hasBook(EntityPlayer player, String bookName) {
    Object2BooleanOpenHashMap<UUID> map = data.computeIfAbsent(bookName, book -> new Object2BooleanOpenHashMap<>());
    map.defaultReturnValue(false);
    return map.getBoolean(player.getUniqueID());
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    // This isn't a high-frequency map so I'm happy to get the slowdown with UUID.fromString
    data.clear();
    NBTTagCompound tag = nbt.getCompoundTag("BookRegistryData");
    Set<String> bookList = Sets.newHashSet(tag.getKeySet());
    for (String bookName : bookList) {
      Object2BooleanOpenHashMap<UUID> map = data.computeIfAbsent(bookName, o -> new Object2BooleanOpenHashMap<>());
      map.defaultReturnValue(false);
      NBTTagCompound entries = tag.getCompoundTag(bookName);
      Set<String> uuids = Sets.newHashSet(entries.getKeySet());
      for (String uuid : uuids) {
        UUID id = UUID.fromString(uuid);
        map.put(id, entries.getBoolean(uuid));
      }
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    NBTTagCompound data = new NBTTagCompound();
    for (Map.Entry<String, Object2BooleanOpenHashMap<UUID>> entry : this.data.entrySet()) {
      NBTTagCompound thisEntry = new NBTTagCompound();
      for (Object2BooleanMap.Entry<UUID> subEntry : entry.getValue().object2BooleanEntrySet()) {
        thisEntry.setBoolean(subEntry.getKey().toString(), subEntry.getBooleanValue());
      }
      data.setTag(entry.getKey(), thisEntry);
    }
    compound.setTag("BookRegistryData", data);
    return compound;
  }
}
