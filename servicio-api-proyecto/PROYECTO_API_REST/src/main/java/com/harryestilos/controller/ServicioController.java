package com.harryestilos.controller;

import com.harryestilos.dao.ServicioDAO;
import com.harryestilos.model.Servicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "2. Módulo de Servicios", description = "Servicios web para consulta y administración del catálogo de servicios de barbería")
public class ServicioController {

    private final ServicioDAO servicioDAO = new ServicioDAO();

    @Operation(summary = "Listar catálogo de servicios", description = "Retorna todos los servicios disponibles con precio en pesos colombianos y duración en minutos")
    @GetMapping
    public ResponseEntity<List<Servicio>> listarTodos() {
        return ResponseEntity.ok(servicioDAO.listarTodos());
    }

    @Operation(summary = "Consultar servicio por ID", description = "Obtiene los detalles de un servicio de barbería específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Servicio s = servicioDAO.buscarPorId(id);
        if (s == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el servicio con ID " + id));
        }
        return ResponseEntity.ok(s);
    }

    @Operation(summary = "Crear nuevo servicio", description = "Agrega un nuevo servicio al catálogo de la barbería")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Servicio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de servicio incompletos")
    })
    @PostMapping
    public ResponseEntity<?> crearServicio(@RequestBody Servicio s) {
        if (s.getNombre() == null || s.getNombre().isBlank() || s.getPrecio() <= 0 || s.getDuracionMinutos() <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Los campos nombre, precio y duracionMinutos son obligatorios"));
        }

        int idGenerado = servicioDAO.insertar(s);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Servicio creado correctamente", "id", idGenerado, "servicio", s));
    }

    @Operation(summary = "Actualizar servicio", description = "Modifica el precio, duración o nombre de un servicio existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(@PathVariable int id, @RequestBody Servicio s) {
        Servicio existente = servicioDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el servicio con ID " + id));
        }

        if (s.getNombre() != null && !s.getNombre().isBlank()) existente.setNombre(s.getNombre());
        if (s.getClave() != null && !s.getClave().isBlank()) existente.setClave(s.getClave());
        if (s.getPrecio() > 0) existente.setPrecio(s.getPrecio());
        if (s.getDuracionMinutos() > 0) existente.setDuracionMinutos(s.getDuracionMinutos());

        servicioDAO.actualizar(existente);
        return ResponseEntity.ok(Map.of("mensaje", "Servicio actualizado exitosamente", "servicio", existente));
    }

    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio del catálogo de barbería")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable int id) {
        boolean ok = servicioDAO.eliminar(id);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el servicio con ID " + id));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Servicio eliminado exitosamente", "id", id));
    }
}
