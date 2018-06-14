package epicsquid.mysticallib.network;

import epicsquid.mysticallib.fx.FXRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageEffect implements IMessage {
  public int id = 0;
  public NBTTagCompound tag = new NBTTagCompound();

  public MessageEffect() {
  }

  public MessageEffect(int id, NBTTagCompound tag) {
    this.tag = tag;
    this.id = id;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    id = buf.readInt();
    tag = ByteBufUtils.readTag(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(id);
    ByteBufUtils.writeTag(buf, tag);
  }

  public static class MessageHolder implements IMessageHandler<MessageEffect, IMessage> {
    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(final MessageEffect message, final MessageContext ctx) {
      Minecraft.getMinecraft().addScheduledTask(() -> {
        FXRegistry.effects.get(message.id).apply(message.tag);
      });
      return null;
    }
  }
}
