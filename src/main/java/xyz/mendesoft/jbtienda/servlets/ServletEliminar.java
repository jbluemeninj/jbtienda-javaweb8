package xyz.mendesoft.jbtienda.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import xyz.mendesoft.jbtienda.models.Producto;
import xyz.mendesoft.jbtienda.utils.Utilidades;


@WebServlet(name = "ServletEliminar", urlPatterns = {"/eliminar"})
public class ServletEliminar extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        HttpSession sesion = request.getSession();
        
        String idProducto = request.getParameter("id");
        short id;
        try {
            id = Short.parseShort(idProducto);
        } catch (NumberFormatException nfe) {
            id = 0;
        }
        
        List<Producto> carro = (List) sesion.getAttribute("carro");
        if (carro != null) {
            for (Producto producto : carro) {
                if (producto.getId() == id) {
                    carro.remove(producto);
                    break;
                }
            }
        }
        
        sesion.setAttribute("carro", carro);
        Utilidades.getInstancia().irAPagina(request, response, getServletContext(), "/carro.jsp");
        


    }
    
    
    

   

}
