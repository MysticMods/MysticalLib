package epicsquid.mysticallib.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Util {

  public static Random rand = new Random();

  @Nonnull
  public static <T extends TileEntity> List<T> getTileEntitiesWithin(@Nonnull World world, @Nonnull Class<? extends T> teClass, BlockPos pos, int radius) {
    List<T> tiles = new ArrayList<>();
    for (int i = pos.getX() - radius; i <= pos.getX() + radius; i++) {
      for (int j = pos.getY() - radius; j <= pos.getY() + radius; j++) {
        for (int k = pos.getZ() - radius; k <= pos.getZ() + radius; k++) {
          BlockPos p = new BlockPos(i, j, k);
          IChunk c = world.getChunk(p);
          // TODO confirm that "is loaded" is fine changed to this
          if (c.isModified()) {
            TileEntity t = world.getChunk(p).getTileEntity(p);
            if (teClass.isInstance(t)) {
              tiles.add((T) t);
            }
          }
        }
      }
    }
    return tiles;
  }

  public static <T extends Entity> List<T> getEntitiesWithinRadius(World world, Class<? extends T> classEntity, BlockPos pos, float xradius, float yradius,
                                                                   float zradius) {
    return world.getEntitiesWithinAABB(classEntity,
        new AxisAlignedBB(pos.getX() - xradius, pos.getY() - yradius, pos.getZ() - zradius, pos.getX() + xradius, pos.getY() + yradius, pos.getZ() + zradius));
  }

  public static List<LivingEntity> getEntitiesWithinRadius(World world, Predicate<Entity> comparison, BlockPos pos, float xradius, float yradius,
                                                           float zradius) {
    return world.getEntitiesWithinAABB(LivingEntity.class,
        new AxisAlignedBB(pos.getX() - xradius, pos.getY() - yradius, pos.getZ() - zradius, pos.getX() + xradius, pos.getY() + yradius, pos.getZ() + zradius))
        .stream().filter(comparison).collect(Collectors.toList());
  }

  public static List<BlockPos> getBlocksWithinRadius(World world, BlockPos pos, float xradius, float yradius, float zradius, Block... block) {
    List<Block> blocks = Arrays.asList(block);
    return getBlocksWithinRadius(world, pos, xradius, yradius, zradius, (test) -> blocks.contains(world.getBlockState(test).getBlock()));
  }

  public static List<BlockPos> getBlocksWithinRadius(World world, BlockPos pos, float xradius, float yradius, float zradius, Block block) {
    return getBlocksWithinRadius(world, pos, xradius, yradius, zradius, (test) -> world.getBlockState(test).getBlock() == block);
  }

  public static List<BlockPos> getBlocksWithinRadius(World world, BlockPos pos, float xradius, float yradius, float zradius, Predicate<BlockPos> comparison) {
    List<BlockPos> blockList = new ArrayList<>();
    for (int x = (int) -xradius; x <= xradius; x++) {
      for (int z = (int) -zradius; z <= zradius; z++) {
        for (int y = (int) -yradius; y <= yradius; y++) {
          if (comparison.test(pos.add(x, y, z))) {
            blockList.add(pos.add(x, y, z));
          }
        }
      }
    }
    return blockList;
  }

  @Nonnull
  public static String lowercase(@Nonnull String s) {
    String f = "";
    for (int i = 0; i < s.length(); i++) {
      String c = s.substring(i, i + 1);
      if (c.toUpperCase().compareTo(c) == 0) {
        if (i > 0) {
          f += "_";
        }
        f += c.toLowerCase();
      } else {
        f += c;
      }
    }
    return f;
  }

  public static String englishName(ResourceLocation location) {
    String internalName = location.getPath();
    return Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
  }

  public static int intColor(int r, int g, int b) {
    return ((255 << 24) + r * 65536 + g * 256 + b);
  }

  @Nonnull
  public static String getLowercaseClassName(@Nonnull Class c) {
    String[] nameParts = c.getTypeName().split("\\.");
    String className = nameParts[nameParts.length - 1];
    return lowercase(className);
  }

  public static void spawnInventoryInWorld(World world, double x, double y, double z, IItemHandler inventory) {
    if (inventory != null && !world.isRemote) {
      for (int i = 0; i < inventory.getSlots(); i++) {
        if (!inventory.getStackInSlot(i).isEmpty()) {
          world.addEntity(new ItemEntity(world, x, y, z, inventory.getStackInSlot(i)));
        }
      }
    }
  }

  public static void appendLoreTag(ItemStack stack, String... lines) {
    CompoundNBT tag = stack.getTag();
    if (tag == null) {
      tag = new CompoundNBT();
      stack.setTag(tag);
    }

    CompoundNBT display;
    if (tag.get("display") == null || !(tag.get("display") instanceof CompoundNBT)) {
      display = new CompoundNBT();
      tag.put("display", display);
    } else {
      display = tag.getCompound("display");
    }

    ListNBT lore;
    if (tag.get("lore") == null || !(display.get("lore") instanceof ListNBT)) {
      lore = new ListNBT();
      display.put("lore", lore);
    } else {
      lore = display.getList("lore", Constants.NBT.TAG_STRING);
    }

    for (String string : lines) {
      lore.add(new StringNBT(string));
    }
  }

  public static ItemStack damageItem (int amount, ItemStack stack, CraftingInventory inventory) {
    Container container = ObfuscationReflectionHelper.getPrivateValue(CraftingInventory.class, inventory, "field_70465_c");
    if (container instanceof WorkbenchContainer) {
      WorkbenchContainer wb = (WorkbenchContainer) container;
      PlayerEntity player = ObfuscationReflectionHelper.getPrivateValue(WorkbenchContainer.class, wb, "field_192390_i");
      if (player != null) {
        stack.damageItem(amount, player, (p) -> {});
        return stack;
      }
    }

    if (stack.attemptDamageItem(amount, Util.rand, null)) {
      stack.shrink(1);
    }

    return stack;
  }
}
