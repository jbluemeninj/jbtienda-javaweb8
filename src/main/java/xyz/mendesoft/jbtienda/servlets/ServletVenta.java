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
import javax.servlet.http.HttpSession;
import xyz.mendesoft.jbtienda.dao.PSQLVenta;
import xyz.mendesoft.jbtienda.exceptions.ExcepcionGeneral;
import xyz.mendesoft.jbtienda.models.Producto;
import xyz.mendesoft.jbtienda.models.VentaEncabezado;
import xyz.mendesoft.jbtienda.utils.Utilidades;


@WebServlet(name = "ServletVenta", urlPatterns = {"/vender"})
public class ServletVenta extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession sesion = request.getSession();
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        List<Producto> carro = (List) sesion.getAttribute("carro");
        
        PSQLVenta dao = new PSQLVenta();
        VentaEncabezado venta = null;
        try {
            venta = dao.insertarVenta(nombre, direccion, carro);
        } catch (ExcepcionGeneral | ClassNotFoundException ex) {
            Logger.getLogger(ServletVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        sesion.removeAttribute("carro");
        request.setAttribute("venta", venta);
        Utilidades.getInstancia().irAPagina(request, response, getServletContext(), "/venta.jsp");


    }
    
    

   

}
