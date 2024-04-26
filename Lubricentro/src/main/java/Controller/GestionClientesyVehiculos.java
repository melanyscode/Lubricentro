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
            return; // Salir del método sin hacer más operaciones
        }

        String cedula = JOptionPane.showInputDialog("Ingrese la cédula del cliente:");
        if (cedula == null) { // El usuario presionó cancelar
            return; // Salir del método sin hacer más operaciones
        }

        String modeloVehiculo = JOptionPane.showInputDialog("Ingrese el Modelo del vehículo del cliente:");
        if (modeloVehiculo == null) { // El usuario presionó cancelar
            return; // Salir del método sin hacer más operaciones
        }
        Vehiculo vehiculo = new Vehiculo(modeloVehiculo);
        Cliente cliente = new Cliente(nombre, cedula);
        System.out.println(cliente.toString());
        pilaClientes.apilar(cliente); // Agregar cliente a la pila
        agregarClienteBD(cliente, vehiculo); // Agregar cliente a la base de datos
        JOptionPane.showMessageDialog(null, "Cliente registrado en la pila y en la base de datos: " + cliente, "Registro de Cliente", JOptionPane.INFORMATION_MESSAGE);
        pilaClientes.vaciarPila();
    }

    public void ConsultarPilaClientes() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("Contenido de la pila de clientes:\n");
        agregarBDPila(pilaClientes);

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
        pilaClientes.vaciarPila();
    }

    public void EliminarClientes() {
        agregarBDPila(pilaClientes);

        //pedir el ID del cliente a eliminar
        int id = 0;
        String input = JOptionPane.showInputDialog(null, "Ingrese el ID del cliente que desea eliminar: ");
        if (input == null) {
            pilaClientes.vaciarPila();
            return;

        } else {
            try {
                id = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto, intente de nuevo");
            }

            if (id != 0) {
                Cliente cliente = buscarCliente(id);
                if (cliente != null) {
                    int opc = JOptionPane.showConfirmDialog(null, "¿Desea eliminar, Cliente ID: " + cliente.getIdCliente() + " Nombre: " + cliente.getNombre() + " Cedula: " + cliente.getCedula());
                    if (opc == 0) {
                        eliminarClienteBD(id);
                        JOptionPane.showMessageDialog(null, "El cliente se ha eliminado satisfactoriamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Acción cancelada");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro un cliente asociado con ese ID");
                }

            }
        }
        pilaClientes.vaciarPila();
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
    public void agregarClienteBD(Cliente cliente, Vehiculo vehiculo) {
        ConexionBD conexion = new ConexionBD(); // Crear una nueva instancia de ConexionBD
        PreparedStatement statement = null;
        int idVehiculo = 0;
        int idCliente = 0;
        try {
            conexion.setConexion(); // Establecer la conexión a la base de datos

            conexion.setConsulta("INSERT INTO vehiculo (modelo) VALUES (?)");
            statement = conexion.getConsulta();
            statement.setString(1, vehiculo.getModelo());
            statement.executeUpdate();

            //obtener el id del vehiculo
            conexion.setConsulta("SELECT * FROM vehiculo WHERE modelo = ?");
            statement = conexion.getConsulta();
            statement.setString(1, vehiculo.getModelo());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                idVehiculo = rs.getInt("id_vehiculo");

            }
            cliente.setIdVehiculo(idVehiculo);

            // Preparar la consulta SQL para insertar el cliente en la tabla
            String consulta = "INSERT INTO cliente (nombre, cedula, id_vehiculo) VALUES (?, ?, ?)";
            conexion.setConsulta(consulta);
            statement = conexion.getConsulta();
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getCedula());
            statement.setInt(3, idVehiculo);

            // Ejecutar la consulta para insertar el cliente en la tabla
            statement.executeUpdate();

            //obtener ID
            String consulta2 = "SELECT * FROM cliente WHERE id_vehiculo = ?";
            conexion.setConsulta(consulta2);
            statement = conexion.getConsulta();
            statement.setInt(1, idVehiculo);

            ResultSet rs1 = statement.executeQuery();
            while (rs1.next()) {
                idCliente = rs1.getInt("id_vehiculo");
            }
            cliente.setIdCliente(idCliente);
//           

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

    public static void agregarBDPila(PilaCliente pila) {
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

    public void eliminarClienteBD(int id) {

        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("DELETE FROM cliente WHERE id_cliente = ?");
            preState = conexion.getConsulta();
            preState.setInt(1, id);
            preState.executeUpdate();

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

    public Cliente buscarCliente(int id) {
        PreparedStatement preState = null;
        Cliente cliente = new Cliente();
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM cliente WHERE id_cliente = ?");
            preState = conexion.getConsulta();
            preState.setInt(1, id);
            ResultSet rs = preState.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String cedula = rs.getString("cedula");
                int idCliente = rs.getInt("id_cliente");
                return cliente = new Cliente(nombre, cedula, idCliente);

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
        return null;
    }
}
