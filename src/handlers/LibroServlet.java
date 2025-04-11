package handlers;

import com.google.gson.Gson;
import dao.LibroDAO;
import modelo.Libro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.List;

@WebServlet("/libros/*")
public class LibroServlet extends HttpServlet {
    private LibroDAO libroDAO = new LibroDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();
        String path = request.getPathInfo();

        try {
            if (path != null && !path.equals("/")) {
                String[] parts = path.split("/");
                if (parts.length > 1) {
                    int id = Integer.parseInt(parts[1]);
                    Libro libro = libroDAO.obtenerPorId(id);
                    if (libro != null) {
                        out.print(gson.toJson(libro));
                    } else {
                        response.setStatus(404);
                        out.print("{\"error\": \"Libro no encontrado\"}");
                    }
                }
            } else {
                List<Libro> libros = libroDAO.obtenerTodos();
                out.print(gson.toJson(libros));
            }
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();

        try {
            StringBuilder json = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            Libro libro = gson.fromJson(json.toString(), Libro.class);
            if (libroDAO.insertar(libro)) {
                response.setStatus(201);
                out.print(gson.toJson(libro));
            } else {
                response.setStatus(400);
                out.print("{\"error\": \"No se pudo insertar el libro\"}");
            }
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();

        try {
            String path = request.getPathInfo();
            if (path != null && !path.equals("/")) {
                String[] parts = path.split("/");
                if (parts.length > 1) {
                    int id = Integer.parseInt(parts[1]);
                    if (libroDAO.eliminar(id)) {
                        out.print("{\"message\": \"Libro eliminado\"}");
                    } else {
                        response.setStatus(404);
                        out.print("{\"error\": \"No se pudo eliminar el libro\"}");
                    }
                }
            }
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(200);
    }
}
