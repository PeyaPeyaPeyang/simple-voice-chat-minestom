package dev.lu15.voicechat.network.voice.packets;

import dev.lu15.voicechat.network.voice.VoicePacket;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.NetworkBufferTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record AuthenticatePacket(
        @NotNull UUID player,
        @NotNull UUID secret
) implements VoicePacket<AuthenticatePacket> {

    public static final @NotNull NetworkBuffer.Type<AuthenticatePacket> SERIALIZER = NetworkBufferTemplate.template(
            NetworkBuffer.UUID, AuthenticatePacket::player,
            NetworkBuffer.UUID, AuthenticatePacket::secret,
            AuthenticatePacket::new
    );

    @Override
    public int id() {
        return 0x5;
    }

    @Override
    public NetworkBuffer.@NotNull Type<AuthenticatePacket> serializer() {
        return SERIALIZER;
    }

}
