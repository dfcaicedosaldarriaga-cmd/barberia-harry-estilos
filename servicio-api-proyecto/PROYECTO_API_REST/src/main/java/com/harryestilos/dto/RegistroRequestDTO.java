package com.harryestilos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para el registro de un nuevo cliente/usuario")
public class RegistroRequestDTO {

    @Schema(description = "Nombre completo del usuario", example = "Carlos Mendoza")
    private String nombre;

    @Schema(description = "Correo electrónico (usuario)", example = "carlos.mendoza@email.com")
    private String usuario;

    @Schema(description = "Contraseña segura (mínimo 6 caracteres)", example = "mendoza2026*")
    private String password;

    public RegistroRequestDTO() {
    }

    public RegistroRequestDTO(String nombre, String usuario, String password) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
