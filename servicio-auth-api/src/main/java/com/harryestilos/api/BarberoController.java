package com.harryestilos.api;

import com.harryestilos.dao.BarberoDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Catálogo de barberos, de solo lectura (GA7-220501096-AA5-EV03).
 * Reutiliza {@link BarberoDAO} del proyecto principal.
 */
@RestController
@RequestMapping("/api/barberos")
public class BarberoController {

    private final BarberoDAO barberoDAO = new BarberoDAO();

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(barberoDAO.listarTodos());
    }
}
