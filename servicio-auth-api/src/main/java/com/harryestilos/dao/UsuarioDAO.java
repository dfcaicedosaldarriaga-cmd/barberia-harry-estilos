package com.harryestilos.dao;

import com.harryestilos.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Acceso a datos del módulo de autenticación (tabla "usuarios").
 * Sigue el mismo patrón JDBC usado en el resto del proyecto (CitaDAO,
 * ServicioDAO, BarberoDAO): sentencias preparadas para prevenir inyección
 * SQL, try-with-resources para el manejo de conexiones.
 *
 * Evidencia: GA7-220501096-AA3-EV01 / GA7-220501096-AA3-EV02.
 *
 * NOTA (GA7-220501096-AA5-EV01): esta clase es una copia intencional,
 * carácter por carácter, de com.harryestilos.dao.UsuarioDAO del módulo
 * principal (WAR JSP/Servlet). Se duplica aquí porque este servicio REST
 * vive en un módulo Maven aparte (Spring Boot, empaquetado JAR) para no
 * mezclar el despliegue web tradicional con el servicio API. Ambas clases
 * operan sobre la misma tabla "usuarios" de la misma base de datos MySQL,
 * así que no hay divergencia de datos entre el login web y el login API.
 */
public class UsuarioDAO {

    /**
     * Registra un nuevo usuario. La contraseña ya debe llegar convertida a
     * hash + sal (ver {@link com.harryestilos.util.PasswordUtil}); este DAO
     * nunca recibe ni almacena contraseñas en texto plano. El usuario
     * queda activo por defecto salvo que se indique lo contrario.
     *
     * @return el id generado, o -1 si no se pudo insertar (por ejemplo,
     *         si el correo ya existe, gracias a la restricción UNIQUE).
     */
    public int registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, correo, password_hash, salt, rol, activo) VALUES (?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getPasswordHash());
            stmt.setString(4, u.getSalt());
            stmt.setString(5, u.getRol());
            stmt.setBoolean(6, u.isActivo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    u.setId(idGenerado);
                }
            }

        } catch (SQLException e) {
            // Código 1062 en MySQL: entrada duplicada (correo ya registrado).
            e.printStackTrace();
        }

        return idGenerado;
    }

    /**
     * Busca un usuario por su correo electrónico. Se usa tanto para el
     * inicio de sesión (validar la contraseña y el estado activo) como
     * para el registro (verificar que el correo no esté ya en uso).
     */
    public Usuario buscarPorCorreo(String correo) {
        String sql = "SELECT id, nombre, correo, password_hash, salt, rol, activo FROM usuarios WHERE correo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setCorreo(rs.getString("correo"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setSalt(rs.getString("salt"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
