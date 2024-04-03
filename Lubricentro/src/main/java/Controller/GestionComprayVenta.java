/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import ModuloCompraVenta.Cola;
import ModuloCompraVenta.ColaVenta;
import java.sql.ResultSet;
import Objetos.Producto;
import ModuloCompraVenta.ColaCompra;
import ModuloCompraVenta.NodoColaCompra;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionComprayVenta {

    public static Cola listaCompra = new Cola();
    public static ColaVenta listaVenta = new ColaVenta();
    public static ColaCompra listaCompraVenta = new ColaCompra();
    private ColaCompra carritoCompra = new ColaCompra();
    
    Producto p = new Producto();
    ConexionBD conexion = new ConexionBD();

    public void agregarCarrito(Producto producto) {
        carritoCompra.agregar(producto);
    }

    public void realizarCompra() {
        StringBuilder mensaje = new StringBuilder("Productos comprados:\n");
        while (!carritoCompra.estaVacia()) {
            Producto productoComprado = carritoCompra.atender();
            mensaje.append(productoComprado.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Compra realizada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public String mostrarCarrito() {
        return carritoCompra.mostrarCarrito();
    }

    public void Ventas() {
        String[] opciones = {"Agregar al carrito", "Realizar compra", "Volver"};
        int opcion;
        do {
            opcion = Menu.Menu("Menú Compras", "Lubricentro", opciones, "Agregar al carrito");
            switch (opcion) {
                case 0:
                    mostrarInventario(); // Mostrar inventario antes de agregar al carrito
                    int idProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto a agregar al carrito:"));
                    agregarProductoCarrito(idProducto);
                    break;
                case 1:
                    realizarCompra();
                    break;
                case 2:
                    Lubricentro.Lubricentro.InicioAdmin(); // Volver al menú principal
                    break;
            }
        } while (opcion != opciones.length);
    }

    public void VentasU() {
        String[] opciones = {"Agregar al carrito", "Realizar compra", "Volver"};
        int opcion;
        do {
            opcion = Menu.Menu("Menú Compras", "Lubricentro", opciones, "Agregar al carrito");
            switch (opcion) {
                case 0:
                    mostrarInventario(); // Mostrar inventario antes de agregar al carrito
                    int idProducto = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto a agregar al carrito:"));
                    agregarProductoCarrito(idProducto);
                    break;
                case 1:
                    realizarCompra();
                    break;
                case 2:
                    Lubricentro.Lubricentro.InicioAdmin(); // Volver al menú principal
                    break;
            }
        } while (opcion != opciones.length);
    }
    
    public Producto consultarProductoPorID(int idProducto) {
    Producto producto = null;
    try {
        conexion.setConexion();

        String consulta = "SELECT * FROM producto WHERE id_producto = ?";
        conexion.setConsulta(consulta);
        conexion.getConsulta().setInt(1, idProducto);

        ResultSet resultado = conexion.getConsulta().executeQuery();
        if (resultado.next()) {
            int idCategoria = resultado.getInt("id_categoria");
            String descripcion = resultado.getString("descripcion");
            String detalle = resultado.getString("detalle");
            double precio = resultado.getDouble("precio");
            int existencias = resultado.getInt("existencias");
            boolean activo = resultado.getInt("activo") == 1; // Convertir el int a boolean

            // Crear un nuevo objeto Producto con los valores obtenidos de la consulta
            producto = new Producto(idProducto, descripcion, detalle, precio, existencias, idCategoria, activo);
        } else {
            System.out.println("Producto no encontrado.");
        }
    } catch (SQLException error) {
        error.printStackTrace();
    } finally {
        conexion.cerrarConexion();
    }
    return producto; // Retorna el objeto Producto obtenido de la consulta
}

    public void agregarProductoCarrito(int idProducto) {
        Producto producto = consultarProductoPorID(idProducto);
        if (producto != null) {
            carritoCompra.agregar(producto);
            JOptionPane.showMessageDialog(null, "Producto agregado al carrito: " + producto.getNombre(),
                    "Producto Agregado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarInventario() {
        try {
            conexion.setConexion();
            String consulta = "SELECT * FROM lubricentro_jomean.producto";
            conexion.setConsulta(consulta);
            ResultSet resultado = conexion.getConsulta().executeQuery();
            StringBuilder mensaje = new StringBuilder();

            while (resultado.next()) {
                // Obtener datos del producto desde la consulta
                int idProducto = resultado.getInt("id_producto");
                int idCategoria = resultado.getInt("id_categoria");
                String descripcion = resultado.getString("descripcion");
                String detalle = resultado.getString("detalle");
                double precio = resultado.getDouble("precio");
                int existencias = resultado.getInt("existencias");
                boolean activo = resultado.getInt("activo") == 1;

                // Crear un nuevo objeto Producto con los datos obtenidos de la consulta
                Producto producto = new Producto(idProducto, descripcion, detalle, precio, existencias, idCategoria, activo);

                // Agregar el producto al mensaje a mostrar
                mensaje.append(producto.toString()).append("\n");
            }

            // Mostrar el mensaje con todos los productos en un JOptionPane
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Inventario", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error al consultar el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
    }
}
    

