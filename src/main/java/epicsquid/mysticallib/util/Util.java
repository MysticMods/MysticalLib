package epicsquid.mysticallib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings("unchecked")
public class Util {

  public static Random rand = new Random();

  public static <T extends Entity> List<T> getEntitiesWithinRadius(World world, Class<? extends T> classEntity, BlockPos pos, float xradius, float yradius,
      float zradius) {
    return world.getEntitiesWithinAABB(classEntity,
        new AxisAlignedBB(pos.getX() - xradius, pos.getY() - yradius, pos.getZ() - zradius, pos.getX() + xradius, pos.getY() + yradius, pos.getZ() + zradius));
  }

  public static List<EntityLiving> getEntitiesWithinRadius(World world, Predicate<Entity> comparison, BlockPos pos, float xradius, float yradius,
      float zradius) {
    return world.getEntitiesWithinAABB(EntityLiving.class,
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
          world.spawnEntity(new EntityItem(world, x, y, z, inventory.getStackInSlot(i)));
        }
      }
    }
  }

  public static void appendLoreTag(ItemStack stack, String... lines) {
    NBTTagCompound tag = stack.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      stack.setTagCompound(tag);
    }

    NBTTagCompound display;
    if (!tag.hasKey("display") || !(tag.getTag("display") instanceof NBTTagCompound)) {
      display = new NBTTagCompound();
      tag.setTag("display", display);
    } else {
      display = tag.getCompoundTag("display");
    }

    NBTTagList lore;
    if (!display.hasKey("Lore") || !(display.getTag("Lore") instanceof NBTTagList)) {
      lore = new NBTTagList();
      display.setTag("Lore", lore);
    } else {
      lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);
    }

    for (String string : lines) {
      lore.appendTag(new NBTTagString(string));
    }
  }
}
