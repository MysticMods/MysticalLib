package epicsquid.mysticallib.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;


public class PlayerServerInteractEvent extends Event {
	protected PlayerEntity player;
	protected Hand hand;
	protected ItemStack stack;

	public PlayerServerInteractEvent(@Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull ItemStack stack) {
		this.player = player;
		this.hand = hand;
		this.stack = stack;
	}

	@Nonnull
	public PlayerEntity getPlayer() {
		return player;
	}

	@Nonnull
	public Hand getHand() {
		return hand;
	}

	@Nonnull
	public ItemStack getStack() {
		return stack;
	}

	public static class LeftClickAir extends PlayerServerInteractEvent {

		public LeftClickAir(@Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull ItemStack stack) {
			super(player, hand, stack);
		}

	}

	public static class RightClickAir extends PlayerServerInteractEvent {

		public RightClickAir(@Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull ItemStack stack) {
			super(player, hand, stack);
		}

	}
}
