package epicsquid.mysticallib.item.tool;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import epicsquid.mysticallib.item.ItemToolBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import javax.annotation.Nullable;
import java.util.Set;

public class ItemHammerBase extends ItemSizedTool implements IEffectiveTool {
  private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
  private static Set<String> TOOL_CLASSES = ImmutableSet.of("pickaxe", "hammer");
  private static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(Material.ROCK, Material.IRON, Material.GLASS, Material.ICE, Material.PACKED_ICE, Material.ANVIL);

  public ItemHammerBase(String name, int maxDamage, int enchantability, ToolMaterial materialIn) {
    super(name, 11, -4f, materialIn);
    setMaxDamage(maxDamage);
    setEnchantability(enchantability);
  }

  @Override
  public Set<String> getToolClasses(ItemStack stack) {
    return TOOL_CLASSES;
  }

  @Override
  public Set<Block> getEffectiveBlocks() {
    return EFFECTIVE_ON;
  }

  @Override
  public Set<Material> getEffectiveMaterials() {
    return EFFECTIVE_MATERIALS;
  }

  @Override
  public int getWidth(ItemStack stack) {
    return 3;
  }
}
