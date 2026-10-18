package com.harryestilos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para agendar o actualizar una cita")
public class CitaRequestDTO {

    @Schema(description = "Nombre completo del cliente", example = "Carlos Mendoza")
    private String clienteNombre;

    @Schema(description = "Teléfono de contacto del cliente", example = "3109876543")
    private String clienteTelefono;

    @Schema(description = "Correo del cliente", example = "carlos.mendoza@email.com")
    private String clienteCorreo;

    @Schema(description = "ID del barbero seleccionado (1: Harry, 2: Andres, 3: Camilo)", example = "1")
    private int barberoId;

    @Schema(description = "ID del servicio seleccionado (1: Corte Clasico, 2: Barba, 3: Paquete Completo)", example = "1")
    private int servicioId;

    @Schema(description = "Fecha de la cita (formato YYYY-MM-DD)", example = "2026-10-25")
    private String fecha;

    @Schema(description = "Hora de la cita (formato HH:mm)", example = "15:00")
    private String hora;

    @Schema(description = "Estado de la cita (PENDIENTE, CONFIRMADA, ATENDIDA, CANCELADA)", example = "CONFIRMADA")
    private String estado = "CONFIRMADA";

    public CitaRequestDTO() {
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public String getClienteCorreo() {
        return clienteCorreo;
    }

    public void setClienteCorreo(String clienteCorreo) {
        this.clienteCorreo = clienteCorreo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
