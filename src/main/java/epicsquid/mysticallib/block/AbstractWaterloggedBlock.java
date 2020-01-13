package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

@SuppressWarnings({"deprecation", "Duplicates"})
public abstract class AbstractWaterloggedBlock extends Block implements IWaterLoggable {
  protected static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  public AbstractWaterloggedBlock(Properties properties) {
    super(properties);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    BlockState state = getDefaultState();
    IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
    if (ifluidstate.getFluid() == Fluids.WATER) {
      return state.with(WATERLOGGED, true);
    }

    return state;
  }

  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
    if (stateIn.get(WATERLOGGED)) {
      worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
    }

    return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED);
  }

  @Override
  public IFluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
  }
}
