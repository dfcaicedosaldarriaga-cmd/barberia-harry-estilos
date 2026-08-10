package com.harryestilos.servlet;

import com.harryestilos.dao.ServicioDAO;
import com.harryestilos.model.Servicio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/servicios")
public class ServiciosServlet extends HttpServlet {

    private ServicioDAO servicioDAO;

    @Override
    public void init() throws ServletException {
        servicioDAO = new ServicioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Servicio> servicios = servicioDAO.listarTodos();
            request.setAttribute("servicios", servicios);
            request.getRequestDispatcher("/servicios.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
