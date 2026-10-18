package com.harryestilos.controller;

import com.harryestilos.dao.BarberoDAO;
import com.harryestilos.model.Barbero;
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
@RequestMapping("/api/barberos")
@Tag(name = "3. Módulo de Barberos", description = "Servicios web para consulta y administración del equipo de barberos profesionales")
public class BarberoController {

    private final BarberoDAO barberoDAO = new BarberoDAO();

    @Operation(summary = "Listar todos los barberos", description = "Retorna el listado del equipo de barberos de la Barbería Harry Estilos")
    @GetMapping
    public ResponseEntity<List<Barbero>> listarTodos() {
        return ResponseEntity.ok(barberoDAO.listarTodos());
    }

    @Operation(summary = "Consultar barbero por ID", description = "Obtiene los datos de un barbero por su identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Barbero encontrado"),
            @ApiResponse(responseCode = "404", description = "Barbero no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Barbero b = barberoDAO.buscarPorId(id);
        if (b == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el barbero con ID " + id));
        }
        return ResponseEntity.ok(b);
    }

    @Operation(summary = "Registrar nuevo barbero", description = "Agrega un nuevo barbero profesional al equipo de trabajo")
    @PostMapping
    public ResponseEntity<?> crearBarbero(@RequestBody Barbero b) {
        if (b.getNombre() == null || b.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El campo nombre es obligatorio"));
        }
        int idGenerado = barberoDAO.insertar(b);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Barbero registrado correctamente", "id", idGenerado, "barbero", b));
    }

    @Operation(summary = "Actualizar barbero", description = "Actualiza el nombre u otros datos del barbero")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBarbero(@PathVariable int id, @RequestBody Barbero b) {
        Barbero existente = barberoDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el barbero con ID " + id));
        }
        if (b.getNombre() != null && !b.getNombre().isBlank()) {
            existente.setNombre(b.getNombre().trim());
        }
        barberoDAO.actualizar(existente);
        return ResponseEntity.ok(Map.of("mensaje", "Barbero actualizado exitosamente", "barbero", existente));
    }

    @Operation(summary = "Eliminar barbero", description = "Elimina un barbero del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBarbero(@PathVariable int id) {
        boolean ok = barberoDAO.eliminar(id);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el barbero con ID " + id));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Barbero eliminado exitosamente", "id", id));
    }
}
