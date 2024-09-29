package xyz.mendesoft.jbtienda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import xyz.mendesoft.jbtienda.exceptions.ExcepcionGeneral;
import xyz.mendesoft.jbtienda.models.Producto;
import xyz.mendesoft.jbtienda.models.VentaDetalle;
import xyz.mendesoft.jbtienda.models.VentaEncabezado;


public class PSQLVenta {
    
    private final String ENCABEZADO = "INSERT INTO ventas_encabezado (nombre, direccion) VALUES (?,?) RETURNING id_venta, fecha, nombre, direccion";
    private final String DETALLE    = "INSERT INTO ventas_detalle (id_venta_encabezado, id_producto, id_talla, valor) VALUES (?,?,?,?)";
    
    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;
    
    public VentaEncabezado insertarVenta(String nombre, String direccion, List<Producto> carro)
                throws ExcepcionGeneral, ClassNotFoundException {
        VentaEncabezado encabezado = null;
        try {
            encabezado = insertaEncabezado(nombre, direccion);
            insertaDetalle(carro, encabezado);
        } catch (ExcepcionGeneral eg) {
            throw new ExcepcionGeneral("Error al insertar ventas: "+eg.getMessage());
        }
        return encabezado;
    }
            
    private VentaEncabezado insertaEncabezado(String nombre, String direccion) throws ExcepcionGeneral, ClassNotFoundException {
        VentaEncabezado encabezado = null;
        try {
            conexion = new PSQLConexion().conectar();
            sentencia = conexion.prepareStatement(ENCABEZADO);
            sentencia.setString(1, nombre);
            sentencia.setString(2, direccion);
            resultados = sentencia.executeQuery();
            if (resultados.next()) {
                encabezado = new VentaEncabezado();
                encabezado.setId(resultados.getInt("id_venta"));
                encabezado.setFecha(resultados.getDate("fecha").toLocalDate());
                encabezado.setNombre(resultados.getString("nombre"));
                encabezado.setDireccion(resultados.getString("direccion"));
            }
        } catch (SQLException sqle) {
            throw new ExcepcionGeneral("Error al insertar El encabezado de venta: "+sqle.getMessage());
        } finally {
            cerrarRecursos();
        }
        return encabezado;
    }
    
    private void insertaDetalle(List<Producto> carro, VentaEncabezado encabezado) throws ExcepcionGeneral, ClassNotFoundException {
        try {
            conexion = new PSQLConexion().conectar();
            sentencia = conexion.prepareStatement(DETALLE);
            for (Producto producto : carro) {
                
                VentaDetalle detalle = new VentaDetalle();
                detalle.setIdEncabezado(encabezado.getId());
                detalle.setProducto(producto);
                
                sentencia.setInt(1, detalle.getIdEncabezado());
                sentencia.setShort(2, detalle.getProducto().getId());
                sentencia.setShort(3, detalle.getProducto().getTalla().getId());
                sentencia.setFloat(4, detalle.getProducto().getValor());
                sentencia.executeUpdate();
                encabezado.addDetalle(detalle);
            }
        } catch (SQLException sqle) {
            throw new ExcepcionGeneral("Error al insertar El detalle de venta: "+sqle.getMessage());
        } finally {
            cerrarRecursos();
        }
    }
    
    private void cerrarRecursos() {
        try {
            if (resultados != null) {
                resultados.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException sqle) {}
    }
}
