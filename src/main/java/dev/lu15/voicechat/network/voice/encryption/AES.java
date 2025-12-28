package dev.lu15.voicechat.network.voice.encryption;

import net.minestom.server.network.NetworkBuffer;
import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public final class AES {

    private static final @NotNull Random RANDOM = new SecureRandom();
    private static final @NotNull String ALGO = "AES/GCM/NoPadding";

     private static final ThreadLocal<Cipher> ENCRYPT_CIPHER = ThreadLocal.withInitial(() -> {
        try {
            return Cipher.getInstance(ALGO);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    });

    static final ThreadLocal<Cipher> DECRYPT_CIPHER = ThreadLocal.withInitial(() -> {
        try {
            return Cipher.getInstance(ALGO);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    });

    private static final int KEY_LENGTH = 16;
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private AES() {
    }

    public static byte @NotNull [] getBytesFromUuid(@NotNull UUID uuid) {
        NetworkBuffer buffer = NetworkBuffer.staticBuffer(KEY_LENGTH);
        buffer.write(NetworkBuffer.UUID, uuid);
        byte[] bytes = new byte[KEY_LENGTH];
        buffer.copyTo(0, bytes, 0, KEY_LENGTH);
        return bytes;
    }

    private static byte @NotNull [] generateIv() {
        byte[] iv = new byte[IV_LENGTH];
        RANDOM.nextBytes(iv);
        return iv;
    }

    private static @NotNull SecretKeySpec createKey(@NotNull UUID secret) {
        return new SecretKeySpec(getBytesFromUuid(secret), "AES");
    }

    public static byte @NotNull [] encrypt(@NotNull UUID secret,
                                           byte @NotNull [] data)
            throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] iv = generateIv();
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

        Cipher cipher = ENCRYPT_CIPHER.get();
        cipher.init(Cipher.ENCRYPT_MODE, createKey(secret), spec);

        byte[] encrypted = cipher.doFinal(data);

        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
        return result;
    }

    public static byte @NotNull [] decrypt(@NotNull UUID secret,
                                           byte @NotNull [] result)
            throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(result, 0, iv, 0, iv.length);

        byte[] data = new byte[result.length - iv.length];
        System.arraycopy(result, iv.length, data, 0, data.length);

        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

        Cipher cipher = DECRYPT_CIPHER.get();
        cipher.init(Cipher.DECRYPT_MODE, createKey(secret), spec);

        return cipher.doFinal(data);
    }
}
