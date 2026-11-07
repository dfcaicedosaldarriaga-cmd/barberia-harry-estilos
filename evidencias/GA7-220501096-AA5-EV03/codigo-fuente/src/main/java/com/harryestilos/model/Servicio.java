package com.harryestilos.model;

/**
 * NOTA (GA7-220501096-AA5-EV03): copia intencional, caracter por caracter,
 * de com.harryestilos.model.Servicio del proyecto principal (WAR
 * JSP/Servlet), igual que se hizo con Usuario en la EV01. Se duplica aqui
 * porque este modulo vive en un proyecto Maven aparte.
 */

public class Servicio {
    private int id;
    private String nombre;
    private String clave;
    private int precio;
    private int duracionMinutos;

    public Servicio() {
    }

    public Servicio(int id, String nombre, String clave, int precio, int duracionMinutos) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }
}
