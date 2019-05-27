package epicsquid.mysticallib.item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class ItemKnifeBase extends ItemToolBase {
  public static Set<Block> BLOCKS = Sets.newHashSet(Blocks.PLANKS, Blocks.LOG, Blocks.LOG2);

  public ItemKnifeBase(String name, ToolMaterial material) {
    super(name, 2.0f, -1.6f, material, BLOCKS);
  }

  public ItemKnifeBase(String name, ToolMaterial material, Set<Block> blocks) {
    super(name, 2.0f, -1.6f, material, blocks);
  }

  @Override
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    stack.damageItem(1, attacker);
    return true;
  }

  @Override
  @Nonnull
  public Set<String> getToolClasses(ItemStack stack) {
    return Collections.singleton("druidKnife");
  }
}
