package com.harryestilos.dao;

import com.harryestilos.model.Barbero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarberoDAO {

    public List<Barbero> listarTodos() {
        List<Barbero> lista = new ArrayList<>();
        String sql = "SELECT * FROM barberos ORDER BY id";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Barbero barbero = new Barbero();
                barbero.setId(rs.getInt("id"));
                barbero.setNombre(rs.getString("nombre"));
                lista.add(barbero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
