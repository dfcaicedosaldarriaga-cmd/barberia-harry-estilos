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

@WebServlet("/citas")
public class CitasServlet extends HttpServlet {

    private CitaDAO citaDAO;
    private BarberoDAO barberoDAO;
    private ServicioDAO servicioDAO;
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        citaDAO = new CitaDAO();
        barberoDAO = new BarberoDAO();
        servicioDAO = new ServicioDAO();
        clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr);
                Cita cita = citaDAO.buscarPorId(id);
                List<Barbero> barberos = barberoDAO.listarTodos();
                List<Servicio> servicios = servicioDAO.listarTodos();

                request.setAttribute("cita", cita);
                request.setAttribute("barberos", barberos);
                request.setAttribute("servicios", servicios);

                request.getRequestDispatcher("/editar-cita.jsp").forward(request, response);
            } else {
                List<Cita> citas = citaDAO.listarTodas();
                request.setAttribute("citas", citas);
                request.getRequestDispatcher("/citas.jsp").forward(request, response);
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
            String idStr = request.getParameter("id");
            int id = (idStr != null && !idStr.trim().isEmpty()) ? Integer.parseInt(idStr) : 0;
            String clienteNombre = request.getParameter("cliente");
            int barberoId = Integer.parseInt(request.getParameter("barbero_id"));
            int servicioId = Integer.parseInt(request.getParameter("servicio_id"));
            String fecha = request.getParameter("fecha");
            String hora = request.getParameter("hora");

            Cliente cliente = clienteDAO.obtenerOCrear(clienteNombre);
            int clienteId = (cliente != null) ? cliente.getId() : 0;

            Cita cita = new Cita();
            cita.setId(id);
            cita.setClienteId(clienteId);
            cita.setBarberoId(barberoId);
            cita.setServicioId(servicioId);
            cita.setFecha(fecha);
            cita.setHora(hora);

            citaDAO.actualizar(cita);

            response.sendRedirect(request.getContextPath() + "/citas");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
