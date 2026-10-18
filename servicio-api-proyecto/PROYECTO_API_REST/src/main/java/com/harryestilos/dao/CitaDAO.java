package com.harryestilos.dao;

import com.harryestilos.model.Barbero;
import com.harryestilos.model.Cita;
import com.harryestilos.model.Cliente;
import com.harryestilos.model.Servicio;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CitaDAO {

    private static final Map<Integer, Cita> MEMORIA = new ConcurrentHashMap<>();
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    static {
        // Citas precargadas de ejemplo para pruebas en Postman y Swagger
        Cita c1 = new Cita(1, 1, 1, 1, LocalDate.of(2026, 10, 20), LocalTime.of(10, 0));
        c1.setClienteNombre("Carlos Mendoza");
        c1.setBarberoNombre("Harry");
        c1.setServicioNombre("Corte Clasico");
        c1.setPrecio(25000);
        c1.setEstado("CONFIRMADA");
        MEMORIA.put(1, c1);

        Cita c2 = new Cita(2, 2, 2, 2, LocalDate.of(2026, 10, 21), LocalTime.of(14, 30));
        c2.setClienteNombre("Andres Felipe Perez");
        c2.setBarberoNombre("Andres");
        c2.setServicioNombre("Barba");
        c2.setPrecio(14000);
        c2.setEstado("CONFIRMADA");
        MEMORIA.put(2, c2);

        Cita c3 = new Cita(3, 3, 3, 3, LocalDate.of(2026, 10, 22), LocalTime.of(16, 0));
        c3.setClienteNombre("Juan David Rojas");
        c3.setBarberoNombre("Camilo");
        c3.setServicioNombre("Paquete Completo");
        c3.setPrecio(35000);
        c3.setEstado("PENDIENTE");
        MEMORIA.put(3, c3);

        SECUENCIA.set(4);
    }

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final BarberoDAO barberoDAO = new BarberoDAO();
    private final ServicioDAO servicioDAO = new ServicioDAO();

    public List<Cita> listarTodas() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.cliente_id, c.barbero_id, c.servicio_id, c.fecha, c.hora, " +
                "cl.nombre AS cliente_nombre, b.nombre AS barbero_nombre, " +
                "s.nombre AS servicio_nombre, s.precio " +
                "FROM citas c " +
                "JOIN clientes cl ON c.cliente_id = cl.id " +
                "JOIN barberos b ON c.barbero_id = b.id " +
                "JOIN servicios s ON c.servicio_id = s.id " +
                "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cita c = new Cita();
                c.setId(rs.getInt("id"));
                c.setClienteId(rs.getInt("cliente_id"));
                c.setBarberoId(rs.getInt("barbero_id"));
                c.setServicioId(rs.getInt("servicio_id"));
                c.setFecha(rs.getDate("fecha").toLocalDate());
                c.setHora(rs.getTime("hora").toLocalTime());
                c.setClienteNombre(rs.getString("cliente_nombre"));
                c.setBarberoNombre(rs.getString("barbero_nombre"));
                c.setServicioNombre(rs.getString("servicio_nombre"));
                c.setPrecio(rs.getInt("precio"));
                c.setEstado("CONFIRMADA");
                lista.add(c);
            }
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (SQLException e) {
            // Fallback
        }

        return new ArrayList<>(MEMORIA.values());
    }

    public Cita buscarPorId(int id) {
        String sql = "SELECT c.id, c.cliente_id, c.barbero_id, c.servicio_id, c.fecha, c.hora, " +
                "cl.nombre AS cliente_nombre, b.nombre AS barbero_nombre, " +
                "s.nombre AS servicio_nombre, s.precio " +
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
                    Cita c = new Cita();
                    c.setId(rs.getInt("id"));
                    c.setClienteId(rs.getInt("cliente_id"));
                    c.setBarberoId(rs.getInt("barbero_id"));
                    c.setServicioId(rs.getInt("servicio_id"));
                    c.setFecha(rs.getDate("fecha").toLocalDate());
                    c.setHora(rs.getTime("hora").toLocalTime());
                    c.setClienteNombre(rs.getString("cliente_nombre"));
                    c.setBarberoNombre(rs.getString("barbero_nombre"));
                    c.setServicioNombre(rs.getString("servicio_nombre"));
                    c.setPrecio(rs.getInt("precio"));
                    c.setEstado("CONFIRMADA");
                    return c;
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        return MEMORIA.get(id);
    }

    public int insertar(Cita c) {
        enriquecerCita(c);

        String sql = "INSERT INTO citas (cliente_id, barbero_id, servicio_id, fecha, hora) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, c.getClienteId());
            stmt.setInt(2, c.getBarberoId());
            stmt.setInt(3, c.getServicioId());
            stmt.setDate(4, Date.valueOf(c.getFecha()));
            stmt.setTime(5, Time.valueOf(c.getHora()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGen = rs.getInt(1);
                    c.setId(idGen);
                    MEMORIA.put(idGen, c);
                    return idGen;
                }
            }
        } catch (SQLException e) {
            // Fallback
        }

        int idNuevo = SECUENCIA.getAndIncrement();
        c.setId(idNuevo);
        MEMORIA.put(idNuevo, c);
        return idNuevo;
    }

    public boolean actualizar(Cita c) {
        enriquecerCita(c);

        String sql = "UPDATE citas SET barbero_id = ?, servicio_id = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getBarberoId());
            stmt.setInt(2, c.getServicioId());
            stmt.setDate(3, Date.valueOf(c.getFecha()));
            stmt.setTime(4, Time.valueOf(c.getHora()));
            stmt.setInt(5, c.getId());

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
        String sql = "DELETE FROM citas WHERE id = ?";

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

    public List<Cita> listarPorBarbero(int barberoId) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : listarTodas()) {
            if (c.getBarberoId() == barberoId) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    private void enriquecerCita(Cita c) {
        if (c.getClienteNombre() == null && c.getClienteId() > 0) {
            Cliente cl = clienteDAO.buscarPorId(c.getClienteId());
            if (cl != null) c.setClienteNombre(cl.getNombre());
        }
        if (c.getBarberoNombre() == null && c.getBarberoId() > 0) {
            Barbero b = barberoDAO.buscarPorId(c.getBarberoId());
            if (b != null) c.setBarberoNombre(b.getNombre());
        }
        if (c.getServicioNombre() == null && c.getServicioId() > 0) {
            Servicio s = servicioDAO.buscarPorId(c.getServicioId());
            if (s != null) {
                c.setServicioNombre(s.getNombre());
                c.setPrecio(s.getPrecio());
            }
        }
    }
}
