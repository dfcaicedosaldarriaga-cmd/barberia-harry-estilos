package com.harryestilos.dao;

import com.harryestilos.model.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public List<Servicio> listarTodos() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicios ORDER BY id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servicio s = new Servicio();
                s.setId(rs.getInt("id"));
                s.setNombre(rs.getString("nombre"));
                s.setClave(rs.getString("clave"));
                s.setPrecio(rs.getInt("precio"));
                s.setDuracionMinutos(rs.getInt("duracion_minutos"));
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Servicio buscarPorId(int id) {
        String sql = "SELECT * FROM servicios WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Servicio s = new Servicio();
                    s.setId(rs.getInt("id"));
                    s.setNombre(rs.getString("nombre"));
                    s.setClave(rs.getString("clave"));
                    s.setPrecio(rs.getInt("precio"));
                    s.setDuracionMinutos(rs.getInt("duracion_minutos"));
                    return s;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Servicio buscarPorClave(String clave) {
        String sql = "SELECT * FROM servicios WHERE clave = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clave);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Servicio s = new Servicio();
                    s.setId(rs.getInt("id"));
                    s.setNombre(rs.getString("nombre"));
                    s.setClave(rs.getString("clave"));
                    s.setPrecio(rs.getInt("precio"));
                    s.setDuracionMinutos(rs.getInt("duracion_minutos"));
                    return s;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
