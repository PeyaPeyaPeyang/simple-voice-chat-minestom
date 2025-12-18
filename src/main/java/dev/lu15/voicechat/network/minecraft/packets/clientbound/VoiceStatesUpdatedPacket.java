package dev.lu15.voicechat.network.minecraft.packets.clientbound;

import dev.lu15.voicechat.VoiceChat;
import dev.lu15.voicechat.network.NetworkTypes;
import dev.lu15.voicechat.network.minecraft.Packet;
import dev.lu15.voicechat.network.minecraft.VoiceState;
import net.kyori.adventure.key.Key;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.NetworkBufferTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public record VoiceStatesUpdatedPacket(@NotNull Collection<VoiceState> states)
        implements Packet<VoiceStatesUpdatedPacket> {

    public static final @NotNull Key IDENTIFIER = VoiceChat.key("states");
    public static final @NotNull NetworkBuffer.Type<VoiceStatesUpdatedPacket> SERIALIZER = NetworkBufferTemplate.template(
            NetworkTypes.IntIndexedCollection(VoiceState.NETWORK_TYPE), VoiceStatesUpdatedPacket::states,
            VoiceStatesUpdatedPacket::new
    );

    @Override
    public @NotNull Key id() {
        return IDENTIFIER;
    }

    @Override
    public NetworkBuffer.@NotNull Type<VoiceStatesUpdatedPacket> serializer() {
        return SERIALIZER;
    }

}
