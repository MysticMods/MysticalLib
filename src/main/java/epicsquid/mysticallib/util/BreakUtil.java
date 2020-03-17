package epicsquid.mysticallib.util;


import epicsquid.mysticallib.item.tool.IEffectiveTool;
import epicsquid.mysticallib.item.tool.ILimitAxis;
import epicsquid.mysticallib.item.tool.ISizedTool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.HashSet;
import java.util.Set;

// Concept based partially on ToolFunctions.java by astradamus from MIT Licensed Practical Tools
// https://github.com/astradamus/PracticalTools/blob/master/src/main/java/com/alexanderstrada/practicaltools/ToolFunctions.java
public class BreakUtil {
  public static void breakNeighbours(ItemStack tool, World world, BlockPos pos, EntityPlayer player) {
    if (world.isRemote) return;

    if (tool.isEmpty()) return;

    if (!(tool.getItem() instanceof IEffectiveTool)) return;

    IBlockState originalState = world.getBlockState(pos);
    world.setBlockState(pos, Blocks.GLASS.getDefaultState());
    RayTraceResult ray = rayTrace(world, player);
    world.setBlockState(pos, originalState);

    if (ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK) {
      return;
    }

    EnumFacing facing = ray.sideHit;
    int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
    boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) != 0;

    for (BlockPos target : nearbyBlocks(tool, pos, facing, world, player)) {
      IBlockState state = world.getBlockState(target);

      if (tool.getItem() instanceof IEffectiveTool) {
        IEffectiveTool toolItem = (IEffectiveTool) tool.getItem();
        if (toolItem.getEffectiveBlocks().contains(state.getBlock()) || toolItem.getEffectiveMaterials().contains(state.getMaterial())) {
          world.destroyBlock(target, false);
          state.getBlock().harvestBlock(world, player, target, state, null, tool);
          if (!silkTouch) {
            state.getBlock().dropXpOnBlockBreak(world, target, state.getBlock().getExpDrop(state, world, target, fortune));
          }
          tool.damageItem(1, player);
        }
      }
    }
  }

  public static Set<BlockPos> nearbyBlocks(ItemStack tool, BlockPos origin, EnumFacing facing, World world, EntityPlayer player) {
    int width = ((ISizedTool) tool.getItem()).getWidth(tool);
    if (width % 2 == 0) {
      width /= 2;
    } else {
      width = (width - 1) / 2;
    }

    Set<BlockPos> result = new HashSet<>();

    if (tool.getItem() instanceof ILimitAxis) {
      if (!((ILimitAxis) tool.getItem()).getLimits().contains(facing.getAxis())) {
        return result;
      }
    }

    for (int x = -width; x < width + 1; x++) {
      for (int z = -width; z < width + 1; z++) {
        if (x == z && z == 0) {
          continue;
        }

        BlockPos potential;

        switch (facing.getAxis()) {
          case X:
            potential = origin.add(0, x, z);
            break;
          case Y:
            potential = origin.add(x, 0, z);
            break;
          case Z:
            potential = origin.add(x, z, 0);
            break;
          default:
            continue;
        }

        IBlockState state = world.getBlockState(potential);
        if (!ForgeHooks.canHarvestBlock(state.getBlock(), player, world, potential)) {
          continue;
        }

        IEffectiveTool toolItem = (IEffectiveTool) tool.getItem();
        if (toolItem.getEffectiveBlocks().contains(state.getBlock()) || toolItem.getEffectiveMaterials().contains(state.getMaterial())) {
          result.add(potential);
        }
      }
    }

    return result;
  }

  public static RayTraceResult rayTrace(World world, EntityPlayer player) {
    Vec3d eyes = player.getPositionEyes(1f);
    float yawCos = MathHelper.cos(-player.rotationYaw * (float) (Math.PI / 180F) - (float) Math.PI);
    float yawSin = MathHelper.sin(-player.rotationYaw * (float) (Math.PI / 180F) - (float) Math.PI);
    float pitchCos = -MathHelper.cos(-player.rotationPitch * (float) (Math.PI / 180F));
    float pitchSin = MathHelper.sin(-player.rotationPitch * (float) (Math.PI / 180F));

    float f1 = yawSin * pitchCos;
    float f2 = yawCos * pitchCos;

    double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
    Vec3d reachVec = eyes.add((double) f1 * reach, (double) pitchSin * reach, (double) f2 * reach);
    return world.rayTraceBlocks(eyes, reachVec, false, true, true);
  }
}

