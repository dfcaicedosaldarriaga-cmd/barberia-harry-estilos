package com.harryestilos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciales para inicio de sesión en la API")
public class LoginRequestDTO {

    @Schema(description = "Correo electrónico del usuario", example = "admin@harryestilos.com")
    private String usuario;

    @Schema(description = "Contraseña del usuario", example = "admin123")
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
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
