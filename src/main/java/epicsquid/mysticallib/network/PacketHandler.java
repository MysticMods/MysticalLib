package epicsquid.mysticallib.network;

import epicsquid.mysticallib.MysticalLib;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {

	private static final String PROTOCOL_VERSION = Integer.toString(2);
	private static short index = 0;

	public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
					.named(new ResourceLocation(MysticalLib.MODID, "main_network_channel"))
					.clientAcceptedVersions(PROTOCOL_VERSION::equals)
					.serverAcceptedVersions(PROTOCOL_VERSION::equals)
					.networkProtocolVersion(() -> PROTOCOL_VERSION)
					.simpleChannel();

	private static int id = 0;

	public static void registerMessages() {
	}

	public static void sendTo(Object msg, ServerPlayerEntity player) {
		if (!(player instanceof FakePlayer))
			HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendToServer(Object msg) {
		HANDLER.sendToServer(msg);
	}

	private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
		HANDLER.registerMessage(index, messageType, encoder, decoder, messageConsumer);
		index++;
		if (index > 0xFF)
			throw new RuntimeException("Too many messages!");
	}
}