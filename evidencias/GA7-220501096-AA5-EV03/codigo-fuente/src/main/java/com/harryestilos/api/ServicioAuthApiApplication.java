package com.harryestilos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del servicio web REST de autenticación
 * (GA7-220501096-AA5-EV01).
 *
 * Al arrancar, Spring Boot escanea este paquete y sus subpaquetes
 * (com.harryestilos.*) y registra automáticamente el controlador REST
 * {@link UsuarioController}.
 */
@SpringBootApplication
public class ServicioAuthApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioAuthApiApplication.class, args);
    }
}
