package dev.lu15.voicechat.network.minecraft.packets.clientbound;

import dev.lu15.voicechat.VoiceChat;
import dev.lu15.voicechat.network.minecraft.Packet;
import net.kyori.adventure.key.Key;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.NetworkBufferTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record VoiceStateRemovedPacket(@NotNull UUID uuid) implements Packet<VoiceStateRemovedPacket> {

    public static final @NotNull Key IDENTIFIER = VoiceChat.key("remove_state");
    public static final @NotNull NetworkBuffer.Type<VoiceStateRemovedPacket> SERIALIZER = NetworkBufferTemplate.template(
            NetworkBuffer.UUID, VoiceStateRemovedPacket::uuid,
            VoiceStateRemovedPacket::new
    );

    @Override
    public @NotNull Key id() {
        return IDENTIFIER;
    }

    @Override
    public NetworkBuffer.@NotNull Type<VoiceStateRemovedPacket> serializer() {
        return SERIALIZER;
    }

}
