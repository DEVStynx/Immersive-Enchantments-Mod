package de.stynxyxy.betterminecraft.network;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Modmessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int ID() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ImmersiveEnchatments.MODID,"messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        //net.messageBuilder(TestSpellC2SPacket.class,ID(), NetworkDirection.PLAY_TO_SERVER)
                //.decoder(TestSpellC2SPacket::new)
                //.encoder(TestSpellC2SPacket::toBytes)
                //.consumerMainThread(TestSpellC2SPacket::handle)
                //.add();

    }

    public static <MSG> void sendtoServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendtoPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);

    }
}
