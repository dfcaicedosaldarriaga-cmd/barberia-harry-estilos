package com.harryestilos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de inicio para la API REST del Proyecto Formativo
 * Barberia Harry Estilos.
 *
 * Evidencia: GA7-220501096-AA5-EV03. Diseno y desarrollo de servicios web - proyecto.
 */
@SpringBootApplication
public class BarberiaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarberiaApiApplication.class, args);
        System.out.println("===============================================================================");
        System.out.println("   API REST BARBERIA HARRY ESTILOS - PROYECTO FORMATIVO EN EJECUCION           ");
        System.out.println("   Puerto: http://localhost:8081                                               ");
        System.out.println("   Documentacion Swagger UI: http://localhost:8081/swagger-ui/index.html       ");
        System.out.println("   Especificacion OpenAPI JSON: http://localhost:8081/v3/api-docs              ");
        System.out.println("===============================================================================");
    }
}
