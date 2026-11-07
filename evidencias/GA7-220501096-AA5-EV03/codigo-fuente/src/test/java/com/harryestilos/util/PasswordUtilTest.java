package com.harryestilos.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de PasswordUtil (lógica pura, sin Spring ni JDBC),
 * igual que PasswordUtilTest del módulo principal (GA7-220501096-AA3-EV01).
 * Se ejecutan con "mvn test".
 */
class PasswordUtilTest {

    @Test
    void generarSaltProduceValoresDiferentesEnCadaLlamado() {
        assertNotEquals(PasswordUtil.generarSalt(), PasswordUtil.generarSalt());
    }

    @Test
    void hashPasswordEsDeterministicoParaLaMismaSal() {
        String salt = PasswordUtil.generarSalt();
        assertEquals(
                PasswordUtil.hashPassword("claveSegura123", salt),
                PasswordUtil.hashPassword("claveSegura123", salt));
    }

    @Test
    void verificarAceptaLaContrasenaCorrecta() {
        String salt = PasswordUtil.generarSalt();
        String hash = PasswordUtil.hashPassword("claveSegura123", salt);
        assertTrue(PasswordUtil.verificar("claveSegura123", salt, hash));
    }

    @Test
    void verificarRechazaLaContrasenaIncorrecta() {
        String salt = PasswordUtil.generarSalt();
        String hash = PasswordUtil.hashPassword("claveSegura123", salt);
        assertFalse(PasswordUtil.verificar("otraClave", salt, hash));
    }
}
