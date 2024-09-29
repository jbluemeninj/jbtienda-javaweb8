package xyz.mendesoft.jbtienda.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import xyz.mendesoft.jbtienda.dao.PSQLProducto;
import xyz.mendesoft.jbtienda.dao.PSQLTalla;
import xyz.mendesoft.jbtienda.exceptions.ExcepcionGeneral;
import xyz.mendesoft.jbtienda.models.Producto;
import xyz.mendesoft.jbtienda.models.Talla;
import xyz.mendesoft.jbtienda.utils.Utilidades;


@WebServlet(name = "ServletComprar", urlPatterns = {"/agregar-producto"})
public class ServletComprar extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        
        String sId = request.getParameter("idproducto");
        String sTalla = request.getParameter("talla");
        
        short idProducto, idTalla;
        try {
            idProducto = Short.parseShort(sId);
            idTalla = Short.parseShort(sTalla);
        } catch (NumberFormatException nfe) {
            idProducto = 0;
            idTalla = 0;
        }
        
        PSQLProducto daoProducto = new PSQLProducto();
        PSQLTalla daoTalla = new PSQLTalla();
        
        Producto producto = null;
        try {
            producto = daoProducto.obtenerPorId(idProducto);
        } catch (ExcepcionGeneral | ClassNotFoundException ex) {
            Logger.getLogger(ServletComprar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Talla talla = null;
        
        try {
            talla = daoTalla.obtenerPorId(idTalla);
        } catch (ExcepcionGeneral | ClassNotFoundException ex) {
            Logger.getLogger(ServletComprar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producto.setTalla(talla);
        
        List<Producto> carro = (List) sesion.getAttribute("carro");
        
        if (carro == null) {
            carro = new ArrayList<>();
        }
        
        carro.add(producto);
        sesion.setAttribute("carro", carro);
        Utilidades.getInstancia().irAPagina(request, response, getServletContext(), "/carro.jsp");


    }

    
   

}
