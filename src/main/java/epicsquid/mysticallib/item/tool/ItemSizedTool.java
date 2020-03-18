package epicsquid.mysticallib.item.tool;

import epicsquid.mysticallib.item.ItemToolBase;
import epicsquid.mysticallib.util.BreakUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public abstract class ItemSizedTool extends ItemToolBase implements IEffectiveTool {
  public ItemSizedTool(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocks) {
    super(name, attackDamageIn, attackSpeedIn, materialIn, effectiveBlocks);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {
    Material material = state.getMaterial();
    if (getEffectiveMaterials().contains(material)) {
      return this.efficiency;
    }
    return super.getDestroySpeed(stack, state);
  }

  @Override
  public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
    final Set<BlockPos> breakableBlocks = BreakUtil.nearbyBlocks(itemstack, pos, player);
    if (breakableBlocks.isEmpty()) {
      maybeDamage(itemstack, 1, player);
      return false;
    } else {
      int count = 0;
      final World world = player.world;
      for (final BlockPos breakPos : breakableBlocks) {
        if (BreakUtil.harvestBlock(world, breakPos, player)) {
          count++;
        }
      }
      if (count > 0) {
        final int dam = Math.max(3, itemRand.nextInt(Math.max(1, count - 3)));
        maybeDamage(itemstack, dam, player);
      }
      return false;
    }
  }

  private void maybeDamage(ItemStack stack, int amount, EntityPlayer player) {
    if (!player.isCreative()) {
      stack.damageItem(amount, player);
    }
  }
}
