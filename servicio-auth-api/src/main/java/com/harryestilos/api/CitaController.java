package com.harryestilos.api;

import com.harryestilos.dao.CitaDAO;
import com.harryestilos.dao.ClienteDAO;
import com.harryestilos.dao.ServicioDAO;
import com.harryestilos.model.Cita;
import com.harryestilos.model.Cliente;
import com.harryestilos.model.Servicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST del CRUD de citas (GA7-220501096-AA5-EV03).
 *
 * Reutiliza literalmente {@link CitaDAO}, {@link ClienteDAO} y
 * {@link ServicioDAO} del proyecto principal (WAR JSP/Servlet, copiadas en
 * este módulo igual que se hizo con UsuarioDAO en la EV01): ni el modelo
 * ni el acceso a datos se reescriben, solo se expone como API REST lo que
 * ya existe como flujo web (AgendamientoServlet, CitasServlet).
 *
 * Nota honesta sobre el alcance: el CitaDAO del proyecto no valida cruces
 * de horario entre citas del mismo barbero (no existe esa regla en el
 * código fuente del repositorio). Esta API tampoco la agrega, para no
 * introducir un comportamiento que el módulo web no tiene: ambos canales
 * deben comportarse igual. Queda documentado como una mejora pendiente en
 * la evidencia de diseño.
 */
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaDAO citaDAO = new CitaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ServicioDAO servicioDAO = new ServicioDAO();

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(citaDAO.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Cita cita = citaDAO.buscarPorId(id);
        if (cita == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una cita con id " + id + ".");
        }
        return ResponseEntity.ok(cita);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestParam String cliente,
                                    @RequestParam("barbero_id") int barberoId,
                                    @RequestParam("servicio_id") int servicioId,
                                    @RequestParam String fecha,
                                    @RequestParam String hora) {
        if (cliente == null || cliente.isBlank() || fecha == null || fecha.isBlank()
                || hora == null || hora.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Los campos 'cliente', 'fecha' y 'hora' son obligatorios.");
        }

        Servicio servicio = servicioDAO.buscarPorId(servicioId);
        if (servicio == null) {
            return ResponseEntity.badRequest()
                    .body("El servicio_id " + servicioId + " no existe.");
        }

        Cliente clienteEntidad = clienteDAO.obtenerOCrear(cliente);
        if (clienteEntidad == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible registrar el cliente.");
        }

        Cita nuevaCita = new Cita();
        nuevaCita.setClienteId(clienteEntidad.getId());
        nuevaCita.setBarberoId(barberoId);
        nuevaCita.setServicioId(servicioId);
        nuevaCita.setFecha(fecha);
        nuevaCita.setHora(hora);

        int idGenerado = citaDAO.insertar(nuevaCita);
        if (idGenerado <= 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No fue posible crear la cita.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(citaDAO.buscarPorId(idGenerado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id,
                                         @RequestParam String cliente,
                                         @RequestParam("barbero_id") int barberoId,
                                         @RequestParam("servicio_id") int servicioId,
                                         @RequestParam String fecha,
                                         @RequestParam String hora) {
        Cita existente = citaDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una cita con id " + id + ".");
        }

        if (cliente == null || cliente.isBlank() || fecha == null || fecha.isBlank()
                || hora == null || hora.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Los campos 'cliente', 'fecha' y 'hora' son obligatorios.");
        }

        Servicio servicio = servicioDAO.buscarPorId(servicioId);
        if (servicio == null) {
            return ResponseEntity.badRequest()
                    .body("El servicio_id " + servicioId + " no existe.");
        }

        Cliente clienteEntidad = clienteDAO.obtenerOCrear(cliente);

        Cita citaActualizada = new Cita();
        citaActualizada.setId(id);
        citaActualizada.setClienteId(clienteEntidad.getId());
        citaActualizada.setBarberoId(barberoId);
        citaActualizada.setServicioId(servicioId);
        citaActualizada.setFecha(fecha);
        citaActualizada.setHora(hora);

        citaDAO.actualizar(citaActualizada);

        return ResponseEntity.ok(citaDAO.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        Cita existente = citaDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una cita con id " + id + ".");
        }
        citaDAO.eliminar(id);
        return ResponseEntity.ok("Cita " + id + " eliminada correctamente.");
    }
}
