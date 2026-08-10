package com.harryestilos.model;

public class Cita {
    private int id;
    private int clienteId;
    private int barberoId;
    private int servicioId;
    private String fecha;
    private String hora;

    // Additional display fields
    private String clienteNombre;
    private String barberoNombre;
    private String servicioNombre;
    private int servicioPrecio;
    private int servicioDuracion;

    public Cita() {
    }

    public Cita(int id, int clienteId, int barberoId, int servicioId, String fecha, String hora) {
        this.id = id;
        this.clienteId = clienteId;
        this.barberoId = barberoId;
        this.servicioId = servicioId;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getBarberoId() {
        return barberoId;
    }

    public void setBarberoId(int barberoId) {
        this.barberoId = barberoId;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getBarberoNombre() {
        return barberoNombre;
    }

    public void setBarberoNombre(String barberoNombre) {
        this.barberoNombre = barberoNombre;
    }

    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    public int getServicioPrecio() {
        return servicioPrecio;
    }

    public void setServicioPrecio(int servicioPrecio) {
        this.servicioPrecio = servicioPrecio;
    }

    public int getServicioDuracion() {
        return servicioDuracion;
    }

    public void setServicioDuracion(int servicioDuracion) {
        this.servicioDuracion = servicioDuracion;
    }
}
