package com.harryestilos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Copia intencional de com.harryestilos.dao.ConexionDB del módulo
 * principal (ver nota en UsuarioDAO de este mismo módulo): apunta a la
 * misma base de datos "barberia_harry_estilos" usada por el WAR
 * JSP/Servlet, para que este servicio REST (GA7-220501096-AA5-EV01)
 * comparta los mismos usuarios registrados.
 */
public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/barberia_harry_estilos?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver MySQL JDBC", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
