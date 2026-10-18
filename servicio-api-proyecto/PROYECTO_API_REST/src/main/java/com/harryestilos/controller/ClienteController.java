package com.harryestilos.controller;

import com.harryestilos.dao.ClienteDAO;
import com.harryestilos.model.Cliente;
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
@RequestMapping("/api/clientes")
@Tag(name = "4. Módulo de Clientes", description = "Servicios web para consulta y registro de clientes de la barbería")
public class ClienteController {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Operation(summary = "Listar clientes registrados", description = "Retorna todos los clientes con nombre y datos de contacto")
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteDAO.listarTodos());
    }

    @Operation(summary = "Consultar cliente por ID", description = "Obtiene los detalles de un cliente específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Cliente c = clienteDAO.buscarPorId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el cliente con ID " + id));
        }
        return ResponseEntity.ok(c);
    }

    @Operation(summary = "Registrar nuevo cliente", description = "Crea un registro de cliente con nombre, teléfono y correo")
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente c) {
        if (c.getNombre() == null || c.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El campo nombre es obligatorio"));
        }
        int idGenerado = clienteDAO.obtenerOCrear(c.getNombre(), c.getTelefono(), c.getCorreo());
        c.setId(idGenerado);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Cliente registrado exitosamente", "id", idGenerado, "cliente", c));
    }

    @Operation(summary = "Actualizar cliente", description = "Modifica los datos del cliente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable int id, @RequestBody Cliente c) {
        Cliente existente = clienteDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el cliente con ID " + id));
        }
        if (c.getNombre() != null && !c.getNombre().isBlank()) existente.setNombre(c.getNombre().trim());
        if (c.getTelefono() != null) existente.setTelefono(c.getTelefono().trim());
        if (c.getCorreo() != null) existente.setCorreo(c.getCorreo().trim());

        clienteDAO.actualizar(existente);
        return ResponseEntity.ok(Map.of("mensaje", "Cliente actualizado exitosamente", "cliente", existente));
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable int id) {
        boolean ok = clienteDAO.eliminar(id);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el cliente con ID " + id));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Cliente eliminado exitosamente", "id", id));
    }
}
