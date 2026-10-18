package com.harryestilos.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {

    private static final int SALT_LENGTH_BYTES = 16;
    private static final String ALGORITMO_HASH = "SHA-256";

    private PasswordUtil() {
    }

    public static String generarSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String saltBase64) {
        if (password == null || saltBase64 == null) {
            throw new IllegalArgumentException("La contraseña y la sal no pueden ser nulas");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITMO_HASH);
            digest.update(Base64.getDecoder().decode(saltBase64));
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo de hash no disponible: " + ALGORITMO_HASH, e);
        }
    }

    public static boolean verificar(String password, String saltBase64, String hashAlmacenado) {
        if (password == null || saltBase64 == null || hashAlmacenado == null) {
            return false;
        }
        String hashCalculado = hashPassword(password, saltBase64);
        return comparacionSegura(hashCalculado, hashAlmacenado);
    }

    private static boolean comparacionSegura(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int diferencia = 0;
        for (int i = 0; i < a.length(); i++) {
            diferencia |= a.charAt(i) ^ b.charAt(i);
        }
        return diferencia == 0;
    }
}
