package epicsquid.mysticallib.item.tool;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import epicsquid.mysticallib.item.ItemShovelBase;
import epicsquid.mysticallib.util.BreakUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class ItemExcavatorBase extends ItemSizedTool {
  private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);
  private static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(Material.CLAY, Material.SAND, Material.SNOW, Material.GROUND);

  public ItemExcavatorBase(String name, int maxDamage, ToolMaterial materialIn) {
    super(name, 13, -3f, materialIn, EFFECTIVE_ON);
    setMaxDamage(maxDamage);
    setHarvestLevel("shovel", materialIn.getHarvestLevel());
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
