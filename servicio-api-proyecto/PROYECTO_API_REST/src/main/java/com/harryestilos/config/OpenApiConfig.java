package com.harryestilos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion de OpenAPI 3.0 / Swagger UI para la evidencia
 * GA7-220501096-AA5-EV03: Diseno y desarrollo de servicios web - proyecto.
 *
 * Provee documentacion viva interactiva accesible desde el navegador en:
 * http://localhost:8081/swagger-ui/index.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Barbería Harry Estilos (Proyecto Formativo)")
                        .version("1.0.0")
                        .description("Documentación oficial interactiva de los servicios web RESTful desarrollados para el sistema de agendamiento y gestión de la Barbería Harry Estilos. Cumplimiento de la evidencia SENA GA7-220501096-AA5-EV03.")
                        .contact(new Contact()
                                .name("Diego Fernando Caicedo Saldarriaga - Aprendiz ADSO")
                                .email("dfcaicedosaldarriaga@gmail.com")
                                .url("https://github.com/dfcaicedosaldarriaga-cmd/barberia-harry-estilos"))
                        .license(new License()
                                .name("SENA - Formación Profesional Integral ADSO")
                                .url("https://www.sena.edu.co")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor Local de Desarrollo y Pruebas")
                ));
    }
}
