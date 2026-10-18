package com.harryestilos.dao;

import com.harryestilos.model.Usuario;
import com.harryestilos.util.PasswordUtil;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UsuarioDAO {

    private static final Map<String, Usuario> MEMORIA = new ConcurrentHashMap<>();
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    static {
        String saltAdmin = PasswordUtil.generarSalt();
        String hashAdmin = PasswordUtil.hashPassword("admin123", saltAdmin);
        Usuario admin = new Usuario(
                SECUENCIA.getAndIncrement(),
                "Administrador Harry Estilos",
                "admin@harryestilos.com",
                hashAdmin,
                saltAdmin,
                "ADMINISTRADOR"
        );
        admin.setActivo(true);
        MEMORIA.put(admin.getCorreo().toLowerCase(), admin);
    }

    public int registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, correo, password_hash, salt, rol, activo) VALUES (?, ?, ?, ?, ?, ?)";

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
                    int idGen = rs.getInt(1);
                    u.setId(idGen);
                    MEMORIA.put(u.getCorreo().toLowerCase(), u);
                    return idGen;
                }
            }
        } catch (SQLException e) {
            // Fallback
            String key = u.getCorreo().trim().toLowerCase();
            if (MEMORIA.containsKey(key)) {
                return -1;
            }
            int nuevoId = SECUENCIA.getAndIncrement();
            u.setId(nuevoId);
            MEMORIA.put(key, u);
            return nuevoId;
        }

        return -1;
    }

    public Usuario buscarPorCorreo(String correo) {
        if (correo == null) {
            return null;
        }
        String key = correo.trim().toLowerCase();
        String sql = "SELECT id, nombre, correo, password_hash, salt, rol, activo FROM usuarios WHERE correo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
        } catch (SQLException e) {
            // Fallback
            return MEMORIA.get(key);
        }

        return MEMORIA.get(key);
    }
}
