package com.harryestilos.api;

import com.harryestilos.dao.UsuarioDAO;
import com.harryestilos.model.Usuario;
import com.harryestilos.util.PasswordUtil;
import com.harryestilos.util.RegistroValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST del servicio web de autenticación
 * (GA7-220501096-AA5-EV01).
 *
 * Es la versión API del módulo de inicio de sesión y registro que ya
 * existe como flujo web tradicional (JSP/Servlet, GA7-220501096-AA3-EV01
 * y GA7-220501096-AA3-EV02): no se reimplementa la lógica de negocio, se
 * reutilizan las mismas clases {@link UsuarioDAO}, {@link Usuario},
 * {@link PasswordUtil} y {@link RegistroValidator} (copiadas en este
 * módulo, ver nota en UsuarioDAO), contra la misma tabla "usuarios" de
 * MySQL. Un usuario creado desde el formulario web puede autenticarse
 * aquí, y un usuario registrado aquí puede iniciar sesión desde el
 * formulario web.
 *
 * Recibe los datos como parámetros de formulario (@RequestParam), no como
 * cuerpo JSON: así se prueba directamente desde Postman usando
 * "x-www-form-urlencoded", igual que se explicó en la evidencia.
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * POST /api/usuario/login
     * Parámetros: usuario (correo electrónico), password.
     *
     * Responde "Autenticación satisfactoria." (200) si las credenciales
     * son correctas y la cuenta está activa, o "Error en la
     * autenticación." (401) en caso contrario.
     */
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(@RequestParam String usuario,
                                                 @RequestParam String password) {
        if (usuario == null || usuario.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Los parámetros 'usuario' y 'password' son obligatorios.");
        }

        Usuario encontrado = usuarioDAO.buscarPorCorreo(usuario.trim().toLowerCase());
        boolean credencialesValidas = encontrado != null
                && PasswordUtil.verificar(password, encontrado.getSalt(), encontrado.getPasswordHash());

        if (!credencialesValidas) {
            // Mensaje genérico: no revela si el usuario existe o si falló
            // la contraseña (misma práctica de seguridad que LoginServlet
            // del módulo web).
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error en la autenticación.");
        }

        if (!encontrado.isActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error en la autenticación: la cuenta se encuentra inactiva.");
        }

        return ResponseEntity.ok("Autenticación satisfactoria.");
    }

    /**
     * POST /api/usuario/registro
     * Parámetros: nombre, usuario (correo electrónico), password.
     *
     * Responde "Usuario registrado correctamente." (201) si el registro
     * fue exitoso, o un mensaje de error (400/409) en caso contrario.
     */
    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestParam String nombre,
                                             @RequestParam String usuario,
                                             @RequestParam String password) {
        if (nombre == null || nombre.isBlank()
                || usuario == null || usuario.isBlank()
                || password == null || password.length() < 6) {
            return ResponseEntity.badRequest()
                    .body("Completa 'nombre', 'usuario' y 'password' (mínimo 6 caracteres).");
        }

        if (!RegistroValidator.longitudValida(nombre, RegistroValidator.LONGITUD_MAXIMA_NOMBRE)
                || !RegistroValidator.longitudValida(usuario, RegistroValidator.LONGITUD_MAXIMA_CORREO)) {
            return ResponseEntity.badRequest()
                    .body("El nombre y el usuario no pueden superar los 150 caracteres.");
        }

        if (!RegistroValidator.correoTieneFormatoValido(usuario)) {
            return ResponseEntity.badRequest()
                    .body("El campo 'usuario' debe ser un correo con formato válido (ejemplo: nombre@dominio.com).");
        }

        String correo = usuario.trim().toLowerCase();
        if (usuarioDAO.buscarPorCorreo(correo) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe una cuenta registrada con ese correo.");
        }

        String salt = PasswordUtil.generarSalt();
        String hash = PasswordUtil.hashPassword(password, salt);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre.trim());
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setPasswordHash(hash);
        nuevoUsuario.setSalt(salt);
        nuevoUsuario.setRol("CLIENTE");
        nuevoUsuario.setActivo(true);

        int idGenerado = usuarioDAO.registrar(nuevoUsuario);

        if (idGenerado <= 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible completar el registro.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado correctamente.");
    }
}
