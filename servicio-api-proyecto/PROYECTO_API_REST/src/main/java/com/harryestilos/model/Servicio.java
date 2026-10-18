package com.harryestilos.model;

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
