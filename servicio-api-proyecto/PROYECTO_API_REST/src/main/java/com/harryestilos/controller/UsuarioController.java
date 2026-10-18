package com.harryestilos.controller;

import com.harryestilos.dao.UsuarioDAO;
import com.harryestilos.dto.LoginRequestDTO;
import com.harryestilos.dto.RegistroRequestDTO;
import com.harryestilos.model.Usuario;
import com.harryestilos.util.PasswordUtil;
import com.harryestilos.util.RegistroValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "5. Módulo de Autenticación y Seguridad", description = "Servicios web para inicio de sesión y registro de usuarios con contraseñas encriptadas")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Operation(summary = "Inicio de sesión (JSON)", description = "Autentica credenciales recibiendo JSON en el cuerpo de la petición")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación satisfactoria"),
            @ApiResponse(responseCode = "401", description = "Error en la autenticación (clave o usuario inválido)"),
            @ApiResponse(responseCode = "400", description = "Parámetros incompletos")
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginJson(@RequestBody LoginRequestDTO dto) {
        return procesarLogin(dto.getUsuario(), dto.getPassword());
    }

    @Operation(summary = "Inicio de sesión (Form / URL-Encoded)", description = "Autentica credenciales recibiendo parámetros x-www-form-urlencoded")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> loginForm(@RequestParam(required = false) String usuario,
                                       @RequestParam(required = false) String password) {
        return procesarLogin(usuario, password);
    }

    @Operation(summary = "Registro de usuario (JSON)", description = "Registra un nuevo usuario con contraseña segura")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "409", description = "El correo ya se encuentra registrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o clave menor a 6 caracteres")
    })
    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registroJson(@RequestBody RegistroRequestDTO dto) {
        return procesarRegistro(dto.getNombre(), dto.getUsuario(), dto.getPassword());
    }

    @Operation(summary = "Registro de usuario (Form / URL-Encoded)", description = "Registra un nuevo usuario recibiendo parámetros de formulario")
    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> registroForm(@RequestParam(required = false) String nombre,
                                          @RequestParam(required = false) String usuario,
                                          @RequestParam(required = false) String password) {
        return procesarRegistro(nombre, usuario, password);
    }

    private ResponseEntity<?> procesarLogin(String usuario, String password) {
        if (usuario == null || usuario.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Los parámetros 'usuario' y 'password' son obligatorios."));
        }

        Usuario encontrado = usuarioDAO.buscarPorCorreo(usuario.trim().toLowerCase());
        boolean credencialesValidas = encontrado != null
                && PasswordUtil.verificar(password, encontrado.getSalt(), encontrado.getPasswordHash());

        if (!credencialesValidas) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Error en la autenticación."));
        }

        if (!encontrado.isActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Error en la autenticación: la cuenta se encuentra inactiva."));
        }

        return ResponseEntity.ok(Map.of(
                "mensaje", "Autenticación satisfactoria.",
                "usuario", encontrado.getCorreo(),
                "nombre", encontrado.getNombre(),
                "rol", encontrado.getRol()
        ));
    }

    private ResponseEntity<?> procesarRegistro(String nombre, String usuario, String password) {
        if (nombre == null || nombre.isBlank()
                || usuario == null || usuario.isBlank()
                || password == null || password.length() < 6) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Completa 'nombre', 'usuario' y 'password' (mínimo 6 caracteres)."));
        }

        if (!RegistroValidator.longitudValida(nombre, RegistroValidator.LONGITUD_MAXIMA_NOMBRE)
                || !RegistroValidator.longitudValida(usuario, RegistroValidator.LONGITUD_MAXIMA_CORREO)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El nombre y el usuario no pueden superar los 150 caracteres."));
        }

        if (!RegistroValidator.correoTieneFormatoValido(usuario)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El campo 'usuario' debe ser un correo con formato válido (ejemplo: nombre@dominio.com)."));
        }

        String correo = usuario.trim().toLowerCase();
        if (usuarioDAO.buscarPorCorreo(correo) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ya existe una cuenta registrada con ese correo."));
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
                    .body(Map.of("error", "No fue posible completar el registro."));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensaje", "Usuario registrado correctamente.",
                        "id", idGenerado,
                        "usuario", correo
                ));
    }
}
