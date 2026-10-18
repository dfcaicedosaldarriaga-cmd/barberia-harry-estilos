package com.harryestilos.dao;

import com.harryestilos.model.Barbero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberoDAO {

    private static final Map<Integer, Barbero> MEMORIA = new ConcurrentHashMap<>();
    private static final AtomicInteger SECUENCIA = new AtomicInteger(4);

    static {
        MEMORIA.put(1, new Barbero(1, "Harry"));
        MEMORIA.put(2, new Barbero(2, "Andres"));
        MEMORIA.put(3, new Barbero(3, "Camilo"));
    }

    public List<Barbero> listarTodos() {
        List<Barbero> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM barberos ORDER BY id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Barbero(rs.getInt("id"), rs.getString("nombre")));
            }
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (SQLException e) {
            // Usar persistencia en memoria ante ausencia de MySQL
        }

        return new ArrayList<>(MEMORIA.values());
    }

    public Barbero buscarPorId(int id) {
        String sql = "SELECT id, nombre FROM barberos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Barbero(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        return MEMORIA.get(id);
    }

    public int insertar(Barbero b) {
        String sql = "INSERT INTO barberos (nombre) VALUES (?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, b.getNombre());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGen = rs.getInt(1);
                    b.setId(idGen);
                    MEMORIA.put(idGen, b);
                    return idGen;
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        int idNuevo = SECUENCIA.getAndIncrement();
        b.setId(idNuevo);
        MEMORIA.put(idNuevo, b);
        return idNuevo;
    }

    public boolean actualizar(Barbero b) {
        String sql = "UPDATE barberos SET nombre = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, b.getNombre());
            stmt.setInt(2, b.getId());
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                MEMORIA.put(b.getId(), b);
                return true;
            }
        } catch (SQLException e) {
            // Fallback
        }

        if (MEMORIA.containsKey(b.getId())) {
            MEMORIA.put(b.getId(), b);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM barberos WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                MEMORIA.remove(id);
                return true;
            }
        } catch (SQLException e) {
            // Fallback
        }

        return MEMORIA.remove(id) != null;
    }
}
