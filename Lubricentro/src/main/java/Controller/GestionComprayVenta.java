/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import ModuloCompraVenta.Cola;
import ModuloCompraVenta.ColaVenta;
import java.sql.*;
import Objetos.Producto;
import ModuloCompraVenta.ColaCompra;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Melanie Gutierrez
 */
public class GestionComprayVenta {

    public int cantidad = 0;
    public static Cola listaCompra = new Cola();
    public static ColaVenta listaVenta = new ColaVenta();
    public static ColaCompra listaCompraVenta = new ColaCompra();
    private ColaCompra carritoCompra = new ColaCompra();

    Producto p = new Producto();
    ConexionBD conexion = new ConexionBD();

    public void agregarCarrito(Producto producto) {
        carritoCompra.agregar(producto);
    }

//    private int obtenerIdOperario() {
//        String input = JOptionPane.showInputDialog(null, "Ingrese el ID del operador:");
//        try {
//            int idOperario = Integer.parseInt(input);
//            return idOperario;
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "ID de operador inválido.", "Error", JOptionPane.ERROR_MESSAGE);
//            return obtenerIdOperario(); // Recursar para pedir un ID válido nuevamente
//        }
//    }
    public void realizarCompra(int idProducto) {
        if (idProducto == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos en el carrito, agregue un producto antes de realizar la compra");
            return;
        }
        StringBuilder mensaje = new StringBuilder("Productos comprados:\n");
        int idCliente = 0;
        double totalVenta = 0.0;
        double totalIVA = 0.0;
        double precio = 0;

        while (!carritoCompra.estaVacia()) {
            Producto productoComprado = carritoCompra.atender();
            mensaje.append(productoComprado.getNombre()).append("\n");
            totalIVA = (productoComprado.getPrecio() * cantidad) * 0.13;
            totalVenta = (productoComprado.getPrecio() * cantidad) + totalIVA;
            precio = productoComprado.getPrecio() * cantidad;
        }

        // Mostrar lista de clientes registrados
        imprimirClientesRegistrados();

        // Solicitar al usuario que seleccione un cliente
        String idClienteInput = JOptionPane.showInputDialog(null, "Ingrese el ID del cliente:");
        if (idClienteInput == null) {
            return;
        } else {
            try {
                idCliente = Integer.parseInt(idClienteInput);
            } catch (Exception e) {
            }
        }

        if (idCliente <= 0) {
            JOptionPane.showMessageDialog(null, "Error: El ID del cliente debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Registrar la venta en la tabla "venta" (SIN fecha_venta)
        try {
            Producto producto = consultarProductoPorID(idProducto);
            if(producto == null){
                JOptionPane.showMessageDialog(null, "No existe un producto con ese ID");
                return;
            }
            conexion.setConexion();

            // Obtener el ID de la factura generada
            // Método para obtener el último ID de factura
            // Registrar la venta
            String consultaVenta = "INSERT INTO venta (id_producto, id_cliente, precio, cantidad) VALUES (?, ?, ?, ?)";
            conexion.setConsulta(consultaVenta);

            conexion.getConsulta().setInt(1, producto.getId());
            conexion.getConsulta().setInt(2, idCliente);
            conexion.getConsulta().setDouble(3, totalVenta);
            conexion.getConsulta().setInt(4, cantidad);
            conexion.getConsulta().executeUpdate();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, mensaje.toString() + "\nTotal: " +  precio + "\nTotal con IVA: " + totalVenta + "\nCompra realizada");

            // Actualizar existencias de producto
            actualizarStock(producto.getId(), cantidad);

            // Crear la factura
            String consultaFactura = "INSERT INTO factura (id_cliente, fecha, total) VALUES (?, ?, ?)";
            conexion.setConsulta(consultaFactura);

            // Obtener la fecha actual
            java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());

            // Configurar los parámetros de la consulta
            conexion.getConsulta().setInt(1, idCliente);
            conexion.getConsulta().setDate(2, fechaActual);
            conexion.getConsulta().setDouble(3, totalVenta);

//            conexion.getConsulta().setInt(4, obtenerIdOperario());
            // Ejecutar la consulta para crear la factura
            conexion.getConsulta().executeUpdate();
            // Vaciar el carrito
            carritoCompra.vaciarCarrito();

        } catch (SQLException error) {
            error.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void imprimirClientesRegistrados() {
        try {
            conexion.setConexion();

            String consulta = "SELECT * FROM cliente";
            conexion.setConsulta(consulta);

            ResultSet resultado = conexion.getConsulta().executeQuery();
            StringBuilder mensaje = new StringBuilder("Clientes registrados en la base de datos:\n");

            while (resultado.next()) {
                int idCliente = resultado.getInt("id_cliente");
                String nombre = resultado.getString("nombre");
                String cedula = resultado.getString("cedula");

                mensaje.append("ID Cliente: ").append(idCliente)
                        .append(", Nombre: ").append(nombre)
                        .append(", Cédula: ").append(cedula)
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, mensaje.toString(), "Clientes Registrados", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error al consultar los clientes registrados en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
    }

    public String mostrarCarrito() {
        return carritoCompra.mostrarCarrito();
    }

    public void Ventas() {
        String[] opciones = {"Agregar al carrito", "Realizar compra", "Volver"};
        int opcion;
        int idProducto = 0;
        do {
            opcion = Menu.Menu("Menú Compras", "Gestion de Compras", opciones, "Agregar al carrito");
            switch (opcion) {
                case 0:
                    mostrarInventario(); // Mostrar inventario antes de agregar al carrito
                    String idProductoInput = JOptionPane.showInputDialog("Ingrese el ID del producto a agregar al carrito:");
                    if (idProductoInput == null) {
                        return;
                    } else {
                        idProducto = Integer.parseInt(idProductoInput);
                    }
                    agregarProductoCarrito(idProducto);
                    break;
                case 1:
                    realizarCompra(idProducto);
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
        int idProducto = 0;
        do {
            opcion = Menu.Menu("Menú Compras", "Lubricentro", opciones, "Agregar al carrito");
            switch (opcion) {
                case 0:
                    mostrarInventario(); // Mostrar inventario antes de agregar al carrito
                    String idProductoInput = JOptionPane.showInputDialog("Ingrese el ID del producto a agregar al carrito:");
                    if (idProductoInput == null) {
                        return;
                    } else {
                        idProducto = Integer.parseInt(idProductoInput);
                    }
                    agregarProductoCarrito(idProducto);
                    break;
                case 1:
                    realizarCompra(idProducto);
                    break;
                case 2:
                    Lubricentro.Lubricentro.InicioUsuario(); // Volver al menú principal
                    break;
            }
        } while (opcion != opciones.length);
    }
///BD/////

    public Producto consultarProductoPorID(int idProducto) {
        Producto producto = null;
        try {
            conexion.setConexion();

            String consulta = "SELECT * FROM producto WHERE id_producto = ?";
            conexion.setConsulta(consulta);
            conexion.getConsulta().setInt(1, idProducto);

            ResultSet resultado = conexion.getConsulta().executeQuery();
            while (resultado.next()) {
                int idP = resultado.getInt("id_producto");
                int idCategoria = resultado.getInt("id_categoria");
                String descripcion = resultado.getString("descripcion");
                String detalle = resultado.getString("detalle");
                double precio = resultado.getDouble("precio");
                int existencias = resultado.getInt("existencias");
                boolean activo = resultado.getInt("activo") == 1; // Convertir el int a boolean

                // Crear un nuevo objeto Producto con los valores obtenidos de la consulta
                producto = new Producto(idP, descripcion, detalle, precio, existencias, idCategoria, activo);
                return producto;
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
            String input = JOptionPane.showInputDialog(null, "Ingrese la cantidad que desea: ");
            if (input == null) {
                return;
            } else {
                try {
                    cantidad = Integer.parseInt(input);
                } catch (Exception e) {
                }
            }
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

    private void actualizarStock(int idProducto, int cantidad) {
        PreparedStatement preState = null;
        int existencias = 0;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM producto WHERE id_producto = ?");
            preState = conexion.getConsulta();
            preState.setInt(1, idProducto);
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                existencias = rs.getInt("existencias");
            }
            //hacer update 
            int nuevoStock = existencias - cantidad;
            conexion.setConsulta("UPDATE producto SET existencias = ? WHERE id_producto = ?");
            preState = conexion.getConsulta();
            preState.setInt(1, nuevoStock);
            preState.setInt(2, idProducto);
            preState.executeUpdate();

        } catch (Exception e) {
        }
    }
}
