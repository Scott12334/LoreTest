package net.newlife.lore.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.newlife.lore.Main;
import net.newlife.lore.network.messages.ServerBoundUpdatePacket;


public class Network {
    public static final String NETWORK_VERSION="1";
    public static final SimpleChannel INSTANCE =NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Main.MOD_ID, "network"),
            () -> NETWORK_VERSION,
            NETWORK_VERSION::equals,
            NETWORK_VERSION::equals
    );

    public static void init(){
        int index=0;
        INSTANCE.messageBuilder(ServerBoundUpdatePacket.class,index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerBoundUpdatePacket::encode)
                .decoder(ServerBoundUpdatePacket::new)
                .consumer(ServerBoundUpdatePacket::handle)
                .add();
    }
}
