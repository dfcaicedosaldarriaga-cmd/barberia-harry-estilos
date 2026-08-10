package com.harryestilos.dao;

import com.harryestilos.model.Cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    public int insertar(Cita c) {
        String sql = "INSERT INTO citas (cliente_id, barbero_id, servicio_id, fecha, hora) VALUES (?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, c.getClienteId());
            stmt.setInt(2, c.getBarberoId());
            stmt.setInt(3, c.getServicioId());
            stmt.setString(4, c.getFecha());
            stmt.setString(5, c.getHora());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    c.setId(idGenerado);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    public List<Cita> listarTodas() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.cliente_id, c.barbero_id, c.servicio_id, c.fecha, c.hora, " +
                     "cl.nombre AS cliente_nombre, " +
                     "b.nombre AS barbero_nombre, " +
                     "s.nombre AS servicio_nombre, " +
                     "s.precio AS servicio_precio, " +
                     "s.duracion_minutos AS servicio_duracion " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.cliente_id = cl.id " +
                     "JOIN barberos b ON c.barbero_id = b.id " +
                     "JOIN servicios s ON c.servicio_id = s.id " +
                     "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cita c = MapearCita(rs);
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Cita buscarPorId(int id) {
        String sql = "SELECT c.id, c.cliente_id, c.barbero_id, c.servicio_id, c.fecha, c.hora, " +
                     "cl.nombre AS cliente_nombre, " +
                     "b.nombre AS barbero_nombre, " +
                     "s.nombre AS servicio_nombre, " +
                     "s.precio AS servicio_precio, " +
                     "s.duracion_minutos AS servicio_duracion " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.cliente_id = cl.id " +
                     "JOIN barberos b ON c.barbero_id = b.id " +
                     "JOIN servicios s ON c.servicio_id = s.id " +
                     "WHERE c.id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return MapearCita(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void actualizar(Cita c) {
        String sql = "UPDATE citas SET cliente_id = ?, barbero_id = ?, servicio_id = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getClienteId());
            stmt.setInt(2, c.getBarberoId());
            stmt.setInt(3, c.getServicioId());
            stmt.setString(4, c.getFecha());
            stmt.setString(5, c.getHora());
            stmt.setInt(6, c.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cita MapearCita(ResultSet rs) throws SQLException {
        Cita c = new Cita();
        c.setId(rs.getInt("id"));
        c.setClienteId(rs.getInt("cliente_id"));
        c.setBarberoId(rs.getInt("barbero_id"));
        c.setServicioId(rs.getInt("servicio_id"));
        c.setFecha(rs.getString("fecha"));
        c.setHora(rs.getString("hora"));
        c.setClienteNombre(rs.getString("cliente_nombre"));
        c.setBarberoNombre(rs.getString("barbero_nombre"));
        c.setServicioNombre(rs.getString("servicio_nombre"));
        c.setServicioPrecio(rs.getInt("servicio_precio"));
        c.setServicioDuracion(rs.getInt("servicio_duracion"));
        return c;
    }
}
