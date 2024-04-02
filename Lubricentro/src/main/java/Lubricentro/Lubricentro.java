package Lubricentro;

import Conexiones.ConexionBD;
import Controller.GestionClientes;
import Controller.GestionComprayVenta;
import Controller.GestionOperarios;
import Controller.GestionProductos;
import Controller.GestionTrabajos;
import Controller.GestionVehiculos;
import Controller.Menu;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author josea
 */
public class Lubricentro {

    public static ConexionBD conexion = new ConexionBD();
    public static Menu menu = new Menu();
    public static GestionProductos gestionP = new GestionProductos();
    public static GestionComprayVenta gestionCYV = new GestionComprayVenta();
    public static GestionOperarios gestionOp = new GestionOperarios();
    public static GestionClientes gestionCl = new GestionClientes();
    public static GestionTrabajos gestionTr = new GestionTrabajos();
    public static GestionVehiculos gestionVe = new GestionVehiculos();

    public static void main(String[] args) {
        Inicio();
    }

    public static void InicioAdmin() {
        String[] opcs = {"Inventario", "Ventas", "Operarios", "Clientes", "Trabajos", "Salir"};
        int opc;
        do {
            opc = Menu.Menu("Menu Principal", "Lubricentro", opcs, "Inventario");
            switch (opc) {
                case 0:
                    Inventario();
                    break;
                case 1:
                    gestionCYV.Ventas();
                    break;
                case 2:
                    gestionOp.OperarioMenu();
                    break;
                case 3:
                    gestionCl.Clientes();
                    break;
                case 4:
                    gestionTr.menuTrabajos();
                case 5:
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }

    public static void Inventario() {
        String[] opcs = {"Productos", "Vehiculos", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario", "Elija una opcion", opcs, "Productos");
            switch (opc) {
                case 0:
                    gestionP.menuProductos();
                    break;
                case 1:
                    gestionVe.menuVehiculos();
                    break;
                case 2:
                    InicioAdmin();
                    break;
            }
        } while (opc != opcs.length);
    }

    //lo que tiene accesos los operarios
    public static void InicioUsuario() {
        String[] opcs = {"Inventario", "Ventas", "Trabajos", "Salir"};
        int opc;
        do {
            opc = Menu.Menu("Menu Principal", "Lubricentro", opcs, "Inventario");
            switch (opc) {
                case 0:
                    InventarioUsuario();
                    break;
                case 1:
                    gestionCYV.VentasU();
                    break;
                case 2:
                    gestionTr.menuTrabajosU();
                case 3:
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }

    public static void InventarioUsuario() {
        String[] opcs = {"Productos", "Vehiculos", "Volver"};
        int opc;
        do {
            opc = Menu.Menu("Inventario", "Elija una opcion", opcs, "Productos");
            switch (opc) {
                case 0:
                    gestionP.menuProductosU();
                    break;
                case 1:
                    gestionVe.menuVehiculosU();
                    break;
                case 2:
                    InicioUsuario();
                    break;
            }
        } while (opc != opcs.length);
    }

    //menu de inicio 
    public static void Inicio() {
        String[] opcs = {"Registrarse", "Iniciar sesion", "Salir"};
        int opc;
        do {
            opc = Menu.Menu("Inicio", "Elija una opcion", opcs, "Registrarse");
            switch (opc) {
                case 0:
                    Registro();
                    break;
                case 1:
                    Login();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        } while (opc != opcs.length);
    }
    //registro

    public static void Registro() {
        String user = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario");
        if (user == null) {
            Inicio();
        }

        //setear el statement 
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM usuario WHERE username = ?");
            preState = conexion.getConsulta();
            preState.setString(1, user);
            ResultSet consulta = preState.executeQuery();
            //si la consulta da un resultado entonces el usuario ya existe en la bd
            if (consulta != null && consulta.next()) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe intente con un usuario diferente");
                Inicio();
            } else {
                //sino existe se le pide la contraseña para registrar el nuevo usuario
                String password = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario");
                if (password == null) {
                    Inicio();
                }
                if (user != null && password != null) {
                    conexion.setConexion();
                    conexion.setConsulta("INSERT INTO usuario (username, password) VALUES (?,?)");
                    preState = conexion.getConsulta();
                    preState.setString(1, user);
                    preState.setString(2, password);
                    preState.executeUpdate();

                    //asignar rol 
                    conexion.setConsulta("SELECT id_usuario FROM usuario WHERE username = ?");
                    preState = conexion.getConsulta();
                    preState.setString(1, user);
                    ResultSet idConsulta = preState.executeQuery();
                    if (idConsulta.next()) {
                        int id = idConsulta.getInt("id_usuario");
                        conexion.setConsulta("INSERT INTO rol (nombre, id_usuario) VALUES (?,?)");
                        preState = conexion.getConsulta();
                        preState.setString(1, "ROLE_USER");
                        preState.setInt(2, id);
                        preState.executeUpdate();
                        conexion.cerrarConexion();
                        JOptionPane.showMessageDialog(null, "Usuario registrado de manera satisfactoria");
                    }
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarConexion();
        }
    }

    //login del sistema
    public static void Login() {
        String username = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario");
        if (username == null) {
            int decision = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", null, JOptionPane.YES_NO_OPTION);
            if (decision == 0) {
                System.exit(0);
            } else {
                Login();
            }
        }
        String contrasenia = JOptionPane.showInputDialog(null, "Ingrese la contraseña");
        if (contrasenia == null) {
            int decision = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", null, JOptionPane.YES_NO_OPTION);
            if (decision == 0) {
                System.exit(0);
            } else {
                Login();
            }
        }
        // conexion con la BD
        PreparedStatement preState = null;
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM usuario");
            preState = conexion.getConsulta();
            ResultSet consulta = preState.executeQuery();

            while (consulta.next()) {

                if (username.equals(consulta.getString("username")) && contrasenia.equals(consulta.getString("password"))) {
                    int id = consulta.getInt("id_usuario");
                    conexion.setConsulta("SELECT nombre FROM rol WHERE id_usuario = ?");
                    preState = conexion.getConsulta();
                    preState.setInt(1, id);
                    ResultSet consultaRol = preState.executeQuery();
                    while (consultaRol.next()) {
                        String admin = "ROLE_ADMIN";
                        String usuario = "ROLE_USER";
                        if (consultaRol.getString("nombre").equals(admin)) {
                            InicioAdmin();
                        } else if (consultaRol.getString("nombre").equals(usuario)) {
                            InicioUsuario();
                        }
                    }

                }
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
