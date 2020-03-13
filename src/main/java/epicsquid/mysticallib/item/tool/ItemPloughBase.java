package epicsquid.mysticallib.item.tool;

import epicsquid.mysticallib.item.ItemHoeBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPloughBase extends ItemHoeBase implements ISizedTool {
  public ItemPloughBase(ToolMaterial material, String name, int toolLevel, int maxDamage, int enchantability) {
    super(material, name, toolLevel, maxDamage, enchantability);
  }

  @Override
  public int getWidth(ItemStack stack) {
    return 3;
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos origin, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack itemstack = player.getHeldItem(hand);

    boolean failed = false;
    int width = getWidth(itemstack);

    for (int x = -width; x < width + 1; x++) {
      for (int z = -width; z < width + 1; z++) {
        BlockPos pos;

        switch (facing.getAxis()) {
          case X:
            pos = origin.add(0, x, z);
            break;
          case Y:
            pos = origin.add(x, 0, z);
            break;
          case Z:
            pos = origin.add(x, z, 0);
            break;
          default:
            continue;
        }

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
          failed = true;
        } else {
          failed = false;
          int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(itemstack, player, worldIn, pos);
          if (hook != 0) {
            if (hook <= 0) {
              failed = true;
            }
          } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
              if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                this.setBlock(itemstack, player, worldIn, pos, Blocks.FARMLAND.getDefaultState());
              } else if (block == Blocks.DIRT) {
                switch (iblockstate.getValue(BlockDirt.VARIANT)) {
                  case DIRT:
                    this.setBlock(itemstack, player, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                    break;
                  case COARSE_DIRT:
                    this.setBlock(itemstack, player, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                  default:
                    break;
                }
              }
            }
          }
        }
      }
    }
    if (failed) {
      return EnumActionResult.FAIL;
    } else {
      return EnumActionResult.PASS;
    }
  }
}
