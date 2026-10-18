package com.harryestilos.dao;

import com.harryestilos.model.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicioDAO {

    private static final Map<Integer, Servicio> MEMORIA = new ConcurrentHashMap<>();
    private static final AtomicInteger SECUENCIA = new AtomicInteger(4);

    static {
        MEMORIA.put(1, new Servicio(1, "Corte Clasico", "corte", 25000, 45));
        MEMORIA.put(2, new Servicio(2, "Barba", "barba", 14000, 25));
        MEMORIA.put(3, new Servicio(3, "Paquete Completo", "paquete", 35000, 70));
    }

    public List<Servicio> listarTodos() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, clave, precio, duracion_minutos FROM servicios ORDER BY id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Servicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"),
                        rs.getInt("precio"),
                        rs.getInt("duracion_minutos")
                ));
            }
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (SQLException e) {
            // Fallback
        }

        return new ArrayList<>(MEMORIA.values());
    }

    public Servicio buscarPorId(int id) {
        String sql = "SELECT id, nombre, clave, precio, duracion_minutos FROM servicios WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Servicio(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("clave"),
                            rs.getInt("precio"),
                            rs.getInt("duracion_minutos")
                    );
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        return MEMORIA.get(id);
    }

    public int insertar(Servicio s) {
        String sql = "INSERT INTO servicios (nombre, clave, precio, duracion_minutos) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, s.getNombre());
            stmt.setString(2, s.getClave() != null ? s.getClave() : s.getNombre().toLowerCase().replaceAll("\\s+", "_"));
            stmt.setInt(3, s.getPrecio());
            stmt.setInt(4, s.getDuracionMinutos());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGen = rs.getInt(1);
                    s.setId(idGen);
                    MEMORIA.put(idGen, s);
                    return idGen;
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        int idNuevo = SECUENCIA.getAndIncrement();
        s.setId(idNuevo);
        if (s.getClave() == null) {
            s.setClave(s.getNombre().toLowerCase().replaceAll("\\s+", "_"));
        }
        MEMORIA.put(idNuevo, s);
        return idNuevo;
    }

    public boolean actualizar(Servicio s) {
        String sql = "UPDATE servicios SET nombre = ?, clave = ?, precio = ?, duracion_minutos = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNombre());
            stmt.setString(2, s.getClave());
            stmt.setInt(3, s.getPrecio());
            stmt.setInt(4, s.getDuracionMinutos());
            stmt.setInt(5, s.getId());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                MEMORIA.put(s.getId(), s);
                return true;
            }
        } catch (SQLException e) {
            // Fallback
        }

        if (MEMORIA.containsKey(s.getId())) {
            MEMORIA.put(s.getId(), s);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE id = ?";

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
