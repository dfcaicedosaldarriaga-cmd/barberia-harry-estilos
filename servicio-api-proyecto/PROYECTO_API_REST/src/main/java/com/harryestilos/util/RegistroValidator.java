package com.harryestilos.util;

import java.util.regex.Pattern;

public final class RegistroValidator {

    private static final Pattern PATRON_CORREO =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");

    public static final int LONGITUD_MAXIMA_NOMBRE = 150;
    public static final int LONGITUD_MAXIMA_CORREO = 150;

    private RegistroValidator() {
    }

    public static boolean correoTieneFormatoValido(String correo) {
        if (correo == null) {
            return false;
        }
        return PATRON_CORREO.matcher(correo.trim()).matches();
    }

    public static boolean longitudValida(String texto, int longitudMaxima) {
        if (texto == null) {
            return false;
        }
        String limpio = texto.trim();
        return !limpio.isEmpty() && limpio.length() <= longitudMaxima;
    }
}
