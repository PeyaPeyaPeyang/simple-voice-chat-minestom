package dev.lu15.voicechat.network.minecraft.packets.clientbound;

import dev.lu15.voicechat.VoiceChat;
import dev.lu15.voicechat.network.minecraft.Packet;
import dev.lu15.voicechat.network.minecraft.VoiceState;
import net.kyori.adventure.key.Key;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.NetworkBufferTemplate;
import org.jetbrains.annotations.NotNull;

public record VoiceStateUpdatedPacket(@NotNull VoiceState state) implements Packet<VoiceStateUpdatedPacket> {

    public static final @NotNull Key IDENTIFIER = VoiceChat.key("state");
    public static final @NotNull NetworkBuffer.Type<VoiceStateUpdatedPacket> SERIALIZER = NetworkBufferTemplate.template(
            VoiceState.NETWORK_TYPE, VoiceStateUpdatedPacket::state,
            VoiceStateUpdatedPacket::new
    );

    @Override
    public @NotNull Key id() {
        return IDENTIFIER;
    }

    @Override
    public NetworkBuffer.@NotNull Type<VoiceStateUpdatedPacket> serializer() {
        return SERIALIZER;
    }

}
