package com.harryestilos.dao;

import com.harryestilos.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {

    public Cliente buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM clientes WHERE nombre = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNombre(rs.getString("nombre"));
                    return c;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int insertar(Cliente c) {
        String sql = "INSERT INTO clientes (nombre) VALUES (?)";
        int idGenerado = -1;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, c.getNombre());
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

    public Cliente obtenerOCrear(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        String nombreLimpio = nombre.trim();
        Cliente clienteExistente = buscarPorNombre(nombreLimpio);
        if (clienteExistente != null) {
            return clienteExistente;
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombreLimpio);
        int generatedId = insertar(nuevoCliente);

        if (generatedId > 0) {
            nuevoCliente.setId(generatedId);
            return nuevoCliente;
        }

        return null;
    }
}
