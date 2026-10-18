package com.harryestilos.controller;

import com.harryestilos.dao.CitaDAO;
import com.harryestilos.dao.ClienteDAO;
import com.harryestilos.dto.CitaRequestDTO;
import com.harryestilos.model.Cita;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "1. Módulo de Citas (Agendamiento)", description = "Servicios web CRUD para creación, consulta, modificación y cancelación de citas")
public class CitaController {

    private final CitaDAO citaDAO = new CitaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Operation(summary = "Listar todas las citas", description = "Retorna el listado completo de citas agendadas con los nombres de cliente, barbero, servicio y precio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de citas obtenido exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<Cita>> listarTodas() {
        return ResponseEntity.ok(citaDAO.listarTodas());
    }

    @Operation(summary = "Consultar cita por ID", description = "Retorna la información detallada de una cita específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada con el ID indicado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable @Parameter(description = "ID de la cita") int id) {
        Cita cita = citaDAO.buscarPorId(id);
        if (cita == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró la cita con ID " + id));
        }
        return ResponseEntity.ok(cita);
    }

    @Operation(summary = "Agendar nueva cita", description = "Registra una nueva cita para el cliente en el barbero y servicio seleccionados")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cita agendada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de agendamiento incompletos o inválidos")
    })
    @PostMapping
    public ResponseEntity<?> agendarCita(@RequestBody CitaRequestDTO dto) {
        if (dto.getClienteNombre() == null || dto.getClienteNombre().isBlank()
                || dto.getBarberoId() <= 0 || dto.getServicioId() <= 0
                || dto.getFecha() == null || dto.getHora() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Los campos clienteNombre, barberoId, servicioId, fecha y hora son obligatorios"));
        }

        try {
            int clienteId = clienteDAO.obtenerOCrear(dto.getClienteNombre(), dto.getClienteTelefono(), dto.getClienteCorreo());

            Cita nueva = new Cita();
            nueva.setClienteId(clienteId);
            nueva.setClienteNombre(dto.getClienteNombre().trim());
            nueva.setBarberoId(dto.getBarberoId());
            nueva.setServicioId(dto.getServicioId());
            nueva.setFecha(LocalDate.parse(dto.getFecha().trim()));
            nueva.setHora(LocalTime.parse(dto.getHora().trim()));
            nueva.setEstado(dto.getEstado() != null ? dto.getEstado() : "CONFIRMADA");

            int idGenerado = citaDAO.insertar(nueva);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "mensaje", "Cita agendada correctamente",
                    "citaId", idGenerado,
                    "cita", nueva
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Formato de fecha u hora inválido (Use YYYY-MM-DD y HH:mm)"));
        }
    }

    @Operation(summary = "Actualizar / Reprogramar cita", description = "Modifica los datos de una cita existente (fecha, hora, barbero, servicio o estado)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable int id, @RequestBody CitaRequestDTO dto) {
        Cita existente = citaDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró la cita con ID " + id));
        }

        try {
            if (dto.getBarberoId() > 0) existente.setBarberoId(dto.getBarberoId());
            if (dto.getServicioId() > 0) existente.setServicioId(dto.getServicioId());
            if (dto.getFecha() != null && !dto.getFecha().isBlank()) {
                existente.setFecha(LocalDate.parse(dto.getFecha().trim()));
            }
            if (dto.getHora() != null && !dto.getHora().isBlank()) {
                existente.setHora(LocalTime.parse(dto.getHora().trim()));
            }
            if (dto.getEstado() != null && !dto.getEstado().isBlank()) {
                existente.setEstado(dto.getEstado());
            }

            boolean ok = citaDAO.actualizar(existente);
            if (ok) {
                return ResponseEntity.ok(Map.of(
                        "mensaje", "Cita reprogramada / actualizada correctamente",
                        "cita", existente
                ));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "No se pudo actualizar la cita"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Error en formato de datos para actualización"));
        }
    }

    @Operation(summary = "Cancelar / Eliminar cita", description = "Elimina permanentemente una cita agendada por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita cancelada y eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCita(@PathVariable int id) {
        boolean eliminada = citaDAO.eliminar(id);
        if (!eliminada) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró la cita con ID " + id));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Cita cancelada y eliminada exitosamente", "id", id));
    }

    @Operation(summary = "Listar citas por barbero", description = "Filtra el listado de citas según el ID del barbero")
    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<Cita>> listarPorBarbero(@PathVariable int barberoId) {
        return ResponseEntity.ok(citaDAO.listarPorBarbero(barberoId));
    }
}
