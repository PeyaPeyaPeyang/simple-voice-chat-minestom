package dev.lu15.voicechat.network.minecraft.packets.clientbound;

import dev.lu15.voicechat.VoiceChat;
import dev.lu15.voicechat.network.minecraft.Packet;
import net.kyori.adventure.key.Key;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.NetworkBufferTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record GroupChangedPacket(
        @Nullable UUID group,
        boolean incorrectPassword
) implements Packet<GroupChangedPacket> {

    public static final @NotNull Key IDENTIFIER = VoiceChat.key("joined_group");
    @SuppressWarnings("DataFlowIssue")
    public static final @NotNull NetworkBuffer.Type<GroupChangedPacket> SERIALIZER = NetworkBufferTemplate.template(
            NetworkBuffer.OPT_UUID, GroupChangedPacket::group,
            NetworkBuffer.BOOLEAN, GroupChangedPacket::incorrectPassword,
            GroupChangedPacket::new
    );

    @Override
    public @NotNull Key id() {
        return IDENTIFIER;
    }

    @Override
    public NetworkBuffer.@NotNull Type<GroupChangedPacket> serializer() {
        return SERIALIZER;
    }

}
