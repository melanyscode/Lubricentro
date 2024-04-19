/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Lubricentro.Lubricentro.InicioAdmin;
import Conexiones.ConexionBD;
import ModuloClientes.NodoCliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Objetos.Cliente;
import ModuloClientes.PilaCliente;
import Objetos.Vehiculo;

/**
 *
 * @author Melanie Gutierrez y Jose :3
 */
public class GestionClientesyVehiculos {

    //getsion de clientes y vehiculos (vehiculo esta relacionado con el cliente)
    public static PilaCliente pilaClientes;
    Cliente c = new Cliente();
    public static ConexionBD conexion = new ConexionBD();
    private ConexionBD conexionBD;

    public GestionClientesyVehiculos() {
        conexionBD = new ConexionBD();
        pilaClientes = new PilaCliente();
    }

    public void RegistrarCliente() {
        ///Imprime los clientes ya registrados desde la base de datos//////
        imprimirClientesRegistrados();
        // Solicitar los datos del cliente al usuario
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente:");
        if (nombre == null) { // El usuario presionó cancelar
            JOptionPane.showMessageDialog(null, "Operación cancelada.", "Registro de Cliente", JOptionPane.INFORMATION_MESSAGE);
            return; // Salir del método sin hacer más operaciones
        }

        String cedula = JOptionPane.showInputDialog("Ingrese la cédula del cliente:");
        if (cedula == null) { // El usuario presionó cancelar
            JOptionPane.showMessageDialog(null, "Operación cancelada.", "Registro de Cliente", JOptionPane.INFORMATION_MESSAGE);
            return; // Salir del método sin hacer más operaciones
        }

        String modeloVehiculo = JOptionPane.showInputDialog("Ingrese el Modelo del vehículo del cliente:");
        if (modeloVehiculo == null) { // El usuario presionó cancelar
            JOptionPane.showMessageDialog(null, "Operación cancelada.", "Registro de Cliente", JOptionPane.INFORMATION_MESSAGE);
            return; // Salir del método sin hacer más operaciones
        }

        // Verificar si el ID del cliente ya existe en la base de datos
//            boolean existeCliente = consultarExistenciaCliente(idCliente, conexionBD);
//            if (existeCliente) {
//                JOptionPane.showMessageDialog(null, "Error: El ID del cliente ya existe en la base de datos.", "Registro de Cliente", JOptionPane.ERROR_MESSAGE);
//                return; // Salir del método sin hacer más operaciones
//            }
        // Si el ID no existe en la base de datos, procedemos a registrar el cliente
        Vehiculo vehiculo = new Vehiculo(modeloVehiculo);
        Cliente cliente = new Cliente(nombre, cedula);
        System.out.println(cliente.toString());
        pilaClientes.apilar(cliente); // Agregar cliente a la pila
        agregarClienteBD(cliente, vehiculo); // Agregar cliente a la base de datos
        JOptionPane.showMessageDialog(null, "Cliente registrado en la pila y en la base de datos: " + cliente, "Registro de Cliente", JOptionPane.INFORMATION_MESSAGE);

    }

    public void ConsultarPilaClientes() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("Contenido de la pila de clientes:\n");

        if (!pilaClientes.esVacia()) {
            NodoCliente auxiliar = pilaClientes.getCima();
            while (auxiliar != null) {
                resultado.append(auxiliar.getElemento().toString()).append("\n");
                auxiliar = auxiliar.getSiguiente();
            }
        } else {
            resultado.append("La pila de clientes está vacía.");
        }

        JOptionPane.showMessageDialog(null, resultado.toString(), "Consulta de Pila de Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    public void EliminarClientes() {
        if (!pilaClientes.esVacia()) {
            pilaClientes.desapilar(); // Llama al método desapilar de la pila
            JOptionPane.showMessageDialog(null, "Cliente eliminado de la pila correctamente.", "Eliminar Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "La pila de clientes está vacía.", "Eliminar Cliente", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void Clientes() {
        String[] opcs = {"Registrar", "Consultar", "Eliminar", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Menú Clientes", "Elija una opción", opcs, "Registrar");
            switch (opc) {
                case 0:
                    RegistrarCliente();
                    break;
                case 1:
                    ConsultarPilaClientes();
                    break;
                case 2:
                    EliminarClientes();
                    break;
                case 3:
                    InicioAdmin();
                    break;
            }
        } while (opc != opcs.length);
    }

    ////////SECCION CON BD/////////////
    ///////BE CAREFUL/////////////////
    private boolean consultarExistenciaCliente(int idCliente, ConexionBD conexion) {
        boolean existeCliente = false;
        try {
            conexion.setConexion(); // Establecer la conexión a la base de datos

            // Preparar la consulta SQL para contar cuántos registros tienen el ID del cliente
            String consulta = "SELECT COUNT(*) FROM cliente WHERE id_cliente = ?";
            conexion.setConsulta(consulta);
            conexion.getConsulta().setInt(1, idCliente);

            // Ejecutar la consulta y obtener el resultado
            ResultSet resultado = conexion.getResultado();
            if (resultado.next()) {
                int count = resultado.getInt(1);
                existeCliente = count > 0; // Si count > 0, significa que el cliente ya existe en la base de datos
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error al consultar la existencia del cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();
        }
        return existeCliente;
    }

    public void agregarClienteBD(Cliente cliente, Vehiculo vehiculo) {
        ConexionBD conexion = new ConexionBD(); // Crear una nueva instancia de ConexionBD
        PreparedStatement statement = null;
        try {
            conexion.setConexion(); // Establecer la conexión a la base de datos

            // Verificar si el ID del cliente ya existe en la base de datos
//            boolean existeCliente = consultarExistenciaCliente(cliente.getIdCliente(), conexion);
//            if (existeCliente) {
//                JOptionPane.showMessageDialog(null, "Error: El ID del cliente ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
//                return; // Salir del método sin hacer más operaciones
//            
            conexion.setConsulta("INSERT INTO vehiculo (modelo) VALUES (?)");
            statement = conexion.getConsulta();
            statement.setString(1, vehiculo.getModelo());
            statement.executeUpdate();

            //obtener el id del vehiculo
            conexion.setConsulta("SELECT * FROM vehiculo where modelo = ?");
            statement = conexion.getConsulta();
            statement.setString(1, vehiculo.getModelo());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
            }

            // Preparar la consulta SQL para insertar el cliente en la tabla
            String consulta = "INSERT INTO cliente (nombre, cedula, id_vehiculo) VALUES (?, ?, ?)";
            conexion.setConsulta(consulta);
            statement = conexion.getConsulta();
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getCedula());
            statement.setInt(3, vehiculo.getIdVehiculo());

            // Ejecutar la consulta para insertar el cliente en la tabla
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente agregado a la base de datos correctamente.", "Cliente Agregado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el cliente a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error al agregar el cliente a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();
        } finally {
            conexion.cerrarConexion(); // Cerrar la conexión
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

    public static void agregarBDCola(PilaCliente pila) {
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM cliente");
            preState = conexion.getConsulta();

            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String cedula = rs.getString("cedula");
                int idVehiculo = rs.getInt("id_vehiculo");
                Cliente cliente = new Cliente(nombre, cedula, idCliente, idVehiculo);
                pila.apilar(cliente);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
        } finally {
            try {
                if (preState != null) {
                    preState.close();
                }
                conexion.cerrarConexion();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage());
            }
        }
    }

}
