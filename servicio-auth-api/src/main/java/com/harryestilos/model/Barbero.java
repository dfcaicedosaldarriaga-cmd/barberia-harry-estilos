package com.harryestilos.model;

/**
 * NOTA (GA7-220501096-AA5-EV03): copia intencional, caracter por caracter,
 * de com.harryestilos.model.Barbero del proyecto principal (WAR
 * JSP/Servlet), igual que se hizo con Usuario en la EV01. Se duplica aqui
 * porque este modulo vive en un proyecto Maven aparte.
 */

public class Barbero {
    private int id;
    private String nombre;

    public Barbero() {
    }

    public Barbero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
