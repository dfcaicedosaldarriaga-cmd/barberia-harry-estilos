package com.harryestilos.util;

import java.util.regex.Pattern;

/**
 * Lógica pura (sin JDBC ni Servlet API) para validar los datos del
 * formulario de registro de usuarios: formato de correo electrónico y
 * longitud máxima de los campos de texto, alineada con las columnas
 * VARCHAR(150) de la tabla "usuarios".
 *
 * Estas validaciones surgieron al probar el módulo de inicio de sesión y
 * registro contra el caso de uso CU-01 "Registrarse" (evidencia
 * GA7-220501096-AA3-EV02): el registro original no comprobaba el formato
 * del correo ni la longitud de los campos antes de insertar en la base de
 * datos.
 */
public final class RegistroValidator {

    // Patrón simple de correo: texto@texto.texto, sin espacios ni arrobas
    // repetidas. No pretende cubrir el 100% del RFC 5321, pero sí detectar
    // los casos de entrada inválida más comunes en pruebas de caja negra.
    private static final Pattern PATRON_CORREO =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");

    public static final int LONGITUD_MAXIMA_NOMBRE = 150;
    public static final int LONGITUD_MAXIMA_CORREO = 150;

    private RegistroValidator() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Verifica que el correo tenga un formato válido (usuario@dominio.tld).
     * Devuelve false ante null, cadenas vacías o texto sin arroba/dominio.
     */
    public static boolean correoTieneFormatoValido(String correo) {
        if (correo == null) {
            return false;
        }
        return PATRON_CORREO.matcher(correo.trim()).matches();
    }

    /**
     * Verifica que un texto no sea nulo/vacío y no exceda la longitud
     * máxima indicada (pensada para alinear con el límite de la columna
     * en la base de datos y evitar una excepción SQL por truncamiento).
     */
    public static boolean longitudValida(String texto, int longitudMaxima) {
        if (texto == null) {
            return false;
        }
        String limpio = texto.trim();
        return !limpio.isEmpty() && limpio.length() <= longitudMaxima;
    }
}
