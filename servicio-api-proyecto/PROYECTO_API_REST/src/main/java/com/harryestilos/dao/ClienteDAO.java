package com.harryestilos.dao;

import com.harryestilos.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteDAO {

    private static final Map<Integer, Cliente> MEMORIA = new ConcurrentHashMap<>();
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    static {
        MEMORIA.put(1, new Cliente(1, "Carlos Mendoza", "3109876543", "carlos.mendoza@email.com"));
        MEMORIA.put(2, new Cliente(2, "Andres Felipe Perez", "3154433221", "andres.perez@email.com"));
        MEMORIA.put(3, new Cliente(3, "Juan David Rojas", "3201122334", "juan.rojas@email.com"));
        SECUENCIA.set(4);
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM clientes ORDER BY id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Cliente mem = MEMORIA.get(id);
                String tel = (mem != null && mem.getTelefono() != null) ? mem.getTelefono() : "3100000000";
                String mail = (mem != null && mem.getCorreo() != null) ? mem.getCorreo() : "cliente@email.com";
                lista.add(new Cliente(id, rs.getString("nombre"), tel, mail));
            }
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (SQLException e) {
            // Fallback
        }

        return new ArrayList<>(MEMORIA.values());
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT id, nombre FROM clientes WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente mem = MEMORIA.get(id);
                    String tel = (mem != null && mem.getTelefono() != null) ? mem.getTelefono() : "3100000000";
                    String mail = (mem != null && mem.getCorreo() != null) ? mem.getCorreo() : "cliente@email.com";
                    return new Cliente(rs.getInt("id"), rs.getString("nombre"), tel, mail);
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        return MEMORIA.get(id);
    }

    public int obtenerOCrear(String nombre, String telefono, String correo) {
        String sqlBuscar = "SELECT id FROM clientes WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(?))";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuscar)) {

            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

            String sqlInsert = "INSERT INTO clientes (nombre) VALUES (?)";
            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, nombre.trim());
                stmtInsert.executeUpdate();
                try (ResultSet rsGen = stmtInsert.getGeneratedKeys()) {
                    if (rsGen.next()) {
                        int idGen = rsGen.getInt(1);
                        MEMORIA.put(idGen, new Cliente(idGen, nombre.trim(), telefono, correo));
                        return idGen;
                    }
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        for (Cliente c : MEMORIA.values()) {
            if (c.getNombre().equalsIgnoreCase(nombre.trim())) {
                return c.getId();
            }
        }

        int idNuevo = SECUENCIA.getAndIncrement();
        Cliente nuevo = new Cliente(idNuevo, nombre.trim(), telefono, correo);
        MEMORIA.put(idNuevo, nuevo);
        return idNuevo;
    }

    public boolean actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setInt(2, c.getId());
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                MEMORIA.put(c.getId(), c);
                return true;
            }
        } catch (SQLException e) {
            // Fallback
        }

        if (MEMORIA.containsKey(c.getId())) {
            MEMORIA.put(c.getId(), c);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

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
