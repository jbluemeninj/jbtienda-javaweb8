package xyz.mendesoft.jbtienda.servlets;

import java.io.IOException;
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


@WebServlet(name = "ServletProductoDetalle", urlPatterns = {"/producto-detalle"})
public class ServletProductoDetalle extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        String sId = request.getParameter("id");
        short id;
        try {
            id = Short.parseShort(sId);
        } catch (NumberFormatException nfe) {
            id = 0;
        }
        
        PSQLProducto dao = new PSQLProducto();
        Producto producto = null;
        try {
            producto = dao.obtenerPorId(id);
        } catch (ExcepcionGeneral | ClassNotFoundException ex) {
            Logger.getLogger(ServletProductoDetalle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("producto", producto);
        Utilidades.getInstancia().irAPagina(request, response, getServletContext(), "/producto-detalle.jsp");
    }
    
    

   

}
