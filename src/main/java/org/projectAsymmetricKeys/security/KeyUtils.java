package org.projectAsymmetricKeys.security;

import java.io.InputStream;
import java.security.KeyFactory;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
    private KeyUtils() {
    }

    private static PrivateKey loadPrivateKey(final String pemPath) throws Exception {
        final String key = readKeyFromResources(pemPath)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\\s", "");

        final byte[] decoded = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(final String pemPath) throws Exception {
        final String key = readKeyFromResources(pemPath).replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        final byte[] decoded = Base64.getDecoder().decode(key);
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    private static String readKeyFromResources(final String path) throws Exception {
        try (final InputStream inputStream = KeyUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("key not found: " + path);
            }
            return new String(inputStream.readAllBytes());
        }
    }
}
