package epicsquid.mysticallib.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerServerInteractEvent extends Event {
  protected EntityPlayer player = null;
  protected EnumHand hand = null;
  protected ItemStack stack = ItemStack.EMPTY;

  public PlayerServerInteractEvent(EntityPlayer player, EnumHand hand, ItemStack stack) {
    this.player = player;
    this.hand = hand;
    this.stack = stack;
  }

  public EntityPlayer getPlayer() {
    return player;
  }

  public EnumHand getHand() {
    return hand;
  }

  public ItemStack getStack() {
    return stack;
  }

  public static class LeftClickAir extends PlayerServerInteractEvent {

    public LeftClickAir(EntityPlayer player, EnumHand hand, ItemStack stack) {
      super(player, hand, stack);
    }

  }

  public static class RightClickAir extends PlayerServerInteractEvent {

    public RightClickAir(EntityPlayer player, EnumHand hand, ItemStack stack) {
      super(player, hand, stack);
    }

  }
}
