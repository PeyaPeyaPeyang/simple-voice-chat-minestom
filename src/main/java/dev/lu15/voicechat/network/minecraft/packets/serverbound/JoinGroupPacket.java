package dev.lu15.voicechat.network.minecraft.packets.serverbound;

import dev.lu15.voicechat.VoiceChat;
import dev.lu15.voicechat.network.minecraft.Packet;
import net.kyori.adventure.key.Key;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.NetworkBufferTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record JoinGroupPacket(
        @NotNull UUID group,
        @Nullable String password
) implements Packet<JoinGroupPacket> {

    public static final @NotNull Key IDENTIFIER = VoiceChat.key("set_group");
    public static final @NotNull NetworkBuffer.Type<JoinGroupPacket> SERIALIZER = NetworkBufferTemplate.template(
            NetworkBuffer.UUID, JoinGroupPacket::group,
            NetworkBuffer.STRING.optional(), JoinGroupPacket::password,
            JoinGroupPacket::new
    );

    @Override
    public @NotNull Key id() {
        return IDENTIFIER;
    }

    @Override
    public NetworkBuffer.@NotNull Type<JoinGroupPacket> serializer() {
        return SERIALIZER;
    }

}
