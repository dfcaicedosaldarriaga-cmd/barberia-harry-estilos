package com.harryestilos.servlet;

import com.harryestilos.dao.BarberoDAO;
import com.harryestilos.dao.CitaDAO;
import com.harryestilos.dao.ClienteDAO;
import com.harryestilos.dao.ServicioDAO;
import com.harryestilos.model.Barbero;
import com.harryestilos.model.Cita;
import com.harryestilos.model.Cliente;
import com.harryestilos.model.Servicio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/agendamiento", "/confirmacion"})
public class AgendamientoServlet extends HttpServlet {

    private BarberoDAO barberoDAO;
    private ServicioDAO servicioDAO;
    private ClienteDAO clienteDAO;
    private CitaDAO citaDAO;

    @Override
    public void init() throws ServletException {
        barberoDAO = new BarberoDAO();
        servicioDAO = new ServicioDAO();
        clienteDAO = new ClienteDAO();
        citaDAO = new CitaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String servletPath = request.getServletPath();
            if ("/confirmacion".equals(servletPath)) {
                String idParam = request.getParameter("id");
                if (idParam != null && !idParam.trim().isEmpty()) {
                    int citaId = Integer.parseInt(idParam);
                    Cita cita = citaDAO.buscarPorId(citaId);
                    if (cita != null) {
                        Servicio servicio = servicioDAO.buscarPorId(cita.getServicioId());
                        request.setAttribute("cita", cita);
                        request.setAttribute("servicio", servicio);
                    }
                }
                request.getRequestDispatcher("/confirmacion.jsp").forward(request, response);
            } else {
                List<Barbero> barberos = barberoDAO.listarTodos();
                List<Servicio> servicios = servicioDAO.listarTodos();

                request.setAttribute("barberos", barberos);
                request.setAttribute("servicios", servicios);

                String servicioClave = request.getParameter("servicio");
                if (servicioClave != null && !servicioClave.trim().isEmpty()) {
                    request.setAttribute("servicioParam", servicioClave);
                }

                request.getRequestDispatcher("/agendamiento.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String clienteNombre = request.getParameter("cliente");
            int barberoId = Integer.parseInt(request.getParameter("barbero_id"));
            int servicioId = Integer.parseInt(request.getParameter("servicio_id"));
            String fecha = request.getParameter("fecha");
            String hora = request.getParameter("hora");

            Cliente cliente = clienteDAO.obtenerOCrear(clienteNombre);
            int clienteId = (cliente != null) ? cliente.getId() : 0;

            Cita cita = new Cita();
            cita.setClienteId(clienteId);
            cita.setBarberoId(barberoId);
            cita.setServicioId(servicioId);
            cita.setFecha(fecha);
            cita.setHora(hora);

            int citaId = citaDAO.insertar(cita);
            if (citaId <= 0 && cita.getId() > 0) {
                citaId = cita.getId();
            }

            response.sendRedirect(request.getContextPath() + "/confirmacion?id=" + citaId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
