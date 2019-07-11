package epicsquid.mysticallib.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nonnull;

// TODO determine if IPlantable is even needed (might be for autofarmers)
public class SeedItem extends Item implements IPlantable {

	private PlantType plantType;
	private Block crop;

	/**
	 * Creates a generic seed item with a given farmland and crop block
	 */
	public SeedItem(Properties props, @Nonnull Block crop, @Nonnull PlantType type) {
		super(props);
		this.plantType = type;
		this.crop = crop;
	}

	@Override
	@Nonnull
	public ActionResultType onItemUse(ItemUseContext context) {
		Direction facing = context.getFace();
		PlayerEntity player = context.getPlayer();
		BlockPos pos = context.getPos();

		ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
		BlockState state = context.getWorld().getBlockState(context.getPos());
		if (facing == Direction.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && context.getWorld().isAirBlock(pos.up()) && (state.getBlock()
						.canSustainPlant(state, context.getWorld(), pos, Direction.UP, this))) {
			context.getWorld().setBlockState(pos.up(), crop.getDefaultState());

			if (player instanceof ServerPlayerEntity) {
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos.up(), stack);
			}

			if (!player.isCreative())
				stack.shrink(1);
			return ActionResultType.SUCCESS;
		} else {
			return ActionResultType.FAIL;
		}
	}

	@Override
	@Nonnull
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return this.plantType;
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return crop.getDefaultState();
	}
}
