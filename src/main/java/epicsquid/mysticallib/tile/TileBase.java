package epicsquid.mysticallib.tile;

import epicsquid.mysticallib.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileBase extends TileEntity implements ITile {

  public TileBase(TileEntityType<?> type) {
    super(type);
  }

  @Override
  public boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player, @Nonnull Hand hand,
                          @Nonnull Direction side, float hitX, float hitY, float hitZ) {
    return false;
  }

  @Override
  public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable PlayerEntity player) {
    invalidateCaps();
  }

  @Override
  @Nonnull
  public CompoundNBT getUpdateTag() {
    return write(new CompoundNBT());
  }

  @Override
  @Nullable
  public SUpdateTileEntityPacket getUpdatePacket() {
    return new SUpdateTileEntityPacket(getPos(), 0, getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    read(pkt.getNbtCompound());
  }

  @Nonnull
  public static String getTileName(@Nonnull Class<? extends TileEntity> teClass) {
    return Util.getLowercaseClassName(teClass);
  }

  // TODO: I literally can't think of a better name for this function
  public void updatePacketViaState() {
    BlockState state = world.getBlockState(getPos());
    world.notifyBlockUpdate(getPos(), state, state, 8);
  }

  protected boolean dropItemInInventory(ItemStackHandler inventory, int slot) {
    if (!inventory.getStackInSlot(slot).isEmpty()) {
      ItemStack extracted = inventory.extractItem(slot, 1, false);
      if (!world.isRemote) {
        world.addEntity(new ItemEntity(world, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, extracted));
      }
      markDirty();
      updatePacketViaState();
      return true;
    }
    return false;
  }
}