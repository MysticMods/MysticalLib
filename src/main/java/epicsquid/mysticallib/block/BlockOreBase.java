package epicsquid.mysticallib.block;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockOreBase extends BlockBase {
  private final Item drop;
  private final int minXP;
  private final int maxXP;

  public BlockOreBase(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nullable Item drop, int level, int minXP,
      int maxXP) {
    super(mat, type, hardness, name);
    this.drop = drop;
    //this.setHarvestLevel("pickaxe", level);
    this.minXP = minXP;
    this.maxXP = maxXP;
  }

//  @Override
//  public Item getItemDropped(BlockState state, Random rand, int fortune) {
//    if (drop != null)
//      return drop;
//
//    return super.getItemDropped(state, rand, fortune);
//  }
//
//
//  @Override
//  public int quantityDroppedWithBonus(int fortune, Random random) {
//    if (this.drop == null)
//      return super.quantityDroppedWithBonus(fortune, random);
//
//    int drop = quantityDropped(random);
//    if (fortune > 0 || random.nextInt(10) == 0) {
//      int i = random.nextInt(fortune + 2);
//
//      if (i < 0) {
//        i = 1;
//      }
//
//      return drop * i;
//    }
//
//    return drop;
//  }

  @Override
  public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
    if (minXP == -1 || maxXP == -1)
      return 0;

    Random rand = world instanceof World ? ((World) world).rand : new Random();
    return MathHelper.getInt("", minXP, maxXP);
    //todo: fix the string part of getInt
  }

}
