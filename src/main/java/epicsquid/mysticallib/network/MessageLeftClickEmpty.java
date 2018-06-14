package epicsquid.mysticallib.network;

import java.util.UUID;

import epicsquid.mysticallib.event.PlayerServerInteractEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageLeftClickEmpty implements IMessage {
  public UUID id = null;
  public EnumHand hand = null;
  public NBTTagCompound tag = new NBTTagCompound();

  public MessageLeftClickEmpty() {
  }

  public MessageLeftClickEmpty(EntityPlayer player, EnumHand hand, ItemStack stack) {
    this.id = player.getUniqueID();
    this.hand = hand;
    this.tag = stack.writeToNBT(new NBTTagCompound());
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    id = new UUID(buf.readLong(), buf.readLong());
    hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    tag = ByteBufUtils.readTag(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeLong(id.getMostSignificantBits());
    buf.writeLong(id.getLeastSignificantBits());
    buf.writeBoolean(hand == EnumHand.MAIN_HAND);
    ByteBufUtils.writeTag(buf, tag);
  }

  public static class MessageHolder implements IMessageHandler<MessageLeftClickEmpty, IMessage> {
    @Override
    public IMessage onMessage(final MessageLeftClickEmpty message, final MessageContext ctx) {
      MinecraftForge.EVENT_BUS.post(
          new PlayerServerInteractEvent.LeftClickAir(ctx.getServerHandler().player.world.getPlayerEntityByUUID(message.id), message.hand,
              new ItemStack(message.tag)));
      return null;
    }
  }
}
