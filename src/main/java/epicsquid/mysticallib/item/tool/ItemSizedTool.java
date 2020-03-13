package epicsquid.mysticallib.item.tool;

import epicsquid.mysticallib.item.ItemToolBase;
import epicsquid.mysticallib.util.BreakUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;

public abstract class ItemSizedTool extends ItemToolBase implements IEffectiveTool {
  protected int enchantability;

  public ItemSizedTool(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn) {
    super(name, attackDamageIn, attackSpeedIn, materialIn, Collections.emptySet());
  }

  public int getEnchantability() {
    return enchantability;
  }

  public void setEnchantability(int enchantability) {
    this.enchantability = enchantability;
  }

  @Override
  public int getItemEnchantability() {
    return getEnchantability();
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
  public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
    if (entityLiving instanceof EntityPlayer && !entityLiving.isSneaking()) {
      BreakUtil.breakNeighbours(stack, worldIn, pos, (EntityPlayer) entityLiving);
    }
    return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
  }
}
