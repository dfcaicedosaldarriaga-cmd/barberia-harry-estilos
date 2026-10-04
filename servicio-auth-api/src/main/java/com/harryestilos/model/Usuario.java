package com.harryestilos.model;

/**
 * Representa un usuario autenticable del sistema (Cliente, Barbero o
 * Administrador). Es el modelo base del módulo de inicio de sesión y
 * registro (GA7-220501096-AA3-EV01).
 *
 * El campo "activo" se agregó al probar el caso de uso CU-02 (evidencia
 * GA7-220501096-AA3-EV02): un usuario inactivo debe quedar bloqueado
 * aunque sus credenciales sean correctas.
 */
public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String passwordHash;
    private String salt;
    private String rol; // CLIENTE, BARBERO o ADMINISTRADOR
    private boolean activo = true;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String correo, String passwordHash, String salt, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.rol = rol;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
