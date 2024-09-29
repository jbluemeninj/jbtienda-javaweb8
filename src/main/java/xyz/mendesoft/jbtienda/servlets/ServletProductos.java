package xyz.mendesoft.jbtienda.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xyz.mendesoft.jbtienda.dao.PSQLProducto;
import xyz.mendesoft.jbtienda.exceptions.ExcepcionGeneral;
import xyz.mendesoft.jbtienda.models.Producto;
import xyz.mendesoft.jbtienda.utils.Utilidades;


@WebServlet(name = "ServletProductos", urlPatterns = {"/productos"})
public class ServletProductos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PSQLProducto dao = new PSQLProducto();
        List<Producto> listado = null;
        
        try {
            listado = dao.obtenerTodos();
        } catch (ExcepcionGeneral | ClassNotFoundException ex) {
            Logger.getLogger(ServletProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        req.setAttribute("listadoProductos", listado);
        Utilidades.getInstancia().irAPagina(req, resp, getServletContext(), "/productos.jsp");
        
    }
    
    

   

}
