package com.harryestilos.api;

import com.harryestilos.dao.ServicioDAO;
import com.harryestilos.model.Servicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Catálogo de servicios, de solo lectura (GA7-220501096-AA5-EV03).
 * Reutiliza {@link ServicioDAO} del proyecto principal.
 */
@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioDAO servicioDAO = new ServicioDAO();

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(servicioDAO.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Servicio servicio = servicioDAO.buscarPorId(id);
        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un servicio con id " + id + ".");
        }
        return ResponseEntity.ok(servicio);
    }
}
