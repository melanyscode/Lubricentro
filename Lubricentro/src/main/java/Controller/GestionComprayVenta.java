/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Conexiones.ConexionBD;
import static Controller.GestionClientesyVehiculos.conexion;
import ModuloCompraVenta.Cola;
import ModuloCompraVenta.ColaVenta;
import java.sql.ResultSet;
import Objetos.Producto;
import ModuloCompraVenta.ColaCompra;
import ModuloCompraVenta.NodoColaCompra;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private int obtenerIdOperario() {
        String input = JOptionPane.showInputDialog(null, "Ingrese el ID del operador:");
        try {
            int idOperario = Integer.parseInt(input);
            return idOperario;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de operador inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return obtenerIdOperario(); // Recursar para pedir un ID válido nuevamente
        }
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
                case 3:
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
                case 3:
                    Lubricentro.Lubricentro.InicioAdmin(); // Volver al menú principal
                    break;

            }
        } while (opcion != opciones.length);
    }
///BD////

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

    private int obtenerUltimoIdFactura(ConexionBD conexion) throws SQLException {
        String consulta = "SELECT LAST_INSERT_ID()";
        conexion.setConsulta(consulta);
        ResultSet resultado = conexion.getConsulta().executeQuery();

        if (resultado.next()) {
            return resultado.getInt(1);
        } else {
            throw new SQLException("Error al obtener el último ID de factura generado.");
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

    private void actualizarExistenciasProductos(ConexionBD conexion) {
        Producto producto;
        int existenciasActuales;

        while (!carritoCompra.estaVacia()) {
            producto = carritoCompra.atender();
            existenciasActuales = producto.getStock(); // Utilizar el método `getStock`

            // Actualizar existencias en la tabla "producto"
            try {
                String consultaExistencias = "UPDATE producto SET stock = ? WHERE id_producto = ?";
                conexion.setConsulta(consultaExistencias); // Usar `setConsulta`

                conexion.getConsulta().setInt(1, existenciasActuales - producto.getStock()); // Restar la cantidad vendida
                conexion.getConsulta().setInt(2, producto.getId()); // Utilizar el método `getId`

                conexion.getConsulta().executeUpdate();
            } catch (SQLException error) {
                error.printStackTrace();
            }
        }
    }

    public void realizarCompra() {
        StringBuilder mensaje = new StringBuilder("Factura Simplificada\n\n"); // Updated header
        int idCliente = 0;
        double totalVenta = 0.0;

        while (!carritoCompra.estaVacia()) {
            Producto productoComprado = carritoCompra.atender();
            mensaje.append(productoComprado.getNombre())
                    .append(": $")
                    .append(productoComprado.getPrecio())
                    .append("\n"); // You might need to add 'quantity' if tracking it
            totalVenta += productoComprado.getPrecio();
        }

        // Mostrar lista de clientes registrados
        imprimirClientesRegistrados();

        // Solicitar al usuario que seleccione un cliente
        idCliente = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el ID del cliente:"));

        if (idCliente <= 0) {
            JOptionPane.showMessageDialog(null, "Error: El ID del cliente debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        while (!carritoCompra.estaVacia()) {
            Producto productoComprado = carritoCompra.atender();
            mensaje.append(productoComprado.getNombre()).append("\n");
            totalVenta += productoComprado.getPrecio();
        }

        mensaje.append("\nTotal: $").append(totalVenta);

        // Get user confirmation
        int confirmacion = JOptionPane.showConfirmDialog(
                null, mensaje.toString(),
                "¿Confirmar Compra?",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Process the transaction (e.g., update product stock) if confirmed
            actualizarExistenciasProductos(conexion); // Update stocks if needed
            carritoCompra.vaciarCarrito();
            JOptionPane.showMessageDialog(null, "Compra realizada con éxito!", "Compra", JOptionPane.INFORMATION_MESSAGE);

            // Registrar la venta en la tabla "venta" (SIN fecha_venta)
            try {
                conexion.setConexion();

                // Crear la factura
                String consultaFactura = "INSERT INTO factura (id_cliente, fecha, total, id_operario) VALUES (?, ?, ?, ?)";
                conexion.setConsulta(consultaFactura);

                // Obtener la fecha actual
                java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());

                // Configurar los parámetros de la consulta
                conexion.getConsulta().setInt(1, idCliente);
                conexion.getConsulta().setDate(2, fechaActual);
                conexion.getConsulta().setDouble(3, totalVenta);
                conexion.getConsulta().setInt(4, obtenerIdOperario());

                // Ejecutar la consulta para crear la factura
                conexion.getConsulta().executeUpdate();

                // Obtener el ID de la factura generada
                int idFactura = obtenerUltimoIdFactura(conexion); // Método para obtener el último ID de factura

                // Registrar la venta
                String consultaVenta = "INSERT INTO venta (id_cliente, precio, id_factura) VALUES (?, ?, ?)";
                conexion.setConsulta(consultaVenta);

                conexion.getConsulta().setInt(1, idCliente);
                conexion.getConsulta().setDouble(2, totalVenta);
                conexion.getConsulta().setInt(3, idFactura);

                conexion.getConsulta().executeUpdate();

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, mensaje.toString() + "\nTotal: $" + totalVenta, "Compra realizada", JOptionPane.INFORMATION_MESSAGE);

                // Actualizar existencias de productos
                actualizarExistenciasProductos(conexion);

                // Vaciar el carrito
                carritoCompra.vaciarCarrito();

            } catch (SQLException error) {
                error.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                conexion.cerrarConexion();
            }
        }
    }
}
