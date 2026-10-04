package com.harryestilos.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad de dominio para el manejo seguro de contraseñas del módulo de
 * autenticación (inicio de sesión / registro de usuarios).
 *
 * No almacena contraseñas en texto plano: cada usuario tiene una sal (salt)
 * aleatoria única y se guarda únicamente el hash SHA-256 de
 * (contraseña + sal). Es una clase de lógica pura (sin JDBC ni Servlet API),
 * por lo que se puede probar de forma unitaria sin base de datos ni Tomcat.
 *
 * Evidencia: GA7-220501096-AA3-EV01 - Codificación de módulos del software
 * stand-alone, web y móvil (módulo: inicio de sesión / registro de usuarios).
 */
public final class PasswordUtil {

    private static final int SALT_LENGTH_BYTES = 16;
    private static final String ALGORITMO_HASH = "SHA-256";

    private PasswordUtil() {
        // Clase de utilidad: no debe instanciarse.
    }

    /**
     * Genera una sal (salt) aleatoria y única, codificada en Base64, para
     * usarse al registrar un nuevo usuario.
     */
    public static String generarSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Calcula el hash SHA-256 de una contraseña combinada con su sal.
     * El resultado es lo único que debe persistirse en la base de datos.
     *
     * @param password contraseña en texto plano ingresada por el usuario
     * @param saltBase64 sal (Base64) asociada a ese usuario
     * @return hash de la contraseña, codificado en Base64
     */
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

    /**
     * Verifica si una contraseña en texto plano corresponde al hash
     * almacenado para un usuario, dado su sal.
     *
     * @param password        contraseña ingresada en el formulario de login
     * @param saltBase64      sal almacenada para ese usuario
     * @param hashAlmacenado  hash guardado en la base de datos para ese usuario
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public static boolean verificar(String password, String saltBase64, String hashAlmacenado) {
        if (password == null || saltBase64 == null || hashAlmacenado == null) {
            return false;
        }
        String hashCalculado = hashPassword(password, saltBase64);
        return comparacionSegura(hashCalculado, hashAlmacenado);
    }

    /**
     * Compara dos cadenas en tiempo constante para reducir el riesgo de
     * ataques de temporización (timing attacks) al validar contraseñas.
     */
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
