package dev.lu15.voicechat.network.voice;

import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;

public record RawPacket(
        byte @NotNull [] data,
        @NotNull SocketAddress address,
        long timestamp
) {
}
