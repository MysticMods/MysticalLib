package epicsquid.mysticallib.network;

import epicsquid.mysticallib.tile.TileModular;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageToggleModuleOutput implements IMessage {
  public int x = 0, y = 0, z = 0;
  public String module = "";

  public MessageToggleModuleOutput() {
    //
  }

  public MessageToggleModuleOutput(int x, int y, int z, String module) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.module = module;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    x = buf.readInt();
    y = buf.readInt();
    z = buf.readInt();
    module = ByteBufUtils.readUTF8String(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(x);
    buf.writeInt(y);
    buf.writeInt(z);
    ByteBufUtils.writeUTF8String(buf, module);
  }

  public static class MessageHolder implements IMessageHandler<MessageToggleModuleOutput, IMessage> {
    @Override
    public IMessage onMessage(final MessageToggleModuleOutput message, final MessageContext ctx) {
      FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
        BlockPos p = new BlockPos(message.x, message.y, message.z);
        TileEntity t = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTileEntity(p);
        if (t instanceof TileModular) {
          TileModular m = (TileModular) t;
//          if (m.validIOModules.contains(message.module)) {
//            m.validIOModules.remove(message.module);
//          } else {
//            m.validIOModules.add(message.module);
//          }
          m.markDirty();
        }
      });
      return null;
    }
  }
}
